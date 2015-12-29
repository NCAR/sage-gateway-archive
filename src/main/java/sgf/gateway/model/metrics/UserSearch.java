package sgf.gateway.model.metrics;

import sgf.gateway.model.AbstractPersistableEntity;
import sgf.gateway.model.security.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserSearch extends AbstractPersistableEntity {

    private User user;

    private Date created;

    private String product;

    private UserSearchType userSearchType;

    private Set<UserSearchFacet> userSearchFacetSet = new HashSet<>();

    public UserSearch() {

        super(true);
    }

    public User getUser() {

        return this.user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    /**
     * Gets the created.
     *
     * @return the created
     */
    public Date getCreated() {

        return this.created;
    }

    /**
     * Sets the created.
     *
     * @param date the new created
     */
    public void setCreated(Date date) {

        this.created = date;
    }

    /**
     * Gets the product.
     *
     * @return the product
     */
    public String getProduct() {

        return this.product;
    }

    /**
     * Sets the product.
     *
     * @param text the new product
     */
    public void setProduct(String text) {

        this.product = text;
    }

    /**
     * Gets the user search type.
     *
     * @return the user search type
     */
    public UserSearchType getUserSearchType() {

        return this.userSearchType;
    }

    /**
     * Sets the user search type.
     *
     * @param userSearchType the new user search type
     */
    public void setUserSearchType(UserSearchType userSearchType) {

        this.userSearchType = userSearchType;
    }

    /**
     * Gets the user search facets.
     *
     * @return the user search facets
     */
    public Set<UserSearchFacet> getUserSearchFacets() {

        return this.userSearchFacetSet;
    }

    /**
     * Sets the user search facets.
     *
     * @param userSearchFacets the new user search facets
     */
    public void setUserSearchFacets(Set<UserSearchFacet> userSearchFacets) {

        this.userSearchFacetSet = userSearchFacets;
    }

    /**
     * Adds the user search facet.
     *
     * @param userSearchFacet the user search facet
     */
    public void addUserSearchFacet(UserSearchFacet userSearchFacet) {

        userSearchFacet.setUserSearch(this);
        getUserSearchFacets().add(userSearchFacet);
    }
}
