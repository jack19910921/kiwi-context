package org.kiwi.context.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/20.
 */
public class EnhancedContextNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("property-placeholder", new EnhancedPropertyPlaceholderBeanDefinitionParser());
    }

}