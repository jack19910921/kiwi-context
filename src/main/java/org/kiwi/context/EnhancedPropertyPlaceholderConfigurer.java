package org.kiwi.context;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.kiwi.context.Constant.PROFILE_ENV;
import static org.kiwi.context.ProfileUtil.determineProfileEnv;
import static org.kiwi.context.ProfileUtil.generateConfigPath;

/**
 * 配置文件解析
 *
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/20.
 */
@Component
public class EnhancedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Resource> locations = getLocations();
        if (locations.size() == 0) {
            String profileEnv = determineProfileEnv();
            PropertiesHolder.setProperty(PROFILE_ENV, profileEnv);
            // 没有设置location属性,使用kiwi框架默认配置文件
            setLocation(buildClassPathResource(profileEnv));
        }
    }

    private List<Resource> getLocations() throws Exception {
        Field field_locations = null;
        Class<?> clazz = this.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field_locations = clazz.getDeclaredField("locations");
            } catch (Exception e) {
                // ignore
            }
        }
        field_locations.setAccessible(true);
        Resource[] resources = (Resource[]) field_locations.get(this);
        return resources != null ? Arrays.asList(resources) : Collections.<Resource>emptyList();
    }

    private ClassPathResource buildClassPathResource(String profileEnv) {
        return new ClassPathResource(generateConfigPath(profileEnv), Thread.currentThread().getContextClassLoader());
    }

}
