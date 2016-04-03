package pl.spiralarchitect.kplan.batch;

import java.io.Serializable;
import java.util.ArrayList;

import javax.batch.api.partition.PartitionCollector;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class KnowledgeResourcePartitionCollector implements PartitionCollector {
	
	@Inject
	private StepContext stepContext;

	@Override
	public Serializable collectPartitionData() throws Exception {
		return stepContext.getPersistentUserData();
	}

}
