package com.meguru.spring;

import com.meguru.annotation.Component;
import com.meguru.definition.BeanDefinition;
import com.meguru.iteface.BeanPostProcessor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext {

    private final Map<String, Object> ioc = new HashMap<>();
    private final Map<String, Object> loadingIoc = new HashMap<>();
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private final List<BeanPostProcessor> postProcessors = new ArrayList<>();

    public ApplicationContext(String packageName) throws IOException {
        initContext(packageName);
    }

    /**
     * 初始化容器
     *
     * @param packageName
     * @throws IOException
     */
    public void initContext(String packageName) throws IOException {
        // 先构造BeanDefinition, 再实例化Bean
        scanPackage(packageName).stream().filter(this::scanCreate).forEach(this::wrapper);
        initBeanPostProcessor();
        beanDefinitionMap.values().forEach(this::createBean);
    }

    /**
     * 优先创建生命周期函数
     */
    private void initBeanPostProcessor() {
        beanDefinitionMap.values().stream()
                .filter(bd -> BeanPostProcessor.class.isAssignableFrom(bd.getBeanType()))
                .map(this::createBean)
                .map(bean -> (BeanPostProcessor) bean)
                .forEach(postProcessors::add);
    }

    /**
     * 判断是否需要注册
     *
     * @param type
     * @return
     */
    protected boolean scanCreate(Class<?> type) {
        return type.isAnnotationPresent(Component.class);
    }

    /**
     * 创建BeanDefinition对象
     *
     * @param type
     * @return
     */
    protected BeanDefinition wrapper(Class<?> type) {
        BeanDefinition beanDefinition = new BeanDefinition(type);
        if (beanDefinitionMap.containsKey(beanDefinition.getName())) {
            throw new RuntimeException("Bean名字重复");
        }
        beanDefinitionMap.put(beanDefinition.getName(), beanDefinition);
        return beanDefinition;
    }

    /**
     * 通过beanDefinition创建实例对象
     *
     * @param beanDefinition
     */
    protected Object createBean(BeanDefinition beanDefinition) {
        String name = beanDefinition.getName();
        if (ioc.containsKey(name))
            return ioc.get(name);
        if (loadingIoc.containsKey(name))
            return loadingIoc.get(name);
        return doCreateBean(beanDefinition);
    }

    protected Object doCreateBean(BeanDefinition beanDefinition) {
        Object bean;
        try {
            // 实例化
            bean = beanDefinition.getConstructor().newInstance();
            loadingIoc.put(beanDefinition.getName(), bean);
            // 属性注入
            autoWiredBean(bean, beanDefinition);
            bean = initializeBean(bean, beanDefinition);
            loadingIoc.remove(beanDefinition.getName());
            ioc.put(beanDefinition.getName(), bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    private Object initializeBean(Object bean, BeanDefinition beanDefinition) throws InvocationTargetException, IllegalAccessException {
        for (BeanPostProcessor postProcessor : postProcessors) {
            bean = postProcessor.beforeInitializeBean(bean, beanDefinition.getName());
        }

        Method postConstruct = beanDefinition.getPostConstruct();
        if (postConstruct != null) {
            postConstruct.invoke(bean);
        }

        for (BeanPostProcessor postProcessor : postProcessors) {
            bean = postProcessor.afterInitializeBean(bean, beanDefinition.getName());
        }

        return bean;
    }

    private void autoWiredBean(Object bean, BeanDefinition beanDefinition) throws IllegalAccessException {
        for (Field field : beanDefinition.getAutowiredFields()) {
            field.setAccessible(true);
            field.set(bean, getBean(field.getType()));
        }
    }

    /**
     * 扫描包名下的所有类, 得到class对象集合
     *
     * @param packageName
     * @return
     */
    public List<Class<?>> scanPackage(String packageName) throws IOException {
        List<Class<?>> classList = new ArrayList<>();
        URL resource = this.getClass().getClassLoader().getResource(packageName.replace(".", File.separator));
        Path path = Path.of(resource.getFile());
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path absolutePath = file.toAbsolutePath();
                if (absolutePath.toString().endsWith(".class")) {
                    String replace = absolutePath.toString().replace(File.separator, ".");
                    String className = replace.substring(
                            replace.indexOf(packageName),
                            replace.length() - ".class".length()
                    );
                    try {
                        classList.add(Class.forName(className));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return classList;
    }

    public Object getBean(String name) {
        if (name == null)
            return null;
        Object bean = ioc.get(name);
        if (bean != null)
            return bean;
        if (beanDefinitionMap.containsKey(name))
            return createBean(beanDefinitionMap.get(name));
        return null;
    }

    public <T> T getBean(Class<T> beanType) {
        String name = this.beanDefinitionMap.values().stream()
                .filter(bd -> beanType.isAssignableFrom(bd.getBeanType()))
                .map(BeanDefinition::getName)
                .findFirst().orElse(null);
        return (T) getBean(name);
    }

    public <T> List<T> getBeans(Class<T> beanType) {
        return this.beanDefinitionMap.values().stream()
                .filter(bd -> beanType.isAssignableFrom(bd.getBeanType()))
                .map(BeanDefinition::getName)
                .map(this::getBean)
                .map(o -> (T) o)
                .toList();
    }


}
