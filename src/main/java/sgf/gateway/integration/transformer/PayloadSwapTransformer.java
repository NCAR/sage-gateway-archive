package sgf.gateway.integration.transformer;

public class PayloadSwapTransformer {

    private final Object newPayload;

    public PayloadSwapTransformer(Object newPayload) {
        super();
        this.newPayload = newPayload;
    }

    public Object transform(Object oldPayload) {
        return newPayload;
    }
}
