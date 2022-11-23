package guru.springframework.msscbreweryclient.web.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OkHttpTemplateCustomizer implements RestTemplateCustomizer {


    @Value("${sfg.httpclient.okhttp3.maxidle:8}")
    private Integer maxIdle;

    @Value("${sfg.httpclient.okhttp3.keepaliveduration:30}")
    private Integer keepAliveDuration;

    @Value("${sfg.httpclient.okhttp3.readtimeout:3}")
    private Integer readTimeout;

    @Value("${sfg.httpclient.okhttp3.connecttimeout:3}")
    private Integer connectTimeout;

    @Value("${sfg.httpclient.okhttp3.writetimeout:3}")
    private Integer writeTimeout;

    OkHttp3ClientHttpRequestFactory createFactory() {
        OkHttpClient client = new OkHttpClient();
        Interceptor interceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request();
                log.debug("intercept: "+request);
                Response response = chain.proceed(request);
                log.debug("  -> "+response);
                return response;
            }
        };
        HttpLoggingInterceptor logger  = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC);

        log.debug("setup maxIdle: "+maxIdle);

        return new OkHttp3ClientHttpRequestFactory(client.newBuilder()
                .connectionPool(new ConnectionPool(maxIdle, keepAliveDuration, TimeUnit.SECONDS))
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .build());
    }



    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(createFactory());
    }
}
