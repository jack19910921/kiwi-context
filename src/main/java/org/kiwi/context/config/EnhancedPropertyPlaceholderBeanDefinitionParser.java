package org.kiwi.context.config;

import org.kiwi.context.EnhancedPropertyPlaceholderConfigurer;
import org.kiwi.context.KiwiConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/20.
 */
public class EnhancedPropertyPlaceholderBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return EnhancedPropertyPlaceholderConfigurer.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String location = element.getAttribute("location");
        if (StringUtils.hasLength(location)) {
            String[] locations = StringUtils.commaDelimitedListToStringArray(location);
            builder.addPropertyValue("locations", locations);
        }

        String fileEncoding = element.getAttribute("file-encoding");
        if (StringUtils.hasLength(fileEncoding)) {
            builder.addPropertyValue("fileEncoding", fileEncoding);
        }

        String order = element.getAttribute("order");
        if (StringUtils.hasLength(order)) {
            builder.addPropertyValue("order", Integer.valueOf(order));
        }

        builder.addPropertyValue("ignoreResourceNotFound",
                Boolean.valueOf(element.getAttribute("ignore-resource-not-found")));

        builder.addPropertyValue("localOverride",
                Boolean.valueOf(element.getAttribute("local-override")));

        /**
         * 基础设施bean,不会被代理
         */
        builder.setRole(ROLE_INFRASTRUCTURE);

        /**
         * 1.提前解析配置文件并缓存、因为kiwi-data在解析标签的时候就要获取数据源配置
         * 2.也可以在web层监听器中做,确保初始化上下文之前完成初始化即可
         */
        KiwiConfig.getInstance();
    }

}
