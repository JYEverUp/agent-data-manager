
package com.alibaba.cloud.ai.agentdatamanager.config;

import com.alibaba.cloud.ai.agentdatamanager.properties.FileStorageProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.nio.file.Paths;
import java.time.Duration;

/**
 * Web配置类 (WebFlux 版本)
 */
@Configuration
@AllArgsConstructor
public class WebConfig implements WebFluxConfigurer {

	private final FileStorageProperties fileStorageProperties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadDir = Paths.get(fileStorageProperties.getPath()).toAbsolutePath().toString();

		registry.addResourceHandler(fileStorageProperties.getUrlPrefix() + "/**")
			.addResourceLocations("file:" + uploadDir + "/")
			.setCacheControl(CacheControl.maxAge(Duration.ofHours(1)));
	}

}
