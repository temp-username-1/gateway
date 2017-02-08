package me.giannists.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.giannists.connections.TypicodeApi;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class RetrofigConfig {

    @Value("${typicode.baseurl}")
    private String typicodeUrl;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public TypicodeApi getPAccountApi() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return new Retrofit.Builder()
                .baseUrl(typicodeUrl)
                .client(new OkHttpClient.Builder()
                        .readTimeout(5, TimeUnit.SECONDS)
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(TypicodeApi.class);
    }

}
