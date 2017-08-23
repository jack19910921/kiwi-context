package org.kiwi.context;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.kiwi.context.Constant.PROFILE_ENV;
import static org.kiwi.context.ProfileUtil.*;

/**
 * 配置文件解析
 *
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/20.
 */
public class EnhancedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Resource> locations = getLocations();
        if (locations.size() == 0) {
            String profileEnv = determineProfileEnv();
            PropertiesHolder.setProperty(PROFILE_ENV, profileEnv);

            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(generateDefaultConfigPath(profileEnv));
            /** 校验类路径是否存在配置 */
            if (inputStream != null) {
                try {
                    /** 类路径存在配置,并且没有设置location属性,使用kiwi框架默认配置文件 */
                    setLocation(buildClassPathResource(profileEnv));
                } finally {
                    inputStream.close();
                }
            } else {
                /** 类路径不存在配置,使用kiwi框架默认相对路径配置文件 */
                setLocation(buildFileSystemResource());
            }
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
        return new ClassPathResource(generateDefaultConfigPath(profileEnv), Thread.currentThread().getContextClassLoader());
    }

    private FileSystemResource buildFileSystemResource() {
        return new FileSystemResource(generateAbsoluteConfigPath());
    }

}
