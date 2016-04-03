package pl.spiralarchitect.kplan.batch;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.batch.api.partition.PartitionAnalyzer;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class KnowledgeResourcePartitionAnalyzer implements PartitionAnalyzer {
	
	@Inject
	private JobContext jobContext;
	
	@Inject
	private KnowledgeResources resources;

	@Override
	public void analyzeCollectorData(Serializable data) throws Exception {
		List<KnowledgeResource> items = (List<KnowledgeResource>) data;
		items.stream().forEach(resource -> resources.addResource((KnowledgeResource) resource));
	}

	@Override
	public void analyzeStatus(BatchStatus batchStatus, String exitStatus) throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
		jobParameters.setProperty("next", "STORE");
	}

}
