package org.springframework.orm.hibernate4.support;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StartsWithShouldNotFilterTest {

    private StartsWithShouldNotFilter filter;

    @Before
    public void setup() {
        this.filter = new StartsWithShouldNotFilter("/js/");
    }

    @Test
    public void testShouldNotFilter() {

        assertThat(filter.shouldNotFilter("/js/"), is(true));
    }

    @Test
    public void testShouldFilter() {

        assertThat(filter.shouldNotFilter("/JS/"), is(false));
        assertThat(filter.shouldNotFilter("/Js/"), is(false));
        assertThat(filter.shouldNotFilter("js/"), is(false));
        assertThat(filter.shouldNotFilter("/js"), is(false));
    }
}
