package org.springframework.orm.hibernate4.support;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * This class is used to stop the opening of database connection for static resources, like, JavaScript, css, or images.
 *
 * For more information:
 * http://basrikahveci.com/extending-springs-opensessioninviewfilter-to-not-open-sessions-for-request-to-static-resources/
 */
public class ExtendedOpenSessionInViewFilter extends OpenSessionInViewFilter {

    private Collection<ShouldNotFilter> shouldNotFilterCollection;

    public ExtendedOpenSessionInViewFilter(Collection<ShouldNotFilter> shouldNotFilterCollection) {

        this.shouldNotFilterCollection = shouldNotFilterCollection;
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        boolean check = false;

        for (ShouldNotFilter shouldNotFilter : this.shouldNotFilterCollection) {

            if (shouldNotFilter.shouldNotFilter(request.getRequestURI())) {

                check = true;
                break;
            }
        }

        return check;
    }
}
