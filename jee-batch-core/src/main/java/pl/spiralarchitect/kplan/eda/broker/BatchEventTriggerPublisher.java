package pl.spiralarchitect.kplan.eda.broker;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;

@Named
public class BatchEventTriggerPublisher {


	@Inject
	@JMSConnectionFactory("java:/jms/KplanConnectionFactory")
	private JMSContext context;
	
	@Resource(lookup="java:/jms/queue/BatchTriggerEventQueue")
	public Destination targetQueue;
	
	public void publishMessage(Message message) {
		context.createProducer().send(targetQueue, message);
	}
	
}
