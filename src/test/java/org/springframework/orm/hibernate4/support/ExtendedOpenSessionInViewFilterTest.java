package org.springframework.orm.hibernate4.support;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExtendedOpenSessionInViewFilterTest {

    @Test
    public void testShouldNotFilter() throws Exception {

        assertThat(filter(true, false, false), is(true));
        assertThat(filter(false, false, true), is(true));
        assertThat(filter(false, true, false), is(true));
    }

    @Test
    public void testShouldFilter() throws Exception {

        assertThat(filter(false, false, false), is(false));
    }

    public boolean filter(boolean... filters) throws Exception {

        ExtendedOpenSessionInViewFilter filter = new ExtendedOpenSessionInViewFilter(this.createShouldNotFilterCollection(filters));

        HttpServletRequest stubRequest = mock(HttpServletRequest.class);
        when(stubRequest.getRequestURI()).thenReturn("/js/");

        return filter.shouldNotFilter(stubRequest);
    }

    public Collection<ShouldNotFilter> createShouldNotFilterCollection(boolean... filters) {

        Collection<ShouldNotFilter> collection = new ArrayList<>();

        for (boolean filter : filters) {

            collection.add(new StubShouldNotFilter(filter));
        }

        return collection;
    }


    private class StubShouldNotFilter implements ShouldNotFilter {

        private boolean filter;

        public StubShouldNotFilter(boolean filter) {

            this.filter = filter;
        }

        @Override
        public boolean shouldNotFilter(String uri) {
            return this.filter;
        }
    }
}
