package com.lt.service;


import com.lt.properties.IpProperties;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class IpCountService {

    @Resource
    private IpProperties ipProperties;

    private Map<String,Integer> ipCountMap = new HashMap<String,Integer>();

    @Resource
    //当前的request对象的注入工作由使用当前starter的工程提供自动装配
    private HttpServletRequest httpServletRequest;

    //每次调用当前操作，就记录当前访问的IP，然后累加访问次数
    public void count(){
        //1.获取当前操作的IP地址
        String ip = httpServletRequest.getRemoteAddr();

        System.out.println("==============================================="+ip);

        //2.根据IP地址从Map取值，并递增
        if (ipCountMap.get(ip) == null){
            ipCountMap.put(ip,1);
        }else {
            ipCountMap.put(ip, ipCountMap.get(ip) + 1);
        }
    }


    //    @Scheduled(cron = "0/5 * * * * ?")
//    @Scheduled(cron = "0/${tools.ip.cycle:5} * * * * ?")  //这里的${tools.ip.cycle:5}等价于@value("${tools.ip.cycle:5}")取出配置文件的值
    @Scheduled(cron = "0/#{ipProperties.cycle} * * * * ?")
    public void print(){
        if (ipProperties.getModel().equals(IpProperties.LogModel.DETAIL.getValue())){
            System.out.println("               IP访问监控");
            System.out.println("+--------------IP-address--------------+----num------+");
            for (Map.Entry<String, Integer> entry : ipCountMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(String.format("|%18s             |%5d        |",key,value));
            }
            System.out.println("|                                                    |");
            System.out.println("|                                                    |");
            System.out.println("|                                                    |");
            System.out.println("|                                                    |");
            System.out.println("+--------------------------------------+----num------+");

        }else if(ipProperties.getModel().equals(IpProperties.LogModel.SIMPLE.getValue())){

            System.out.println("               IP访问监控");
            System.out.println("+--------------IP-address--------------+----num------+");
            for (String key : ipCountMap.keySet()) {
                System.out.println(String.format("|%18s                   |",key));
            }
        }



        if (ipProperties.getCycleReset()){
            ipCountMap.clear();
        }

    }

    public static void main(String[] agrs){
        new IpCountService().print();
    }

}
