package co.com.bancolombia.soapconsumer;

import java.util.HashMap;
import java.util.Map;

public class StringToStringMap extends HashMap<String, String> {
    private boolean equalsOnThis;

    public StringToStringMap() {
        super();
    }

    public StringToStringMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public StringToStringMap(int initialCapacity) {
        super(initialCapacity);
    }

    public StringToStringMap(Map<? extends String, ? extends String> m) {
        super(m);
    }

    public String get(String key, String defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : value;
    }

    /**
     * Get the inverse of this map.
     */
    public StringToStringMap inverse() {
        StringToStringMap inverse = new StringToStringMap();
        for (String key : keySet()) {
            String value = get(key);
            inverse.put(value, key);
        }
        return inverse;
    }

    public boolean hasValue(String key) {
        return containsKey(key) && get(key).length() > 0;
    }
}