package pl.spiralarchitect.kplan.eda.publisher;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.transaction.Transactional;

import pl.spiralarchitect.kplan.eda.common.MessageConstants;

/**
 * Session Bean implementation class BatchImportTriggerEventPublisher
 */
@Named("biEventPublisher")
public class BatchImportTriggerEventPublisher {

	@Resource
	@JMSConnectionFactory("java:/jms/KplanConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(lookup="java:/jms/queue/EventChannelQueue")
	public Destination targetDestination;
	
	@Transactional(Transactional.TxType.REQUIRED)
	public void publishMessage(String body) throws JMSException {
		JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
		TextMessage message = context.createTextMessage(body);
		message.setStringProperty(MessageConstants.MESSAGE_TYPE, MessageConstants.BATCH_IMPORT_MESSAGE);
		JMSProducer producer = context.createProducer();
		producer.send(targetDestination, message);
		//intentional NPE vulnerability here - just to simplify example with tx rollback
		if (body.length() > 0) {
			System.out.println("Message was not empty");
		}
	}
}
