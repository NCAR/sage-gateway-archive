package sgf.gateway.integration.service;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class FileLineReaderService {

    private final MessageChannel outputChannel;

    public FileLineReaderService(MessageChannel outputChannel) {
        super();
        this.outputChannel = outputChannel;
    }

    public Message<File> service(Message<File> input) {

        BufferedReader reader = openReader(input.getPayload());

        try {

            String line;

            while ((line = readLine(reader)) != null) {
                Message<String> message = buildMessage(input.getHeaders(), line);
                sendMessage(message);
            }

        } finally {
            closeReader(reader);
        }

        return input;
    }

    private BufferedReader openReader(File input) {

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(input));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return reader;
    }

    private void closeReader(Reader reader) {

        try {
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String readLine(BufferedReader reader) {

        String line;

        try {
            line = reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return line;
    }

    private Message<String> buildMessage(MessageHeaders headers, String line) {

        MessageBuilder<String> builder = MessageBuilder.withPayload(line);
        builder.copyHeaders(headers);

        Message<String> message = builder.build();

        return message;
    }

    private void sendMessage(Message<String> message) {
        outputChannel.send(message);
    }
}
