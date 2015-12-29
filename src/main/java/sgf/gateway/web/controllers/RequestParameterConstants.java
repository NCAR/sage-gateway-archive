package sgf.gateway.web.controllers;

/**
 * The Class RequestParameterConstants.
 */
public abstract class RequestParameterConstants {

    // URL parameter values.

    /**
     * The Constant PROJECT_ID_PARAM_KEY - The UUID identifier value for a project.
     */
    public static final String PROJECT_ID_PARAMETER_VALUE = "projectId";

    /**
     * The Constant PROJECT_URI - The URI identifier value for a project.
     */
    public static final String PROJECT_URI = "projectURI";

    /**
     * The Constant CATALOG - The catalog type to return.
     */
    public static final String CATALOG = "catalog";

    /**
     * The Constant RESOURCE_ID_PARAM_KEY - The identifier value for a resource..
     */
    public static final String RESOURCE_ID_PARAMETER_VALUE = "resourceID";

    /**
     * The Constant ANNOTATION_ID_PARAMETER_VALUE - The identifier value for an annotation.
     */
    public static final String ANNOTATION_ID_PARAMETER_VALUE = "annotationID";

    /**
     * The Constant ACTIVITY_ID_PARAMETER_VALUE - The UUID identifier value for an activity.
     */
    public static final String ACTIVITY_ID_PARAMETER_VALUE = "activityId";

    /**
     * The Constant DATASET_ID_PARAMETER_VALUE - The UUID identifier value for a dataset.
     */
    public static final String DATASET_ID_PARAMETER_VALUE = "datasetId";

    /**
     * The Constant DATASET_VERSION_ID_PARAMETER_VALUE - The UUID identifier value for a DatasetVersion instance.
     */
    public static final String DATASET_VERSION_ID_PARAMETER_VALUE = "datasetVersionId";

    /**
     * The UUID identifier value for the parent of a new dataset.
     */
    public static final String PARENT_DATASET_ID_PARAMETER_VALUE = "parentDatasetId";

    /**
     * The Constant LICENSE_ID_PARAMETER_VALUE - The UUID identifier value for a license.
     */
    public static final String LICENSE_ID_PARAMETER_VALUE = "licenseId";

    /**
     * The Constant LOGICAL_FILE_ID_PARAMETER_VALUE - The UUID identifier value for a logical file.
     */
    public static final String LOGICAL_FILE_ID_PARAMETER_VALUE = "logicalFileId";

    /**
     * The Constant FILE_ACCESS_POINT_ID_PARAMETER_VALUE - The UUID identifier value for a file access point.
     */
    public static final String FILE_ACCESS_POINT_ID_PARAMETER_VALUE = "fileAccessPointId";

    /**
     * The Constant DATA_TRANSFER_REQUEST_ID_VALUE - The UUID identifier value for a data transfer request.
     */
    public static final String DATA_TRANSFER_REQUEST_ID_VALUE = "dataTransferRequestId";

    /**
     * The Constant DATA_TRANSFER_ITEM_ID_VALUE - The UUID identifier value for a data transfer item.
     */
    public static final String DATA_TRANSFER_ITEM_ID_VALUE = "dataTransferItemId";

    /**
     * The Constant TOPIC_ID - The UUID identifier value for a topic.
     */
    public static final String TOPIC_ID = "topicId";

    /**
     * The Constant CADIS_TOPIC_ID = The UUID indentifier value for a cadis discipline topic
     */
    public static final String CADIS_TOPIC_ID = "cadisTopicId";

    /**
     * The Constant LOCATION_ID - The UUID identifier value for a location.
     */
    public static final String LOCATION_ID = "locationId";

    /**
     * The Constant AUTHORIZATION_TOKEN_PARAMETER_VALUE - The authorizationToken value.
     */
    public static final String AUTHORIZATION_TOKEN = "authzToken";

    /**
     * The Constant GATEWAY_NAME - The gateway name.
     */
    public static final String GATEWAY_NAME = "gateway";

    /**
     * The Constant CONTACT_ID - The UUID identifier of the contact.
     */
    public static final String CONTACT_ID = "contactId";

    /**
     * The Constant PAGE_FLOW - The request attribute key for page redirection.
     */
    public static final String PAGE_FLOW = "pageFlow";

    /**
     * The constant WORKSPACE_ID, holding a workspace UUID.
     */
    public static final String WORKSPACE_ID = "workspaceId";

    /**
     * The constant ID, holding a multi-purpose identifier for a resource.
     */
    public static final String ID = "id";

    /**
     * The constant REFERRAL, holding the URL of another application that referred to the current one, for the purpose of browsing back while keeping state.
     */
    public static final String REFERRAL = "referral";

    public static final String DAC_REQUEST_VALUE_ID = "dacId";

}
