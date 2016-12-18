package lt.itakademija.context;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class ApplicationContextTest {

	@Test
	public void test() {
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		A a = context.getBean(A.class);
		a.execute();
		
		a = context.getBean(A.class);
		a.execute();
		
		a = context.getBean(A.class);
		a.execute();
	}
	
	@Component
	@Scope("singleton")
	static class A {
		private final B b;
		
		@Autowired
		A(B b) {
			this.b = b;
		}
		
		@PostConstruct
		void init() {
			System.out.println("A#init");
		}
		
		void execute() {
			System.out.println("A#execute() called.");
			b.execute();
		}
	}
	
	@Component
	static class B {
		void execute() {
			System.out.println("B#execute called.");
		}
	}
	
	@Configuration
	@ComponentScan("lt.itakademija.context")
	static class Config {
		
		
	}
	
}
