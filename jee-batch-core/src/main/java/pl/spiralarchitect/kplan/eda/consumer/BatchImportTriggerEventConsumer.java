package pl.spiralarchitect.kplan.eda.consumer;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import pl.spiralarchitect.kplan.batch.BatchImportService;

/**
 * Message-Driven Bean implementation class for: BatchImportTriggerEventConsumer
 * 
 * An MDB component will process one message at a time, but messages will normally be fetched from jms provider in batches
 * for each MDB instance. By default MDB processes messages in AUTO_ACKNOWLEGE acknowledgement mode, 
 * this means that if onMesage returns without an error message will be acknowledged.
 * 
 * MDB cannot be called directly by client.
 * 
 * <b>MDB will receive only a triggering event. File with data would be transfered using some
 * other way like sftp. This way we are doing a variant of claim check Enterprise Integration Pattern.
 * Reason for this is that it would be a poor idea to transfer a few hundreds megabytes or more in a JMS or AMQP message</b>
 */
@MessageDriven(
		activationConfig = { 
		//type of destination - topic or queue
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		//name of destination that will be looked up in JNDI directory
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/BatchTriggerEventQueue"),
		//aforementioned acknowledgment mode - USING THIS WITH PROPERTY IN CMT TRANSACITON MANAGEMENT IS NOT REQUIRED
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto_acknowledge"),
		//Connection factory that will create connections to JMS provider
		@ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue="java:/jms/KplanConnectionFactory")
		})
public class BatchImportTriggerEventConsumer implements MessageListener {

	/**
	 * Use CDI to inject EJBs
	 */
	@Inject
	private BatchImportService importService; 
	
	/**
	 * Method called by ejb container for each message fetched from JMS provider
	 * this method can run in one of two transaction modes, either with (REQUIRED)
	 * or without transaction (NOT_SUPPORTED)
	 * 
	 * Message ordering is not guaranteed by JMS spec (apart from priorites of messges
	 * vendor extensions can provide ordering but using ordering among messages of same priority
	 * in case of JMS or EDA is not typical
	 * 
	 * Priority should be enough to order messages.
	 * 
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	/**
    	 * We could extract file name from message
    	 * or even whole path. For the purpose of this example we will just trigger processing
    	 */
    	importService.triggerImport();
    }

}
