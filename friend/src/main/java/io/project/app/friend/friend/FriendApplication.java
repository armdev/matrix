package io.project.app.friend.friend;

import io.project.neo4j.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("io.project")
@EntityScan("io.project.neo4j.domain")
@EnableNeo4jRepositories(
        basePackages = "io.project.neo4j.repository")
@Slf4j
@EnableAsync
@EnableDiscoveryClient
@EnableEurekaClient
public class FriendApplication {

    @Autowired
    private PersonRepository personRepository;

    @Value("${db_url}")
    private String db_url;

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(FriendApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        // personRepository.deleteAll();
        log.info("DB URL IS " + db_url);
    }

}
