package org.springframework.orm.hibernate4.support;

public class StartsWithShouldNotFilter implements ShouldNotFilter {

    private String prefix;

    public StartsWithShouldNotFilter(String prefix) {

        this.prefix = prefix;
    }

    @Override
    public boolean shouldNotFilter(String uri) {

        return uri.startsWith(this.prefix);
    }
}
