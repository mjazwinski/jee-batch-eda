package pl.spiralarchitect.kplan.batch;

import java.io.Serializable;

public class KnowledgeResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 131234253L;
	private String title;
	private String published;

	public KnowledgeResource(String title, String published) {
		this.title = title;
		this.published = published;
	}

	public static Object fromString(String resourceAsString) {
		String[] resourceFields = resourceAsString.split(";");
		if (resourceFields.length < 2) {
			throw new InvalidResourceFormatException();
		}
		String rTitle = resourceFields[0].split(":")[1];
		String rPublished = resourceFields[1].split(":")[1];
		return new KnowledgeResource(rTitle, rPublished);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((published == null) ? 0 : published.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KnowledgeResource other = (KnowledgeResource) obj;
		if (published == null) {
			if (other.published != null)
				return false;
		} else if (!published.equals(other.published))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KnowledgeResource [title=" + title + ", published=" + published + "]";
	}

}
