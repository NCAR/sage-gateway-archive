package sgf.gateway.integration.node;

public class NodeSwitch<T> implements Node<T>, Condition<T> {

    private final Condition<T> condition;
    private final Node<T> node;

    public NodeSwitch(Condition<T> condition, Node<T> node) {
        super();
        this.condition = condition;
        this.node = node;
    }

    @Override
    public Boolean evaluate(T payload) {
        return condition.evaluate(payload);
    }

    @Override
    public void process(T payload) {
        node.process(payload);
    }
}
