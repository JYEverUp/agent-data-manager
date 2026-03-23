package com.alibaba.cloud.ai.agentdatamanager.service;

import com.alibaba.cloud.ai.agentdatamanager.dto.ModelConfigDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicModelFactory {

    public ChatModel createChatModel(ModelConfigDTO config) {
        log.info("Creating NEW ChatModel instance. Provider: {}, Model: {}, BaseUrl: {}", config.getProvider(),
                config.getModelName(), config.getBaseUrl());
        checkBasic(config);

        String apiKey = StringUtils.hasText(config.getApiKey()) ? config.getApiKey() : "";
        OpenAiApi.Builder apiBuilder = OpenAiApi.builder()
                .apiKey(apiKey)
                .baseUrl(config.getBaseUrl())
                .restClientBuilder(getProxiedRestClientBuilder(config))
                .webClientBuilder(getProxiedWebClientBuilder(config));

        if (StringUtils.hasText(config.getCompletionsPath())) {
            apiBuilder.completionsPath(config.getCompletionsPath());
        }

        OpenAiApi openAiApi = apiBuilder.build();
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .model(config.getModelName())
                .temperature(config.getTemperature())
                .maxTokens(config.getMaxTokens())
                .streamUsage(true)
                .build();

        return OpenAiChatModel.builder().openAiApi(openAiApi).defaultOptions(openAiChatOptions).build();
    }

    public EmbeddingModel createEmbeddingModel(ModelConfigDTO config) {
        log.info("Creating NEW EmbeddingModel instance. Provider: {}, Model: {}, BaseUrl: {}", config.getProvider(),
                config.getModelName(), config.getBaseUrl());
        checkBasic(config);

        String apiKey = StringUtils.hasText(config.getApiKey()) ? config.getApiKey() : "";
        OpenAiApi.Builder apiBuilder = OpenAiApi.builder()
                .apiKey(apiKey)
                .baseUrl(config.getBaseUrl())
                .restClientBuilder(getProxiedRestClientBuilder(config))
                .webClientBuilder(getProxiedWebClientBuilder(config));

        if (StringUtils.hasText(config.getEmbeddingsPath())) {
            apiBuilder.embeddingsPath(config.getEmbeddingsPath());
        }

        OpenAiApi openAiApi = apiBuilder.build();
        return new OpenAiEmbeddingModel(openAiApi, MetadataMode.EMBED,
                OpenAiEmbeddingOptions.builder().model(config.getModelName()).build(),
                RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }

    private static void checkBasic(ModelConfigDTO config) {
        Assert.hasText(config.getBaseUrl(), "baseUrl must not be empty");
        if (!"custom".equalsIgnoreCase(config.getProvider())) {
            Assert.hasText(config.getApiKey(), "apiKey must not be empty");
        }
        Assert.hasText(config.getModelName(), "modelName must not be empty");
    }

    private RestClient.Builder getProxiedRestClientBuilder(ModelConfigDTO config) {
        if (config.getProxyEnabled() == null || !config.getProxyEnabled()) {
            return RestClient.builder();
        }

        log.info("Proxy enabled for SYNC requests -> {}:{}", config.getProxyHost(), config.getProxyPort());

        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        if (StringUtils.hasText(config.getProxyUsername())) {
            credsProvider.setCredentials(new AuthScope(config.getProxyHost(), config.getProxyPort()),
                    new UsernamePasswordCredentials(config.getProxyUsername(),
                            config.getProxyPassword().toCharArray()));
        }

        CloseableHttpClient httpClient = HttpClients.custom()
                .setProxy(new HttpHost(config.getProxyHost(), config.getProxyPort()))
                .setDefaultCredentialsProvider(credsProvider)
                .build();

        return RestClient.builder().requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    private WebClient.Builder getProxiedWebClientBuilder(ModelConfigDTO config) {
        if (config.getProxyEnabled() == null || !config.getProxyEnabled()) {
            return WebClient.builder();
        }

        log.info("Proxy enabled for ASYNC requests -> {}:{}", config.getProxyHost(), config.getProxyPort());

        HttpClient nettyClient = HttpClient.create()
                .responseTimeout(Duration.ofMinutes(3))
                .proxy(proxy -> {
                    ProxyProvider.Builder proxyBuilder = proxy.type(ProxyProvider.Proxy.HTTP)
                            .host(config.getProxyHost())
                            .port(config.getProxyPort());
                    if (StringUtils.hasText(config.getProxyUsername())) {
                        proxyBuilder.username(config.getProxyUsername())
                                .password(ignore -> config.getProxyPassword());
                    }
                });

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(nettyClient));
    }

}
