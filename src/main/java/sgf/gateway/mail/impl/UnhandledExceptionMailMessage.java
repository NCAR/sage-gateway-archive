package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.service.messaging.UnhandledException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

public class UnhandledExceptionMailMessage extends FreeMarkerCommonMailMessage {

    public UnhandledExceptionMailMessage(Configuration configuration, Gateway gateway, String commonSubjectTemplate, String commonPlainTextMessageTemplate, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, commonSubjectTemplate, commonPlainTextMessageTemplate, subjectTemplate, plainTextMessageTemplate);
    }

    /**
     * Sets the unhandled exception.
     *
     * @param unhandledException the new unhandled exception
     */
    public void setUnhandledException(UnhandledException unhandledException) {

        // Add the message, in some cases this has important information.
        super.addObject("message", unhandledException.getMessage());


        // Due to scanability issues with the emails being sent Nate W. asked me to try and only
        // display the cause of the UnhandledException. This should make it easier for developers
        // and administrators to quickly determine if the specific exception is important to them.
        Throwable cause = unhandledException.getCause();

        super.addObject("exception", cause);

        StringBuffer stringBuffer = new StringBuffer();

        Set<String> keys = unhandledException.keySet();

        for (String key : keys) {

            stringBuffer.append(key + ": " + unhandledException.get(key) + "\n");
        }

        super.addObject("values", stringBuffer.toString());

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);

        cause.printStackTrace(printWriter);

        String stackTrace = stringWriter.getBuffer().toString();

        super.addObject("stackTrace", stackTrace);
    }
}
