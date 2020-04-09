package com.jojomock.mock.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 用于Spring动态代理注入自定义接口
 * https://www.jianshu.com/p/e2e19fcf97fc
 * https://blog.csdn.net/lichuangcsdn/article/details/89694363
 * https://blog.csdn.net/liyantianmin/article/details/81047373
 * open-feign源码: FeignClientsRegistrar https://blog.csdn.net/sinat_29899265/article/details/86577997
 * <p>
 * BeanDefinitionRegistryPostProcessor 和 ImportBeanDefinitionRegistrar
 * 区别在于后者需要@Import导入类，前者需要@Component
 */
@Slf4j
@Component
public class ServiceBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware, ApplicationContextAware {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private MetadataReaderFactory metadataReaderFactory;

    private ResourcePatternResolver resourcePatternResolver;

    private static ApplicationContext applicationContext;

    public static ArrayList<String> simpleNameList = new ArrayList<>();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 通过反射获取需要代理的接口的clazz列表
        Set<Class<?>> clazzSet = scannerPackages("com.jojomock.mock.test");
        for (Class beanClazz : clazzSet) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            // 这里可以给该对象的属性注入对应的实例。mybatis就在这里注入了dataSource和sqlSessionFactory，
            // definition.getPropertyValues().add("interfaceType", beanClazz)，BeanClass需要提供setter
            // definition.getConstructorArgumentValues()，BeanClass需要提供包含该属性的构造方法，否则会注入失败
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClazz);
            definition.getConstructorArgumentValues().addGenericArgumentValue(applicationContext);

            // 注意，这里的BeanClass是生成Bean实例的工厂，不是Bean本身。
            // FactoryBean是一种特殊的Bean，其返回的对象不是指定类的一个实例，
            // 其返回的是该工厂Bean的getObject方法所返回的对象。
            definition.setBeanClass(ServiceFactory.class);

            // 这里采用的是byType方式注入，类似的还有byName等
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            String simpleName = beanClazz.getSimpleName();
            log.info("beanClazz.getSimpleName(): {}", simpleName);
            log.info("GenericBeanDefinition: {}", definition);

            registry.registerBeanDefinition(simpleName, definition);
            simpleNameList.add(simpleName);
        }
    }

    /**
     * 注册Controller
     */
//    public static void registerController(String controllerBeanName) {
//        RequestMappingHandlerMapping rmhm = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
//        if (rmhm != null) {
//
//            try {
//                Object controller = applicationContext.getBean(controllerBeanName);
//                if (controller == null) {
//                    return;
//                }
//                Map<Method, RequestMappingInfo> methods = MethodIntrospector.selectMethods(controller.getClass(),
//                        (MethodIntrospector.MetadataLookup<RequestMappingInfo>) method -> {
//                            try {
//                                RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
//                                RequestMappingInfo.Builder mappping = RequestMappingInfo
//                                        .paths((requestMapping.path()))
//                                        .methods(requestMapping.method())
//                                        .params(requestMapping.params())
//                                        .headers(requestMapping.headers())
//                                        .consumes(requestMapping.consumes())
//                                        .produces(requestMapping.produces())
//                                        .mappingName(requestMapping.name());
//                                return mappping.build();
//                            } catch (Throwable ex) {
//                                throw new IllegalStateException("Invalid mapping on handler class [" +
//                                        controller.getClass().getName() + "]: " + method, ex);
//                            }
//                        });
//
//                Method rmhmMethod = rmhm.getClass().getSuperclass().getSuperclass().
//                        getDeclaredMethod("registerHandlerMethod", new Class[]{Object.class, Method.class, Object.class});
//                rmhmMethod.setAccessible(true);
//
//                methods.forEach((method, mapping) -> {
//                    try {
//                        rmhmMethod.invoke(rmhm, new Object[]{controller, method, mapping});
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }


    /**
     * 根据包路径获取包及子包下的所有类
     *
     * @param basePackage basePackage
     * @return Set<Class < ?>> Set<Class<?>>
     */
    private Set<Class<?>> scannerPackages(String basePackage) {

        Set<Class<?>> set = new LinkedHashSet<>();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz;
                    try {
                        clazz = Class.forName(className);
                        if (clazz.isInterface() && clazz.getAnnotation(MyController.class) != null) {
                            set.add(clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    protected String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(this.getEnvironment().resolveRequiredPlaceholders(basePackage));
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private Environment getEnvironment() {
        return applicationContext.getEnvironment();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}


