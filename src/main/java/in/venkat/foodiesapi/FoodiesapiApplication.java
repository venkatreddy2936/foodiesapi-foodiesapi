package in.venkat.foodiesapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodiesapiApplication {

	// ✅ Static block runs BEFORE Spring creates any beans
	static {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		System.out.println("✅ .env variables loaded BEFORE Spring Boot starts.");
	}

	public static void main(String[] args) {

		SpringApplication.run(FoodiesapiApplication.class, args);
	}

}
