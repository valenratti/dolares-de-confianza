package ar.edu.itba.infocracks.bd2.dolaresdeconfianza;

import com.google.common.collect.Lists;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres")
@EnableNeo4jRepositories(basePackages = "ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.neo4j")
@EnableTransactionManagement
public class DolaresDeConfianzaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DolaresDeConfianzaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
