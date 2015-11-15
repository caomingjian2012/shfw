package com.common.plugins.spring.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

public class LogAspect {
	private static Logger log = Logger.getLogger(LogAspect.class);  
	   public Object logProfilingTime(ProceedingJoinPoint pjp) throws Throwable {
	    	 
	        
	        StopWatch clock = new StopWatch();  
	        clock.start(); // 计时开始  
	        Object retVal = pjp.proceed();  
	        clock.stop(); // 计时结束         
	     
	        
	        log.debug("Takes:" + clock.getTotalTimeMillis() + " ms  "); 
	        if(clock.getTotalTimeMillis()>1000){	        	
	        	 Object[] args =  pjp.getArgs();	        	
	        	 if(args==null || args.length==0){
	        		  log.warn("Takes:" + clock.getTotalTimeMillis() + " ms" +pjp.getTarget().getClass()+"."+pjp.getSignature().getName()+"()"); 
	        	 }else{
	        		 String[] simpleParams = new String[args.length];  
		        	  for(int i=0;i<args.length;i++){
		        		  simpleParams[i] = args[i]==null?null:args[i].toString();
		        	  }
		        	  log.warn("Takes:" + clock.getTotalTimeMillis() + " ms "+pjp.getTarget().getClass()+"."+pjp.getSignature().getName()+" ("
		        			  +StringUtils.arrayToDelimitedString(simpleParams, ",")+")"
		        			   ); 
	        	 }
	        	 
	        	
	        	 
	        }
	        return retVal; 
	    }

}
