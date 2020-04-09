package com.jojomock.mock.test;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 接口实例工厂，这里主要是用于提供接口的实例对象
 */
@Data
@ToString
public class ServiceFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceType;
    private ApplicationContext applicationContext;

    public ServiceFactory(){}

    public ServiceFactory(Class<T> interfaceType, ApplicationContext applicationContext) {
        this.interfaceType = interfaceType;
        this.applicationContext = applicationContext;
    }

    private T serviceFactoryBean;

    /**
     * 创建接口对应的实例
     */
    @Override
    public T getObject() throws Exception {
        InvocationHandler handler = new ServiceProxy<>(interfaceType, applicationContext);
        if (serviceFactoryBean == null) {
            serviceFactoryBean = (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, handler);
        }
        return serviceFactoryBean;
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
