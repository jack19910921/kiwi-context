package org.kiwi.context;

import org.springframework.context.ApplicationContext;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/21.
 */
public class ApplicationContextHolder {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 容器启动时设置上下文对象
     *
     * @param applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

}
