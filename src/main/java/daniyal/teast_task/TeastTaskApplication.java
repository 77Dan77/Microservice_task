package daniyal.teast_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
//@EnableCassandraRepositories(
//        basePackages = "daniyal.teast_task.*")
@EntityScan("daniyal.teast_task.*")
//@ComponentScan(basePackages = {"daniyal.teast_task.*"})
public class TeastTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeastTaskApplication.class, args);
    }

}
