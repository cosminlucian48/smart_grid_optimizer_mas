package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

public final class ModelParameters implements Concept {

	private static final long serialVersionUID = 1L;
	
	private Integer loadfactor;
	private Integer genfactor;
	private Integer cellmaxbatlevel;
	
	public ModelParameters(Integer loadfactor, Integer genfactor, Integer cellmaxbatlevel) {
		super();
		this.loadfactor = loadfactor;
		this.genfactor = genfactor;
		this.cellmaxbatlevel = cellmaxbatlevel;
	}
	
	public Integer getLoadfactor() {
		return loadfactor;
	}
	public void setLoadfactor(Integer loadfactor) {
		this.loadfactor = loadfactor;
	}
	public Integer getGenfactor() {
		return genfactor;
	}
	public void setGenfactor(Integer genfactor) {
		this.genfactor = genfactor;
	}
	public Integer getCellmaxbatlevel() {
		return cellmaxbatlevel;
	}
	public void setCellmaxbatlevel(Integer cellmaxbatlevel) {
		this.cellmaxbatlevel = cellmaxbatlevel;
	}
}
