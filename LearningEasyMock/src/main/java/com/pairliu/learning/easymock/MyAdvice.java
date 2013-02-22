package com.pairliu.learning.easymock;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MyAdvice implements AdviceInter {  
    
    public void afterMethod(Object target, Method method, Object[] args) {  
        System.out.println("目标对象为：" + target.getClass().getName());  
        System.out.println(method.getName() + "执行完毕！");  
    }  
  
    public void beforeMethod(Object target, Method method, Object[] args) {  
        System.out.println(method.getName() + "开始执行");  
        if (null != args) {  
            System.out.println("参数为：" + Arrays.asList(args));  
        } else {  
            System.out.println("参数为：" + null);  
        }  
    }  
}  
