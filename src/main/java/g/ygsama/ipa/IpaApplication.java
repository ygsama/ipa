package g.ygsama.ipa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringbootApplication 相当于 @Configuration,@EnableAutoConfiguration 和 @ComponentScan 并具有他们的默认属性值
@SpringBootApplication
public class IpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpaApplication.class, args);
	}
}
