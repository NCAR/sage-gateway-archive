package sgf.gateway.model.metadata.inventory.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sgf.gateway.model.metadata.inventory.LogicalFile;

public class LogicalFileValidator implements Validator {

    /**
     * Configuration flag to control checking of parent dataset.
     */
    private boolean checkDatasetParent = false;

    /**
     * Configuration flag to control checking of file access points. Flip the default when we are creating file access points.
     */
    private boolean checkAccessPoint = true;

    /**
     * Configuration flag to control checking file size. Currently we are checking to make sure a file's size is > 0.
     */
    private boolean checkFileSize = true;

    /**
     * {@inheritDoc}
     */
    public boolean supports(Class clazz) {
        return LogicalFile.class.isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    public void validate(Object object, Errors errors) {

        LogicalFile file = (LogicalFile) object;

        if (checkDatasetParent) {
            if (file.getDataset() == null) {
                errors.reject("validation.missing_parent_dataset", "An enclosing dataset must be specified for a file.");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.missing_name", "A name must be specified for a file.");

        // Files must have a primary identifier.
        // PersistentIdentifier primaryIdentifier = file.getIdentifierOfType(IdentifierType.ID);
        //
        // if (null == primaryIdentifier) {
        // errors.reject("validation.missing_primary_identifier", "A primary identifier must be specified for all files.");
        // }

        if (checkFileSize) {
            if (file.getSize() == 0) {
                errors.rejectValue("size", "validation.0_size", "A file must have a size greater than 0.");
            }
        }

        if (checkAccessPoint) {
            if (file.getFileAccessPoints().isEmpty()) {
                errors.reject("validation.missing_file_access_point", "A file must have at least one access point.");
            }
        }

    }

    /**
     * @return the checkDatasetParent
     */
    public boolean isCheckDatasetParent() {
        return this.checkDatasetParent;
    }

    /**
     * @param checkDatasetParent the checkDatasetParent to set
     */
    public void setCheckDatasetParent(boolean checkDatasetParent) {
        this.checkDatasetParent = checkDatasetParent;
    }

    /**
     * @return the checkAccessPoint
     */
    public boolean isCheckAccessPoint() {
        return this.checkAccessPoint;
    }

    /**
     * @param checkAccessPoint the checkAccessPoint to set
     */
    public void setCheckAccessPoint(boolean checkAccessPoint) {
        this.checkAccessPoint = checkAccessPoint;
    }

    /**
     * @return the checkFileSize
     */
    public boolean isCheckFileSize() {
        return this.checkFileSize;
    }

    /**
     * @param checkFileSize the checkFileSize to set
     */
    public void setCheckFileSize(boolean checkFileSize) {
        this.checkFileSize = checkFileSize;
    }

}
