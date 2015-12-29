package sgf.gateway.web.controllers.project.command;

import sgf.gateway.model.metadata.Dataset;

public class NewAwardCommand {

    private Dataset dataset;
    private String awardNumber;

    public NewAwardCommand(Dataset dataset) {

        this.dataset = dataset;
    }

    public Dataset getDataset() {

        return dataset;
    }

    public void setDataset(Dataset dataset) {

        this.dataset = dataset;
    }

    public String getAwardNumber() {

        return awardNumber;
    }

    public void setAwardNumber(String awardNumber) {

        this.awardNumber = awardNumber;
    }
}
