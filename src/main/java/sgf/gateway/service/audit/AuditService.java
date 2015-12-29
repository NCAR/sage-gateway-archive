package sgf.gateway.service.audit;

import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.safehaus.uuid.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.audit.AuditRepository;
import sgf.gateway.model.AuditItem;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.security.RuntimeUserService;

import java.util.HashMap;
import java.util.HashSet;

public class AuditService {

    private static final int MAXIMUM_EXPECTED_TRANSACTION_MAP_SIZE = 50;
    private HashMap<Transaction, UUID> transactionMap;

    private AuditRepository auditRepository;
    private RuntimeUserService runtimeUserService;
    private ExceptionHandlingService exceptionHandlingService;

    public AuditService(AuditRepository auditRepository, RuntimeUserService runtimeUserService, ExceptionHandlingService exceptionHandlingService) {
        this.auditRepository = auditRepository;
        this.runtimeUserService = runtimeUserService;
        this.exceptionHandlingService = exceptionHandlingService;
        this.transactionMap = new HashMap<>();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void add(AuditItem auditItem, Transaction transaction) {

        setTransactionInfo(auditItem, transaction);
        this.auditRepository.add(auditItem);
    }


    public void removeCompletedTransaction(Transaction transaction) {

        if (this.transactionMap.containsKey(transaction)) {

            this.transactionMap.remove(transaction);
        }
    }


    private void setTransactionInfo(AuditItem auditItem, Transaction transaction) {

        String currentUser;

        // For automated jobs, such as EOL Thredds harvesting, getUser() returns null.
        if (this.runtimeUserService.getUser() == null) {
            currentUser = "rootAdmin";
        } else {
            currentUser = this.runtimeUserService.getUser().getUserName();
        }
        auditItem.setChangedBy(currentUser);

        DateTime updatedOn = new DateTime(DateTimeZone.UTC);
        auditItem.setChangedOn(updatedOn);

        UUID transactionId = getTransactionId(transaction);
        auditItem.setTransactionId(transactionId);
    }


    private UUID getTransactionId(Transaction transaction) {

        UUID transactionId;
        if (this.transactionMap.containsKey(transaction)) {

            transactionId = this.transactionMap.get(transaction);
        } else {

            transactionId = org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID();
            this.transactionMap.put(transaction, transactionId);
            checkTransactionMapSize();
        }

        return transactionId;
    }


    private void checkTransactionMapSize() {

        if (this.transactionMap.keySet().size() % MAXIMUM_EXPECTED_TRANSACTION_MAP_SIZE == 0) {

            String warningMessage = "Warning: AuditService has Transaction Map with size " + this.transactionMap.keySet().size();
            UnhandledException unhandledException = new UnhandledException(warningMessage);
            this.exceptionHandlingService.handledException(unhandledException);
            pruneTransactionMap();
        }
    }


    //  Collect and remove old transactions; this should be called only when "removeCompletedTransaction()" is
    //  not being being called regularly.
    private void pruneTransactionMap() {

        HashSet<Transaction> inactiveTransactions = new HashSet<>();

        for (Transaction transaction : this.transactionMap.keySet()) {
            if (!transaction.isParticipating()) {
                inactiveTransactions.add(transaction);
            }
        }
        for (Transaction inactive : inactiveTransactions) {
            this.transactionMap.remove(inactive);
        }

        if (this.transactionMap.keySet().size() > MAXIMUM_EXPECTED_TRANSACTION_MAP_SIZE) {

            String warningMessage = "Warning: After pruning, AuditService has Transaction Map with size " + this.transactionMap.keySet().size();
            UnhandledException unhandledException = new UnhandledException(warningMessage);
            this.exceptionHandlingService.handledException(unhandledException);
        }
    }

}
