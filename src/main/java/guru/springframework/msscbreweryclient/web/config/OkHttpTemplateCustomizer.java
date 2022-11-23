package guru.springframework.msscbreweryclient.web.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OkHttpTemplateCustomizer implements RestTemplateCustomizer {

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

        return new OkHttp3ClientHttpRequestFactory(client.newBuilder()
                .connectionPool(new ConnectionPool(10, 30, TimeUnit.SECONDS))
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .build());
    }



    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(createFactory());
    }
}
