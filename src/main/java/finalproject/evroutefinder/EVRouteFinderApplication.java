package finalproject.evroutefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EVRouteFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EVRouteFinderApplication.class, args);
	}

}
