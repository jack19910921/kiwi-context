package org.kiwi.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.kiwi.context.Constant.PROFILE_ENV;
import static org.kiwi.context.Constant.PROFILE_PRODUCTION;
import static org.kiwi.context.ProfileUtil.*;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/22.
 */
public class KiwiConfig {

    private static final Properties PROPS = new Properties();

    private KiwiConfig() {
        try {
            loadConfigFromClassPath();
        } catch (Exception e) {
            loadConfigFromRelativePath();
        } finally {
            DeployPathHolder.clear();
        }
    }

    private void loadConfigFromClassPath() throws IOException {
        String profileEnv = determineProfileEnv();

        PropertiesHolder.setProperty(PROFILE_ENV, profileEnv);

        String configPath = generateDefaultConfigPath(profileEnv);

        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath);

            PROPS.load(in);

            PropertiesHolder.addProperties(PROPS);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    private void loadConfigFromRelativePath() {
        try {
            PropertiesHolder.setProperty(PROFILE_ENV, PROFILE_PRODUCTION);

            InputStream in = null;
            try {
                String relativeConfigPath = new File("").getAbsolutePath() + File.separator + generateRelativeConfigPath();

                in = new FileInputStream(relativeConfigPath);

                PROPS.load(in);

                PropertiesHolder.addProperties(PROPS);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
            throw new KiwiException("loading config has some problem.", e);
        }
    }

    public Properties getConfig() {
        return PROPS;
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
