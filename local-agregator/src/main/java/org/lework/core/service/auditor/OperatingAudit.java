package org.lework.core.service.auditor;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作审计(系统操作日志)注解.
 * 通过该注解，在类或方法中使用，当调用到该方法时
 * 会引起一次aop，就是{@link OperatingRecordAspect}类，通过该类在调用
 * 之后都会做一次记录，并个把所有的记录存储在SS_OPERATING_RECORD
 *
 * @author Gongle
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperatingAudit {
	
	/**
	 * 模块名称，默认为"" ，可以在Controller类中说明该Controller是什么模块
	 * 
	 */
	String value() default "";
	
	/**
	 * 功能名称 默认为"" ，主要是在Controller的方法中说明该方法是什么功能
	 * 
	 */
	String function() default "";
}
