package pl.spiralarchitect.kplan.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class KnowledgeResourceReader extends AbstractItemReader {

	@Inject
	private JobContext jobContext;
	
	@Inject
	@BatchProperty(name="fileName")
	private String fileName;
	
	private FileInputStream inputStream;
	private Integer recordNumber = Integer.valueOf(0);
	private BufferedReader br;

	@Override
	public Object readItem() throws Exception {
		String line = br.readLine();
		if (line != null) {
			recordNumber++;
		}
		return line;
	}

	@Override
	public void open(Serializable checkpoint) throws Exception {
		String resourceName = getResourceName();
		inputStream = new FileInputStream(resourceName);
		br = new BufferedReader(new InputStreamReader(inputStream));

		if (checkpoint != null) {
			recordNumber = (Integer) checkpoint;
		}
		for (int i = 1; i < recordNumber; i++) { // Skip upto recordNumber
			br.readLine();
		}
	}

	@Override
	public void close() throws Exception {
		if (br != null) {
			br.close();
		}
		new File(getResourceName()).delete();
		super.close();
	}

	private String getResourceName() {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
		String resourceName = (String) jobParameters
				.get(KnowledgeImportParamters.RESOURCE_TEMP_NAME.getParameterName());
		return resourceName + File.separator + fileName;
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		return recordNumber;
	}

}
