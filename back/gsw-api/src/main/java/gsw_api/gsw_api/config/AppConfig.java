package gsw_api.gsw_api.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        System.out.println("RestTemplate bean criado em AppConfig.");
        return new RestTemplate();
    }
}
