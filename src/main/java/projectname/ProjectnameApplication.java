package projectname;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@EnableAsync
@EnableCaching
@SpringBootApplication
public class ProjectnameApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-03:00"));
		SpringApplication.run(ProjectnameApplication.class, args);
	}
}
