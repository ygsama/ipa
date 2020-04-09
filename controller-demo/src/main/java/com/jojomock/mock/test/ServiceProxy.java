package com.jojomock.mock.test;


import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理，需要注意的是，这里用到的是JDK自带的动态代理，代理对象只能是接口，不能是类
 */
@Data
@ToString
public class ServiceProxy<T> implements InvocationHandler {

    private Class<T> interfaceType;
    private ApplicationContext applicationContext;

    public ServiceProxy(Class<T> interfaceType, ApplicationContext applicationContext) {
        this.interfaceType = interfaceType;
        this.applicationContext = applicationContext;
    }

    /**
     * controller接口的代理实现类里，执行的代理的hello方法
     * 可以通过反射，注解等，进行结果集的处理
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        System.out.println("调用前，args = " + args);
        MyControllerMethod annotation = method.getAnnotation(MyControllerMethod.class);
        Object bean = applicationContext.getBean(annotation.beanType());
        Method beanMethod = ReflectionUtils.findMethod(bean.getClass(), annotation.methodName(), method.getParameterTypes());
        beanMethod.setAccessible(true);
        Object result = ReflectionUtils.invokeMethod(beanMethod, bean, args);
        System.out.println("调用后，result = " + result);
        return result;
    }
}

