package org.kiwi.context;

/**
 * 存放部署目录绝对路径
 *
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/6/26.
 */
public class DeployPathHolder {

    private static final ThreadLocal<String> PATH_HOLDER = new ThreadLocal<String>();

    public static void setDeployPath(String deployPath) {
        PATH_HOLDER.set(deployPath);
    }

    public static String getDeployPath() {
        return PATH_HOLDER.get();
    }

    public static void clear() {
        PATH_HOLDER.set(null);
    }

}
