package pl.spiralarchitect.kplan.batch;

public enum KnowledgeImportParamters {

	RESOURCE_TEMP_NAME("resourceTempFileName"),
	RESOURECE_DIR("resourceFileDir");
	
	private String parameterName;
	
	private KnowledgeImportParamters(String parameterName){
		this.parameterName = parameterName;
	}
	
	public String getParameterName() {
		return parameterName;
	}
}
