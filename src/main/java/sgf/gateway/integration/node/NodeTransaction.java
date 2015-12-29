package sgf.gateway.integration.node;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class NodeTransaction<T> implements Node<T> {

    private PlatformTransactionManager transactionManager;
    private final Node<T> node;

    public NodeTransaction(PlatformTransactionManager transactionManager, String defaultPropagationName, Node<T> node) {
        super();

        this.transactionManager = transactionManager;
        this.node = node;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void process(final T payload) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(this.transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            public void doInTransactionWithoutResult(TransactionStatus status) {
                getNode().process(payload);
            }
        });
    }

    private Node<T> getNode() {
        return this.node;
    }
}
