package sgf.gateway.dao.metrics;

import sgf.gateway.model.metrics.UserLogin;
import sgf.gateway.model.metrics.UserSearch;

public interface UserMetricsDAO {

    /**
     * Store user login.
     *
     * @param userLogin the user login
     */
    void storeUserLogin(UserLogin userLogin);

    /**
     * Store user search.
     *
     * @param userSearch the user search
     */
    void storeUserSearch(UserSearch userSearch);
}
