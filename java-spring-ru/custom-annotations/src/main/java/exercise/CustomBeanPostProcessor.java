package exercise;

import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.*;

import org.slf4j.LoggerFactory;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor{
    private static Logger LOGGER = LoggerFactory.getLogger(CustomBeanPostProcessor.class.getName());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Inspect.class)) {
            String level = bean.getClass().getAnnotation(Inspect.class).level();
            Object myProxy = Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        if(level.equals("debug"))
                            LOGGER.debug("Was called method: {}() with arguments: {}", method.getName(), args);
                        if(level.equals("info")) {
                            LOGGER.info("Was called method: {}() with arguments: {}", method.getName(), args);
                        }
                        return method.invoke(bean, args);
                    }
            );
            return myProxy;
        }
        return bean;
    }
}
