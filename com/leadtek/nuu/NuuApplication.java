package com.leadtek.nuu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.leadtek.webconfig.FileProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan
@EnableSwagger2
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties({
	FileProperties.class
})
public class NuuApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NuuApplication.class, args);
	}

}
