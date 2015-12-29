package sgf.gateway.integration.node;

public interface Condition<T> {
    Boolean evaluate(T payload);
}
