package com.lt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("ipProperties")
@ConfigurationProperties(prefix = "tools.ip")
public class IpProperties {
    /**
     * 日志显示周期
     */
    private Long cycle = 5L;

    /**
     * 是否周期内重置数据
     */
    private Boolean cycleReset = false;

    /**
     * 日志输出格式，detail： 详细模式，simple：简单模式
     *
     */
    private String model = "detail";

    public enum LogModel{
        DETAIL("detail"),
        SIMPLE("simple");

        private String value;
        LogModel(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }
}
