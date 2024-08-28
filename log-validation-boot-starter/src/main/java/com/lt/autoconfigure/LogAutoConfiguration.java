package com.lt.autoconfigure;

import com.lt.aspect.ControllerLogAspect;
import com.lt.aspect.GlobalOperationAspect;
import com.lt.properties.LogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(LogProperties.class)
@ConditionalOnClass({ControllerLogAspect.class, GlobalOperationAspect.class})
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean  //没有ControllerLogAspect bean的时候创建
    public ControllerLogAspect controllerLogAspect(){
       return new ControllerLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalOperationAspect globalOperationAspect(){
        return new GlobalOperationAspect();
    }
}
