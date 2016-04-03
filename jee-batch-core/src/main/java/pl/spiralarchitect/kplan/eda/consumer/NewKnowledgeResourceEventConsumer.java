package pl.spiralarchitect.kplan.eda.consumer;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.transaction.Transactional;

/**
 * Since JTA 1.2 / Java EE 7 CDI beans can be @Trasnsactional
 * 
 * There is also a new cool @TransactionScoped scope (prior to this we had to use e.g. TransactionSynchronizationRegistry)
 */
@Named
public class NewKnowledgeResourceEventConsumer {
	
	private static final int RECEIVE_TIMEOUT = 2500;
	public static final String NO_MESSAGE = "__NO_MESSAGE";
	@Inject
	@JMSConnectionFactory("java:/jms/KplanConnectionFactory")
	private JMSContext context;
	@Resource(lookup = "java:/jms/queue/NewKnowledgeResourceEventQueue")
	private Queue queue;
	
	/**
     * @see MessageListener#onMessage(Message)
     */
	@Transactional
    public String receive() {
    	JMSConsumer consumer = context.createConsumer(queue);
    	Message message = consumer.receive(RECEIVE_TIMEOUT);
    	if (message != null) {
    		try {
				return message.getBody(String.class);
			} catch (JMSException e) {
				throw new MessageProcessingException(e);
			}
    	} else {
    		return NO_MESSAGE;
    	}
    }

}
