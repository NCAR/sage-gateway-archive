package sgf.gateway.model.metrics;

import sgf.gateway.model.security.User;

import java.io.Serializable;
import java.util.Date;

public class UserLogin implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1;

    /**
     * The user.
     */
    private User user;

    /**
     * The login date.
     */
    private Date loginDate;

    /**
     * The login type.
     */
    private UserLoginType userLoginType;

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the login date.
     *
     * @return the login date
     */
    public Date getLoginDate() {
        return this.loginDate;
    }

    /**
     * Sets the login date.
     *
     * @param loginDate the new login date
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * Gets the user login type.
     *
     * @return the user login type
     */
    public UserLoginType getUserLoginType() {
        return this.userLoginType;
    }

    /**
     * Sets the user login type.
     *
     * @param userLoginType the new user login type
     */
    public void setUserLoginType(UserLoginType userLoginType) {
        this.userLoginType = userLoginType;
    }
}
