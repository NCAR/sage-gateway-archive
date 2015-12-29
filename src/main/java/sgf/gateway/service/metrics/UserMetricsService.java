package sgf.gateway.service.metrics;

import sgf.gateway.model.metrics.UserLoginType;
import sgf.gateway.model.security.User;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.SearchResult;

import java.util.Date;

/**
 * The Interface UserMetricsService.
 */
public interface UserMetricsService {

    /**
     * Store user login.
     *
     * @param user          the user
     * @param userLoginType the user login type
     */
    void storeUserLogin(User user, UserLoginType userLoginType);

    /**
     * Store user search.
     *
     * @param user           the user
     * @param searchResult   the search result
     */
    void storeUserSearch(User user, Criteria criteria, SearchResult searchResult);

}
