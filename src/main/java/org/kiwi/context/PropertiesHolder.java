package org.kiwi.context;

import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/21.
 */
public class PropertiesHolder {

    private static final Map<String, String> PROPERTIES = new ConcurrentHashMap<>();

    static {
        // 1.加载操作系统环境变量
        PROPERTIES.putAll(System.getenv());
        // 2.加载jvm环境变量
        putIfNecessary(System.getProperties());
    }

    public static Map<String, String> getProperties() {
        return PROPERTIES;
    }

    public static String getProperty(String key) {
        return PROPERTIES.get(key);
    }

    public static String setProperty(String key, String value) {
        return PROPERTIES.put(key, value);
    }

    public static void addProperties(Properties props) {
        putIfNecessary(props);
    }

    private static void putIfNecessary(Properties props) {
        Assert.notNull(props, "props must not be null");

        Iterator<Map.Entry<Object, Object>> iterator = props.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> entry = iterator.next();
            PROPERTIES.put(entry.getKey().toString(), entry.getValue().toString());
        }
    }

}
