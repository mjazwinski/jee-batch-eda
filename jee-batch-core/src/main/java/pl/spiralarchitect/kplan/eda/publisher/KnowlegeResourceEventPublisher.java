package pl.spiralarchitect.kplan.eda.publisher;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import pl.spiralarchitect.kplan.eda.common.MessageConstants;

/**
 * Session Bean implementation class KnowlegeResourceEventPublisher
 */
@Stateless
@LocalBean
public class KnowlegeResourceEventPublisher {

	@Resource
	@JMSConnectionFactory("java:/jms/KplanConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(lookup="java:/jms/queue/EventChannelQueue")
	private Destination targetDestination;
	
	@Resource
	private SessionContext sessionContext;
	
	public void publishMessage(String body) throws JMSException {
		JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
		TextMessage message = context.createTextMessage(body);
		message.setStringProperty(MessageConstants.MESSAGE_TYPE, MessageConstants.KNOWLEDGE_RESOURCE_MESSAGE);
		context.createProducer().send(targetDestination, message);
		//intended NPE vulnerability
		if (body.length()>0) {
			System.out.println("message was not empty");
		}
	}

}
