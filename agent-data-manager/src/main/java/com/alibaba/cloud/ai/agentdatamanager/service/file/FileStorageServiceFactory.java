
package com.alibaba.cloud.ai.agentdatamanager.service.file;

import com.alibaba.cloud.ai.agentdatamanager.properties.FileStorageProperties;
import com.alibaba.cloud.ai.agentdatamanager.properties.OssStorageProperties;
import com.alibaba.cloud.ai.agentdatamanager.service.file.FileStorageServiceEnum;
import com.alibaba.cloud.ai.agentdatamanager.service.file.impls.LocalFileStorageServiceImpl;
import com.alibaba.cloud.ai.agentdatamanager.service.file.impls.OssFileStorageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FileStorageServiceFactory implements FactoryBean<FileStorageService> {

	private final FileStorageProperties properties;

	private final OssStorageProperties ossProperties;

	@Override
	public FileStorageService getObject() {
		if (FileStorageServiceEnum.OSS.equals(properties.getType())) {
			return new OssFileStorageServiceImpl(properties, ossProperties);
		}
		else {
			return new LocalFileStorageServiceImpl(properties);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return FileStorageService.class;
	}

}
