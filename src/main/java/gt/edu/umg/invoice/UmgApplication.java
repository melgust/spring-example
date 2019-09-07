package gt.edu.umg.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import gt.edu.umg.invoice.utils.AppProperty;

@SpringBootApplication
@EnableConfigurationProperties({AppProperty.class})
public class UmgApplication {

	public static void main(String[] args) {
        SpringApplication.run(UmgApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(UmgApplication.class);
    }

}
