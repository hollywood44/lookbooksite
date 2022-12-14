package com.lbs.lookbooksite.configs;

import com.lbs.lookbooksite.interceptor.AlertInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(alertInterceptor())
                .addPathPatterns("/**") // 해당 경로에 접근하기 전에 인터셉터가 가로챔
                .excludePathPatterns("/css/**", "/js/**", "/assets/**", "/notice/**","/boardImg/**",
                        "/productImg/**","/lookBookImg/**","/styleTagImg/**") // 해당 경로는 인터셉터가 가로치지 않는다.
                .excludePathPatterns("/admin", "/admin/**","/member/signIn");

    }

    @Bean
    public AlertInterceptor alertInterceptor() {
        return new AlertInterceptor();
    }
}