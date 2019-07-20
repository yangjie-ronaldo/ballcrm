package org.nothink.ballcrm.config;

import org.nothink.ballcrm.component.DateConverter;
import org.nothink.ballcrm.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

/**
 * SpringMVC的配置类
 */
@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    //文件下载的映射路径
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    //文件上传的绝对路径
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/hellopage").setViewName("hello");
    }

    //添加SpringMVC的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyLoginInterceptor())
                // 拦截所有请求 /**
                .addPathPatterns("/**")
                // 排除拦截的请求 登录页面，注册api 登录api 登录处理 微信的接口
                .excludePathPatterns("/", "/pages/login", "/reg","/login","/action/login","/wechat/**","/wechat",
                        // 排除静态资源请求 这些目录下的静态资源可以直接访问
                        "/css/**", "/js/**", "/img/**");
    }

    @Bean
    public HandlerInterceptor getMyLoginInterceptor(){
        return new LoginInterceptor();
    }

    //添加静态文件路径到绝对路径的映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);

    }

    //添加转换器
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
