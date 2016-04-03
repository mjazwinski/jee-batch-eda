package pl.spiralarchitect.kplan.batch;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import javax.batch.api.AbstractBatchlet;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class InitializeResourceImport extends AbstractBatchlet {

	@Inject
	private JobContext jobContext;

	@Override
	public String process() throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
		String resourceFileDir = (String) jobParameters.get(KnowledgeImportParamters.RESOURECE_DIR.getParameterName());
		File importDir = new File(resourceFileDir);
		File[] filesToMove = importDir.listFiles();
		File workDir = new File("./workDir");
		if (filesToMove != null && filesToMove.length > 0) {
			for (File file : filesToMove) {
				Path filePath = Paths.get(workDir.getCanonicalPath()).resolve(file.getName());
				Files.move(Paths.get(file.getCanonicalPath()), filePath, StandardCopyOption.REPLACE_EXISTING);
				jobParameters.setProperty(KnowledgeImportParamters.RESOURCE_TEMP_NAME.getParameterName(),
						workDir.getCanonicalPath());
			}
			return "IMPORT";
		} else {
			return "FAILED";
		}
	}

}
