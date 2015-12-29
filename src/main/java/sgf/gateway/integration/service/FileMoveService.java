package sgf.gateway.integration.service;

import java.io.File;

public class FileMoveService {

    private final File destination;

    public FileMoveService(File destination) {
        super();
        this.destination = destination;
    }

    public File move(File payload) {

        makeDestination(destination);

        File movedPayload = new File(destination, payload.getName());

        Boolean success = payload.renameTo(movedPayload);

        if (!success) {
            throw new RuntimeException("Couldn't move " + payload + " to " + movedPayload);
        }

        return movedPayload;
    }

    private void makeDestination(File destination) {

        if (!destination.exists()) {

            Boolean success = destination.mkdirs();

            if (!success) {
                throw new RuntimeException("Couldn't make directory " + destination.getAbsolutePath());
            }
        }
    }
}
