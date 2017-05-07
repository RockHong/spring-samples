package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringWay {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringWay.class);
	private AbstractApplicationContext context;
	
	{
		context = new ClassPathXmlApplicationContext(
				"classpath:META-INF/spring/context.xml");
	}
	
	public static void main(final String... args) throws Exception {
		SpringWay s = new SpringWay();
		s.sendWithDefaultExchange();
		s.sendToSpecificExchange();
		
		Thread.sleep(3000);
		//s.destory();
	}
	
	private void sendWithDefaultExchange() {
		RabbitTemplate template = context.getBean(RabbitTemplate.class);
		Message message = MessageBuilder.withBody("hello spring amqp to 'default.exchange.queue'".getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();
		template.send("queue.to.default.exchange", message);
		LOGGER.info("message send {}", message);
	}
	
	private void sendToSpecificExchange() {
		RabbitTemplate template = context.getBean(RabbitTemplate.class);
		Message message = MessageBuilder.withBody("hello spring amqp to 'queue.to.exchange.a'".getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();
		template.send("queue.to.exchange.a","topic.a.1", message);
		LOGGER.info("message send {}", message);
	}
	
	private void destory() {
		context.destroy();
	}
}
