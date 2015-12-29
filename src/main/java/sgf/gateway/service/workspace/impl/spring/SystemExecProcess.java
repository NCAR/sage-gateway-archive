package sgf.gateway.service.workspace.impl.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * System Exec Process Utility using managed thread pool for std out/err processing
 * <p/>
 * TODO: move to Util package.
 */
public class SystemExecProcess {

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    private Log logger = LogFactory.getLog(SystemExecProcess.class);

    public SystemExecProcess() {

    }

    /**
     * Runs command as a runtime executed process, reads process output streams and cleans up
     *
     * @param command the system command and args to run
     * @return process output containing standard output and standard error
     * @throws IOException
     * @throws InterruptedException
     */
    public ProcessOutput executeCommand(String workingDirectory, String command) throws IOException, InterruptedException {

        File directory = null;

        String[] environment = null;

        if (workingDirectory != null) {

            directory = new File(workingDirectory);
        }

        // TODO maybe use ProcessBuilder instead of exec to get a process

        Process copyProcess = Runtime.getRuntime().exec(command.toString(), environment, directory);

        ProcessStreamReader standardOutput = new ProcessStreamReader(copyProcess.getInputStream());
        ProcessStreamReader standardError = new ProcessStreamReader(copyProcess.getErrorStream());

        executeAndWait(standardOutput);
        executeAndWait(standardError);

        int returnCode = copyProcess.waitFor();

        ProcessOutput processOutput = new ProcessOutput();

        processOutput.setExitValue(returnCode);

        String output = standardOutput.getOutput();

        if ((output != null) && (output.length() > 0)) {

            processOutput.setOutput(output.toString());
        }

        cleanupProcess(copyProcess);

        return processOutput;
    }

    /**
     * runs command on executor (thread pool), waits for result
     *
     * @param runnable the command to run
     */
    private void executeAndWait(final Runnable runnable) {

        Callable callable = new Callable() {
            public Object call() {
                runnable.run();
                return runnable;
            }
        };

        Future future = executorService.submit(callable);

        try {

            // simple wait until complete
            // TODO: perhaps this should include a timeout

            future.get();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {

            //
        }
    }

    private void cleanupProcess(Process process) throws IOException {

        // cleanup process resources, output streams should close.

        process.getInputStream().close();
        process.getOutputStream().close();
        process.getErrorStream().close();

        process.destroy();
    }

    /**
     * Reads input stream until EOF or exception, stores result in string
     */
    class ProcessStreamReader implements Runnable {

        private InputStream inputStream = null;
        private String output = null;

        public ProcessStreamReader(InputStream inputStream) {

            this.inputStream = inputStream;
        }

        public void run() {

            StringBuilder sb = new StringBuilder();

            char[] buffer = new char[1024];

            int bytesRead;

            InputStreamReader reader = new InputStreamReader(inputStream);

            try {

                while ((bytesRead = reader.read(buffer, 0, 1024)) != -1) {

                    sb.append(buffer, 0, bytesRead);

                }

                output = sb.toString();
            } catch (IOException ioe) {

                logger.error("could not read process output: " + ioe);

                output = null;
            }
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }
    }
}
