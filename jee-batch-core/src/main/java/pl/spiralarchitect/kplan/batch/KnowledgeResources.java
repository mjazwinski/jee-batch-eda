package pl.spiralarchitect.kplan.batch;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class KnowledgeResources {

	private Set<KnowledgeResource> resources = new HashSet<>();

	public void addResource(KnowledgeResource resource) {
		resources.add(resource);
	}
	
	public Set<KnowledgeResource> getResources() {
		return resources;
	}
	
}
