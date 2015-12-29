package sgf.gateway.web.controllers.cadis.publish.command;

public class CadisMoveDatasetCommand {

    private String datasetToMoveShortName;
    private String newParentDatasetShortName;

    public String getDatasetToMoveShortName() {
        return datasetToMoveShortName;
    }

    public void setDatasetToMoveShortName(String datasetToMoveShortName) {
        this.datasetToMoveShortName = datasetToMoveShortName;
    }

    public String getNewParentDatasetShortName() {
        return newParentDatasetShortName;
    }

    public void setNewParentDatasetShortName(String newParentDatasetShortName) {
        this.newParentDatasetShortName = newParentDatasetShortName;
    }

}
