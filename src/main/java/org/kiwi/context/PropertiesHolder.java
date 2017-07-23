package org.kiwi.context;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/21.
 */
public class PropertiesHolder {

    private static final Map<String, String> PROPS = new ConcurrentHashMap<>();

    static {
        // 1.加载操作系统环境变量
        PROPS.putAll(System.getenv());
        // 2.加载jvm环境变量
        putIfNecessary(System.getProperties());
    }

    public static Map<String, String> getProperties() {
        return PROPS;
    }

    public static String getProperty(String key) {
        return PROPS.get(key);
    }

    public static String setProperty(String key, String value) {
        return PROPS.put(key, value);
    }

    public static void addProperties(Properties props) {
        putIfNecessary(props);
    }

    private static void putIfNecessary(Properties props) {
        if (props != null) {
            Iterator<Map.Entry<Object, Object>> iterator = props.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Object, Object> entry = iterator.next();
                PROPS.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
    }

}
