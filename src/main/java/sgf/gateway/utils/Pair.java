package sgf.gateway.utils;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The Class Pair.
 */
public class Pair<K, V> {

    /**
     * The key.
     */
    private K key;

    /**
     * The value.
     */
    private V value;

    /**
     * Instantiates a new pair.
     */
    public Pair() {

    }

    /**
     * Instantiates a new pair.
     *
     * @param key   the key
     * @param value the value
     */
    public Pair(final K key, final V value) {

        super();
        this.key = key;
        this.value = value;
    }

    /**
     * Sets the.
     *
     * @param k the k
     * @param v the v
     */
    public void set(final K k, final V v) {

        this.key = k;
        this.value = v;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public K getKey() {

        return this.key;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public V getValue() {

        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return "[" + getKey() + ", " + getValue() + "]";
    }

    @Override
    public boolean equals(Object objectToCompare) {
        if (objectToCompare instanceof Pair) {
            EqualsBuilder builder = new EqualsBuilder();
            Pair<K, V> otherPair = (Pair<K, V>) objectToCompare;
            builder.append(this.getKey(), otherPair.getKey());
            builder.append(this.getValue(), otherPair.getValue());
            return builder.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.getKey());
        builder.append(this.getValue());
        return builder.toHashCode();
    }

}
