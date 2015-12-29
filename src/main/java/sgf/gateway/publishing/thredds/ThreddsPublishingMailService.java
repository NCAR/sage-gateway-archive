package sgf.gateway.publishing.thredds;

public interface ThreddsPublishingMailService {
    public void sendFailMessage(ThreddsDatasetDetails details);

    public void sendSuccessMessage(ThreddsDatasetDetails details);
}
