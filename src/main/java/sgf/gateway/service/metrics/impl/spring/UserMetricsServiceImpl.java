package sgf.gateway.service.metrics.impl.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sgf.gateway.dao.metrics.UserMetricsDAO;
import sgf.gateway.model.metrics.*;
import sgf.gateway.model.security.User;
import sgf.gateway.search.api.Constraint;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.Facet;
import sgf.gateway.search.api.SearchResult;
import sgf.gateway.service.metrics.ExceptionStrategy;
import sgf.gateway.service.metrics.UserMetricsService;

import java.util.Date;

public class UserMetricsServiceImpl implements UserMetricsService {

    private static final String DEFAULT_PRODUCT = "All";
    private static final String FREE_TEXT_FACET_NAME = "Text";

    private final UserMetricsDAO userMetricsDAO;
    private final ExceptionStrategy exceptionStrategy;
    private final String typeFacet;

    public UserMetricsServiceImpl(final UserMetricsDAO userMetricsDAO, final ExceptionStrategy exceptionStrategy, final String typeFacet) {

        this.userMetricsDAO = userMetricsDAO;
        this.exceptionStrategy = exceptionStrategy;
        this.typeFacet = typeFacet;
    }

    public void storeUserLogin(User user, UserLoginType userLoginType) {

        storeUserLogin(user, new Date(), userLoginType);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void storeUserLogin(User user, Date loginDate, UserLoginType userLoginType) {

        try {

            tryStoreUserlogin(user, loginDate, userLoginType);

        } catch (Exception e) {

            exceptionStrategy.handleException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void tryStoreUserlogin(User user, Date loginDate, UserLoginType userLoginType) {

        UserLogin userLogin = new UserLogin();
        userLogin.setUser(user);
        userLogin.setLoginDate(loginDate);
        userLogin.setUserLoginType(userLoginType);

        UserMetricsServiceImpl.this.userMetricsDAO.storeUserLogin(userLogin);
    }

    public void storeUserSearch(User user, Criteria criteria, SearchResult searchResult) {

        try {

            tryStoreUserSearch(user, criteria);

        } catch (Exception e) {

            exceptionStrategy.handleException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void tryStoreUserSearch(User user, Criteria criteria) {

        UserSearch userSearch = new UserSearch();

        userSearch.setUser(user);
        userSearch.setCreated(new Date());
        userSearch.setUserSearchType(UserSearchType.SIMPLE_SEARCH);

        String product = DEFAULT_PRODUCT;

        for (Facet facet : criteria.getFacets()) {
            if (facet.getName().equals(typeFacet)) {

                if (null != facet.getConstraints() && !facet.getConstraints().isEmpty()) {
                    Constraint typeConstraint = facet.getConstraints().get(0); // use first constraint to set product
                    product = typeConstraint.getName();
                }
            }
        }

        userSearch.setProduct(product);

        String freeText = criteria.getFreeText();
        if (StringUtils.hasText(freeText)) {

            UserSearchFacet userSearchFacet = new UserSearchFacet();
            userSearchFacet.setName(FREE_TEXT_FACET_NAME);
            userSearchFacet.setValue(freeText);

            userSearch.addUserSearchFacet(userSearchFacet);
        }

        for (Facet facet : criteria.getFacets()) {
            for (Constraint constraint : facet.getConstraints()) {

                UserSearchFacet userSearchFacet = new UserSearchFacet();
                userSearchFacet.setName(facet.getName());
                userSearchFacet.setValue(constraint.getName());

                userSearch.addUserSearchFacet(userSearchFacet);
            }
        }

        userMetricsDAO.storeUserSearch(userSearch);
    }
}
