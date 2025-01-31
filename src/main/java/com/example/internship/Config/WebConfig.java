package com.example.internship.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:E:/Users/Dell/eclipse-workspace/restaurant-uploads/");
     // For menu images
        registry.addResourceHandler("/menu-uploads/**")
                .addResourceLocations("file:E:/Users/Dell/eclipse-workspace/menu-uploads/");
    }
    
}
