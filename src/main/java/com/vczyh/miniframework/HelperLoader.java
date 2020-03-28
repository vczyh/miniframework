package com.vczyh.miniframework;

import com.vczyh.miniframework.core.ClassHelper;
import com.vczyh.miniframework.helper.ControllerHelper;
import com.vczyh.miniframework.ioc.BeanHelper;
import com.vczyh.miniframework.ioc.IocHelper;
import com.vczyh.miniframework.util.ClassUtil;

public class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
            ClassHelper.class,
            BeanHelper.class,
            IocHelper.class,
            ControllerHelper.class,
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }

    }
}
