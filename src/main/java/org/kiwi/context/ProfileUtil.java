package org.kiwi.context;

import java.io.File;

import static org.kiwi.context.Constant.*;
import static org.kiwi.context.Utils.isBlank;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/22.
 */
public class ProfileUtil {

    public static String determineProfileEnv() {
        String profileEnv = PropertiesHolder.getProperty(PROFILE_ENV);

        if (isBlank(profileEnv)) {
            profileEnv = PROFILE_PRODUCTION;
        }

        if (!PROFILE_DEVELOPMENT.equalsIgnoreCase(profileEnv) && !PROFILE_PRODUCTION.equalsIgnoreCase(profileEnv)
                && !PROFILE_REGRESSIONTEST.equalsIgnoreCase(profileEnv)) {
            throw new KiwiException("profile.env=[" + profileEnv + "] is invalid.");
        }

        return profileEnv;
    }

    public static String generateConfigPath(String profileEnv) {
        return CONFIG +
                File.separator + profileEnv +
                File.separator + CONFIG_PROPERTIES;
    }

}
