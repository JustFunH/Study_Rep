package com.meguru.container;

import com.meguru.annotation.AutoWired;
import com.meguru.annotation.Bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private Map<Class<?>, Method> methods;
    private Object config;

    private Map<Class<?>, Object> services;

    /**
     * 注册服务实例
     */
    public void init() throws Exception {
        this.methods = new HashMap<>();
        this.services = new HashMap<>();
        Class<?> clazz = Class.forName("com.meguru.cofig.Config");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getDeclaredAnnotation(Bean.class) != null) {
                this.methods.put(method.getReturnType(), method);
            }
        }
        this.config = clazz.getConstructor().newInstance();
    }

    /**
     * 获取对应服务实例
     *
     * @param clazz
     * @return
     */
    public Object getServiceInstanceByClass(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        if (this.services.containsKey(clazz))
            return services.get(clazz);
        if (this.methods.containsKey(clazz)) {
            Method method = this.methods.get(clazz);
            Object obj = method.invoke(this.config);
            this.services.put(clazz, obj);
            return obj;
        }
        return null;
    }

    /**
     * 注入服务实例
     *
     * @param clazz
     * @return
     */
    public Object createInstance(Class<?> clazz) throws Exception {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getDeclaredAnnotation(AutoWired.class) == null)
                continue;
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++)
                args[i] = getServiceInstanceByClass(parameterTypes[i]);
            return constructor.newInstance(args);
        }
        return clazz.getConstructor().newInstance();
    }
}
