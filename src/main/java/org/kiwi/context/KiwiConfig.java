package org.kiwi.context;

import java.io.InputStream;
import java.util.Properties;

import static org.kiwi.context.Constant.PROFILE_ENV;
import static org.kiwi.context.ProfileUtil.determineProfileEnv;
import static org.kiwi.context.ProfileUtil.generateConfigPath;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/22.
 */
public class KiwiConfig {

    private KiwiConfig() {
        try {
            String profileEnv = determineProfileEnv();

            PropertiesHolder.setProperty(PROFILE_ENV, profileEnv);

            String configPath = generateConfigPath(profileEnv);

            InputStream in = null;
            try {
                in = Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath);

                Properties props = new Properties();
                props.load(in);

                PropertiesHolder.addProperties(props);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
            throw new KiwiException("loading config has some problem.", e);
        }
    }

    public static KiwiConfig getInstance() {
        return KiwiConfigHolder.getKiwiConfig();
    }

    private static class KiwiConfigHolder {

        private static final KiwiConfig KIWI_CONFIG = new KiwiConfig();

        public static KiwiConfig getKiwiConfig() {
            return KIWI_CONFIG;
        }

    }
}
