package sgf.gateway.web.controllers.publish;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.Activity;
import sgf.gateway.validation.data.ShortNameCharacters;
import sgf.gateway.validation.data.TitleCharacters;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.persistence.AssertUniqueShortName;
import sgf.gateway.validation.persistence.AssertUniqueTitle;
import sgf.gateway.validation.persistence.ShortNameAvailable;
import sgf.gateway.validation.persistence.ShortNameExists;

import javax.validation.GroupSequence;

/**
 * Command bean used by the CreateNewDatasetController.
 */
@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, CreateNewDatasetCommand.class})

public class CreateNewDatasetCommand {

    /**
     * Optional parent of dataset to be created.
     */
    public Dataset parentDataset;

    /**
     * Optional Activity to be associated with the dataset to be created.
     */
    public Activity activity;

    @NotBlank(groups = Required.class, message = "Title is required.")
    @TitleCharacters(groups = Data.class, message = "Title may only contain the letters a-z, A-Z, numbers, spaces and the characters: - _ . ? ( )")
    public String datasetTitle;

    @NotBlank(groups = Required.class, message = "Short Name is required.")
    @ShortNameCharacters(groups = Data.class, message = "Short Name may only contain the letters a-z, A-Z, numbers, and the characters: $ - _ . ! * ' ( ) ,")
    @ShortNameAvailable(groups = Persistence.class, message = "Short Name must be unique.")
    public String datasetShortName;

    public String authoritativeIdentifier;

    private String doi;

    /**
     * The specific types to be associated with the dataset, if any.
     */
    public String[] datasetTypes = new String[]{};

    public Dataset getParentDataset() {
        return parentDataset;
    }

    public void setParentDataset(Dataset parentDataset) {
        this.parentDataset = parentDataset;
    }

    public String getDatasetTitle() {
        return datasetTitle;
    }

    public void setDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
    }

    public String getDatasetShortName() {
        return datasetShortName;
    }

    public void setDatasetShortName(String datasetShortName) {
        this.datasetShortName = datasetShortName;
    }

    public String getAuthoritativeIdentifier() {
        return authoritativeIdentifier;
    }

    public void setAuthoritativeIdentifier(String authoritativeIdentifier) {
        this.authoritativeIdentifier = authoritativeIdentifier;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String[] getDatasetTypes() {
        return datasetTypes;
    }

    public void setDatasetTypes(String[] datasetTypes) {
        this.datasetTypes = datasetTypes;
    }

    public String getDoi() {

        return this.doi;
    }

    public void setDoi(String doi) {

        this.doi = doi;
    }

}
