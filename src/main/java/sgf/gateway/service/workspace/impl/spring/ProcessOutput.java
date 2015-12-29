package sgf.gateway.service.workspace.impl.spring;

public class ProcessOutput {

    private int exitValue = 0;
    private String output = null;
    private String error = null;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getExitValue() {
        return exitValue;
    }

    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
