package sgf.gateway.web.controllers;

public class BroadcastMessageCommand implements BroadcastMessageRequest {

    private String messageText;
    private String bannerColor;

    public BroadcastMessageCommand() {
    }

    public void setMessageText(String messageText) {

        this.messageText = messageText;
    }

    public String getMessageText() {

        return messageText;
    }

    public String getBannerColor() {
        return bannerColor;
    }

    public void setBannerColor(String bannerColor) {
        this.bannerColor = bannerColor;
    }

}
