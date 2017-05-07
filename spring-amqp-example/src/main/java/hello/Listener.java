package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class Listener implements MessageListener {
	private final static Logger LOGGER = LoggerFactory.getLogger(Listener.class);

	public void onMessage(Message message) {
		LOGGER.info("message received, content is {}", message.getBody());
	}

}
