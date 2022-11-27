package com.lbs.lookbooksite.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/boardImg/**")
                .addResourceLocations("file:///Users/uk/Documents/codding_study/spring_upload/lookbooksite/boardImg/");
        registry.addResourceHandler("/productImg/**")
                .addResourceLocations("file:///Users/uk/Documents/codding_study/spring_upload/lookbooksite/productImg/");
        registry.addResourceHandler("/lookBookImg/**")
                .addResourceLocations("file:///Users/uk/Documents/codding_study/spring_upload/lookbooksite/lookBookImg/");
        registry.addResourceHandler("/styleTagImg/**")
                .addResourceLocations("file:///Users/uk/Documents/codding_study/spring_upload/lookbooksite/styleTagImg/");

        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");


    }
}