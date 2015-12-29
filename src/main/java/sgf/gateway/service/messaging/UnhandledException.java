package sgf.gateway.service.messaging;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class UnhandledException extends RuntimeException {

    private final Map<String, String> map;

    public UnhandledException(Throwable cause) {

        super(cause);

        this.map = new LinkedHashMap<>();
    }

    public UnhandledException(String message) {

        super(message);
        this.map = new LinkedHashMap<>();
    }

    public UnhandledException(String message, Throwable cause) {

        super(message, cause);
        this.map = new LinkedHashMap<>();
    }

    public String get(String key) {

        return this.map.get(key);
    }

    public void put(String key, String value) {

        this.map.put(key, value);
    }

    public Set<String> keySet() {

        return this.map.keySet();
    }

    public Collection<String> values() {

        return this.map.values();
    }
}
