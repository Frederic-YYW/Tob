package com.hw.tobcore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceBeanContext {

    private static final Logger log = LoggerFactory.getLogger( ServiceBeanContext.class);

    private static ServiceBeanContext context = null;

    private static ApplicationContext ctx=null;

    private static ApplicationContext monitorCtx=null;

    private ServiceBeanContext(){

    }

    public static ServiceBeanContext getInstance(){
        if( context == null ){
            synchronized(ServiceBeanContext.class){
                if( context == null ){
                    context = new ServiceBeanContext();
                }
            }
        }
        return context;
    }

    public void loadContext(String path){
        try{
            String[] paths = path.split(",");
            if( paths.length > 1 ){
                ctx = new ClassPathXmlApplicationContext( paths[0] );
                monitorCtx = new ClassPathXmlApplicationContext( paths[1] );
            } else {
                ctx = new ClassPathXmlApplicationContext( path );
                monitorCtx = new ClassPathXmlApplicationContext( path );
            }
        } catch ( Exception e ){
            log.error( e.getMessage() , e );
        }
    }

    public Object getBean(String bean){
        return ctx.getBean(bean);
    }

    public Object getMonitorBean(String bean){
        return monitorCtx.getBean(bean);
    }
}
