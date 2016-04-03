package pl.spiralarchitect.kplan.batch;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class KnowledgeResourceWriter extends AbstractItemWriter {

	@Inject
	private StepContext stepContext;
	
	@Override
	public void writeItems(List<Object> items) throws Exception {
		stepContext.setPersistentUserData((Serializable) items);
	}

}
