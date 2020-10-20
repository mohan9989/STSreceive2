package com.pubsubdemo.mypubsub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MypubsubApplication {
    
	private static final Log LOGGER = LogFactory.getLog(MypubsubApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MypubsubApplication.class, args);
	}
	
	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("myInputChannel") MessageChannel inputChannel,
			PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter =
				new PubSubInboundChannelAdapter(pubSubTemplate, "sub123");
		adapter.setOutputChannel(inputChannel);

		return adapter;
	}

	@Bean
	public MessageChannel myInputChannel() {
		return new DirectChannel();
	}


	@ServiceActivator(inputChannel = "myInputChannel")
	public void messageReceiver(String msg) {
		LOGGER.info("Message arrived! Payload: " + msg);

	}

}
