package pl.spiralarchitect.kplan.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class KnowledgeResourceProcessor implements ItemProcessor {

	@Inject
	private JobContext jobContext;
	
	@Override
	public Object processItem(Object item) throws Exception {
		String resourceAsString = (String)item;
		return KnowledgeResource.fromString(resourceAsString);
	}

}
