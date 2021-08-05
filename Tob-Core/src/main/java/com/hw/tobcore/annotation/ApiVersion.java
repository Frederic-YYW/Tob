package com.hw.tobcore.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 自定义版本号
 * @author lich
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {
	
	/**
	 * 标识版本号
	 * @return
	 */
	int value();

}
