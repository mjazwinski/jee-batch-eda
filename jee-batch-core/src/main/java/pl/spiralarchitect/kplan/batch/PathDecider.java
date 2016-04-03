package pl.spiralarchitect.kplan.batch;

import java.util.Properties;

import javax.batch.api.Decider;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.StepExecution;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PathDecider implements Decider {
	
	@Inject
	private JobContext jobContext;

	@Override
	public String decide(StepExecution[] executions) throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
		String nextStep = jobParameters.getProperty("next");
		if (nextStep != null) {
			return nextStep;
		}
		return "NO_RESOURCES";
	}

}
