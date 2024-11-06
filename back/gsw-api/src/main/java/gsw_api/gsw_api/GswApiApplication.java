package gsw_api.gsw_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "gsw_api.gsw_api")
public class GswApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GswApiApplication.class, args);
    }
}
