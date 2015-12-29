package sgf.gateway.service.workspace.impl.spring;

import sgf.gateway.service.workspace.StorageResourceControl;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory methods for building/accessing StorageResourceControl for various deep storage capabilities. Currently requires Spring configured single instance for
 * access (ie. no static access is currently supported.)
 *
 * @author ejn
 */
public class SubsystemResourceFactory {

    private static Map<String, StorageResourceControl> storageControlMap = new HashMap<>();

    public SubsystemResourceFactory() {

    }

    public static StorageResourceControl getStorageResourceControl(String key) {

        StorageResourceControl control = storageControlMap.get(key);

        if (control == null) {

            control = addDefaultCapability(key);
        }

        return control;
    }

    private static StorageResourceControl addDefaultCapability(String key) {

        synchronized (storageControlMap) {

            MockStorageResourceControl mockControl = new MockStorageResourceControl();

            storageControlMap.put(key, mockControl);

            return mockControl;
        }
    }

	/*
	 * public Map<String,StorageResourceControl> getMap() {
	 * 
	 * return storageControlMap; }
	 */

    public void setStorageControlMap(Map map) {

        storageControlMap = map;
    }
}
