package com.pairliu.learning.easymock;

import java.lang.reflect.Method;

public interface AdviceInter {  
    /** 
     * 目标方法执行之前 
     *  
     */  
    public void beforeMethod(Object target, Method method, Object[] args);  
  
    /** 
     * 目标方法执行之后 
     *  
     * @param target 
     *            目标对象 
     * @param method 
     *            方法 
     * @param args 
     *            参数 
     */  
    public void afterMethod(Object target, Method method, Object[] args);  
}  