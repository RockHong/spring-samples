package hello;

import org.joda.time.LocalTime;

public class HelloWorld {
	public static void main(String[] args) {
		Greeter greeter = new Greeter();
		LocalTime current = new LocalTime();
		System.out.println(greeter.sayHello() + current);
	}
}
