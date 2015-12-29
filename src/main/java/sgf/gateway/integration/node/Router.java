package sgf.gateway.integration.node;

import java.util.List;

public class Router<T> implements Node<T> {

    private List<NodeSwitch<T>> nodeSwitches;
    private Node<T> defaultNode;

    public Router(List<NodeSwitch<T>> nodeSwitches, Node<T> defaultNode) {
        this.nodeSwitches = nodeSwitches;
        this.defaultNode = defaultNode;
    }

    @Override
    public void process(T payload) {

        Node<T> next = defaultNode;

        for (NodeSwitch<T> transformSwitch : nodeSwitches) {
            if (transformSwitch.evaluate(payload)) {
                next = transformSwitch;
                break;
            }
        }

        next.process(payload);
    }
}
