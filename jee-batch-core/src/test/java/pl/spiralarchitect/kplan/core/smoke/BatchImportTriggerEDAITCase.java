package pl.spiralarchitect.kplan.core.smoke;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.*;
import pl.spiralarchitect.kplan.batch.KnowledgeResources;
import pl.spiralarchitect.kplan.eda.publisher.BatchImportTriggerEventPublisher;

/**
 * Batch job test
 * 
 * @author majazwinski
 *
 */
@RunWith(Arquillian.class)
public class BatchImportTriggerEDAITCase extends ArquillianDeploymentSpec { 

	@Inject
	private BatchImportTriggerEventPublisher biEventPublisher;
	
	@Inject
	private KnowledgeResources resources;
	
	@Test
	public void test() throws InterruptedException, JMSException {
		resources.getResources().clear();
		assertEquals(0, resources.getResources().size());
		biEventPublisher.publishMessage("New resources uploaded.");
		TimeUnit.SECONDS.sleep(3);
		assertNotNull(resources.getResources());
		assertTrue(resources.getResources().size() > 0);
	}
	
	@Test
	public void testTransactional() throws InterruptedException, JMSException {
		resources.getResources().clear();
		assertEquals(0, resources.getResources().size());
		try {
			biEventPublisher.publishMessage(null);
		} catch (Exception e) {
			System.err.println("error " + e);
		}
		TimeUnit.SECONDS.sleep(3);
		assertNotNull(resources.getResources());
		assertTrue(resources.getResources().size() == 0);
	}

}
