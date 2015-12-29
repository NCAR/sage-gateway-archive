package sgf.gateway.web.controllers.download;

import java.util.ArrayList;
import java.util.List;

public class FileFilterDefinition {

    private List<String> DatasetId;

    private List<String> variableName;

    private List<String> standardVariableName;

    private String fileNameFilter;

    public FileFilterDefinition() {

        this.DatasetId = new ArrayList<String>();
        this.variableName = new ArrayList<String>();
        this.standardVariableName = new ArrayList<String>();
    }

    public List<String> getDatasetId() {
        return DatasetId;
    }

    public void setDatasetId(List<String> datasetIdentifiers) {
        DatasetId = datasetIdentifiers;
    }

    public List<String> getVariableName() {
        return variableName;
    }

    public void setVariableName(List<String> variableNames) {
        this.variableName = variableNames;
    }

    public List<String> getStandardVariableName() {
        return standardVariableName;
    }

    public void setStandardVariableName(List<String> standardVariableNames) {
        this.standardVariableName = standardVariableNames;
    }

    public String getFileNameFilter() {
        return fileNameFilter;
    }

    public void setFileNameFilter(String fileNameFilter) {
        this.fileNameFilter = fileNameFilter;
    }

}
