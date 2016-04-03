package pl.spiralarchitect.kplan.core.smoke;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import junit.framework.Assert;
import pl.spiralarchitect.kplan.batch.BatchImportService;
import pl.spiralarchitect.kplan.batch.KnowledgeResources;

/**
 * Batch job test
 * 
 * @author majazwinski
 *
 */
@RunWith(Arquillian.class)
public class BatchJobITCase extends ArquillianDeploymentSpec { 

	@Inject
	private BatchImportService importService;
	
	@Inject
	private KnowledgeResources resources;
	
	@Test
	public void test() {
		Assert.assertEquals(0, resources.getResources().size());
		importService.triggerImport();
		Assert.assertTrue(resources.getResources().size() > 0);
	}

}
