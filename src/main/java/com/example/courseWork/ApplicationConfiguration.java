package com.example.courseWork;

import org.springframework.context.annotation.*;

@Configuration
@Import({DatabaseConfiguration.class})
@ComponentScan(basePackages = "com.example")
public class ApplicationConfiguration {


}