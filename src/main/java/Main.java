import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static final String REMOTE_SERVICE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    static void outCatsInfo() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        CloseableHttpResponse response = httpClient.execute(request);

        List<CatsInfo> catsInfo = mapper.readValue(response.getEntity().getContent().readAllBytes(), new TypeReference<List<CatsInfo>>() {
        });
        catsInfo.stream()
                .filter(value -> value.getUpvotes() != 0 && value.getUpvotes() > 0)
                .forEach(System.out::println);
    }


    public static void main(String[] args) throws IOException {
        outCatsInfo();
    }
}
