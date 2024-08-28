package com.lt.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("logProperties")
@ConfigurationProperties(prefix = "custom.log")
public class LogProperties {

    private String packages;
}
