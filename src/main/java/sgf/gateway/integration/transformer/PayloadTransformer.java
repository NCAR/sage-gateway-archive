package sgf.gateway.integration.transformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class PayloadTransformer {

    private final Class payloadClass;
    private final String payloadMethod;

    public PayloadTransformer(String payloadClassName) throws ClassNotFoundException {
        super();
        this.payloadClass = Class.forName(payloadClassName);
        this.payloadMethod = null;
    }

    public PayloadTransformer(String payloadClassName, String payloadMethod) throws ClassNotFoundException {
        super();
        this.payloadClass = Class.forName(payloadClassName);
        this.payloadMethod = payloadMethod;
    }

    public Object transform(Object inputPayload) throws Exception {

        Object outputPayload;

        if (null == payloadMethod) {
            outputPayload = transformConstructor(inputPayload);
        } else {
            outputPayload = transformMethod(inputPayload);
        }

        return outputPayload;
    }

    private Object transformConstructor(Object inputPayload) throws Exception {

        Constructor outputPayloadConstructor = payloadClass.getConstructor(inputPayload.getClass());

        Object outputPayload = outputPayloadConstructor.newInstance(inputPayload);

        return outputPayload;
    }

    private Object transformMethod(Object inputPayload) throws Exception {

        Method outputPayloadMethod = payloadClass.getMethod(payloadMethod, inputPayload.getClass());
        Object outputPayload = payloadClass.newInstance();

        outputPayloadMethod.invoke(outputPayload, inputPayload);

        return outputPayload;
    }
}
