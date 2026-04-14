
package com.alibaba.cloud.ai.agentdatamanager.service.vectorstore;

import com.alibaba.cloud.ai.agentdatamanager.properties.DataAgentProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleVectorStoreInitialization implements ApplicationRunner, DisposableBean {

	private final SimpleVectorStore vectorStore;

	private final DataAgentProperties properties;

	public void load() {
		File file = new File(properties.getVectorStore().getFilePath());

		if (!file.exists()) {
			log.info("No locally serialized vector database file was found.");
			return;
		}

		try {
			vectorStore.load(file);
		}
		catch (Throwable throwable) {
			log.error("Failed to load the locally serialized vector database file.", throwable);
		}
	}

	public void save() {
		log.info("Serialize the vector database to a local file.");
		Path path = Paths.get(properties.getVectorStore().getFilePath());

		try {
			Files.createDirectories(path.getParent());

			if (!Files.exists(path)) {
				Files.createFile(path);
			}

			vectorStore.save(path.toFile());
		}
		catch (Throwable t) {
			log.error("An exception occurred while serializing the vector database to a local file.", t);
		}
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.load();
	}

	@Override
	public void destroy() {
		this.save();
	}

}
