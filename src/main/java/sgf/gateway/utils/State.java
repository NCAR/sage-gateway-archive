package sgf.gateway.utils;

/**
 * The job must be in one of the five states listed in this enumerated type.
 */
public enum State {
    /**
     * The job completed normally.
     */
    SUCCESSFUL,
    /**
     * The job completed with errors and or exceptions (including cancellation).
     */
    UNSUCCESSFUL,
    /**
     * The job is currently processing, and has not completed yet.
     */
    PROCESSING,
    /**
     * The status of the job is unknown because there is no information on the requested job.
     */
    UNKNOWN
}