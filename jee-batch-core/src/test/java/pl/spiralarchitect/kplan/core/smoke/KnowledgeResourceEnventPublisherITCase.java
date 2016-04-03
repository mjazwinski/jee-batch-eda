package pl.spiralarchitect.kplan.core.smoke;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import junit.framework.Assert;
import pl.spiralarchitect.kplan.batch.KnowledgeResources;
import pl.spiralarchitect.kplan.eda.consumer.NewKnowledgeResourceEventConsumer;
import pl.spiralarchitect.kplan.eda.publisher.KnowlegeResourceEventPublisher;

/**
 * Batch job test
 * 
 * @author majazwinski
 *
 */
@RunWith(Arquillian.class)
public class KnowledgeResourceEnventPublisherITCase extends ArquillianDeploymentSpec { 

	@Inject
	private KnowlegeResourceEventPublisher krEventPublisher;
	
	@Inject
	private NewKnowledgeResourceEventConsumer newKnowledgeResourceEventConsumer;
	
	@Inject
	private KnowledgeResources resources;
	
	@Test
	public void test() throws InterruptedException, JMSException {
		Assert.assertEquals(0, resources.getResources().size());
		String sentMessage = "Java EE 7 What's new?";
		krEventPublisher.publishMessage(sentMessage);
		TimeUnit.SECONDS.sleep(3);
		String receivedMessage = newKnowledgeResourceEventConsumer.receive();
		assertEquals(sentMessage, receivedMessage);
	}
	
	@Test
	public void testTransactional() throws InterruptedException, JMSException {
		Assert.assertEquals(0, resources.getResources().size());
		String sentMessage = null;
		try {
			krEventPublisher.publishMessage(sentMessage);
		} catch (Exception e) {
			System.err.println("error " + e);
		}
		TimeUnit.SECONDS.sleep(3);
		String receivedMessage = newKnowledgeResourceEventConsumer.receive();
		assertEquals(NewKnowledgeResourceEventConsumer.NO_MESSAGE, receivedMessage);
	}

}
