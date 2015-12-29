package sgf.gateway.integration.node;

public interface Node<T> {
    void process(T payload);
}
