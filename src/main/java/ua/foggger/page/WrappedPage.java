package ua.foggger.page;

import java.lang.reflect.Proxy;

public class WrappedPage {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T create(Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz, IPage.class}, new PageInvocationHandler());
    }
}
