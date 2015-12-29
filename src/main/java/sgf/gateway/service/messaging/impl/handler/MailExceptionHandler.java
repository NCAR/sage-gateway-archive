package sgf.gateway.service.messaging.impl.handler;

import sgf.gateway.service.mail.MailService;
import sgf.gateway.service.messaging.ExceptionHandler;
import sgf.gateway.service.messaging.UnhandledException;

import java.util.Set;

public class MailExceptionHandler implements ExceptionHandler {

    private final MailService mailService;

    private final Set<String> notificationAddresses;

    public MailExceptionHandler(MailService mailService, Set<String> notificationAddresses) {

        super();
        this.mailService = mailService;
        this.notificationAddresses = notificationAddresses;
    }

    public void handleException(UnhandledException unhandledException) {

        this.mailService.sendUnhandledExceptionMailMessage(this.notificationAddresses, unhandledException);
    }

}
