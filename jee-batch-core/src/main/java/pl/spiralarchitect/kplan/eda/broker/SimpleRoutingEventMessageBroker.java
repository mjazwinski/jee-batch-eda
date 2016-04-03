package pl.spiralarchitect.kplan.eda.broker;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import pl.spiralarchitect.kplan.eda.common.MessageConstants;
import pl.spiralarchitect.kplan.eda.consumer.MessageProcessingException;

/**
 * Message-Driven Bean implementation class for: SimpleRoutingEventMessageBroker
 * 
 * IMPORTANT: Normally this component should only be responsible for receiving messages and invoking routing engine.
 */
@MessageDriven(
		activationConfig = { 
			//type of destination - topic or queue
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
			//name of destination that will be looked up in JNDI directory
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/EventChannelQueue"),
			//Connection factory that will create connections to JMS provider
			@ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue="java:/jms/KplanConnectionFactory")
		})
public class SimpleRoutingEventMessageBroker implements MessageListener {
	
	@Inject
	private BatchEventTriggerPublisher batchEventTriggerPublisher;
	
	@Inject
	private NewKnowledgeResourceEventPublisher newKnowledgeResourceEventPublisher;
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        try {
			String messageType = message.getStringProperty(MessageConstants.MESSAGE_TYPE);
			if (MessageConstants.BATCH_IMPORT_MESSAGE.equals(messageType)) {
				batchEventTriggerPublisher.publishMessage(message);
			} else if (MessageConstants.KNOWLEDGE_RESOURCE_MESSAGE.equals(messageType)) {
				newKnowledgeResourceEventPublisher.publishMessage(message);
			} else {
				//send message to DLQ for message with unknown message types
			}
		} catch (JMSException e) {
			throw new MessageProcessingException(e);
		}
        
    }

}
