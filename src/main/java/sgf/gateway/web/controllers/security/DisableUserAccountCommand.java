package sgf.gateway.web.controllers.security;

public class DisableUserAccountCommand {

    private boolean disable; // true to disable, false to re-enable

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
