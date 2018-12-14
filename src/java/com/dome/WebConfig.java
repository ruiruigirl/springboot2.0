package com.dome;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: dome2
 * @description:
 * @author: Mr.Zhou
 * @create: 2018-12-14 11:28
 **/

@Configuration
public class WebConfig {

    /**
     * 配置cors 全局配置
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("**")
                    .allowedOrigins("**")
                    .allowedMethods("PUT", "DELETE")
                    .allowedHeaders("header1", "header2", "header3")
                    .exposedHeaders("header1", "header2")
                    .allowCredentials(false).maxAge(3600);
        }
        };
    }

}
