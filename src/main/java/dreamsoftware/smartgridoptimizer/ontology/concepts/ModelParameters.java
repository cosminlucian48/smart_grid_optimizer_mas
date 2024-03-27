package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

import java.io.Serial;

/**
 * The ModelParameters class represents a concept containing various model parameters.
 * It implements the Concept interface.
 */
public final class ModelParameters implements Concept {

	@Serial
	private static final long serialVersionUID = 1L;

	private Integer loadFactor;
	private Integer genFactor;
	private Integer cellMaxBatLevel;

	/**
	 * Parameterized constructor to set the model parameters.
	 * @param loadFactor the load factor value.
	 * @param genFactor the generation factor value.
	 * @param cellMaxBatLevel the maximum battery level value.
	 */
	public ModelParameters(Integer loadFactor, Integer genFactor, Integer cellMaxBatLevel) {
		super();
		this.loadFactor = loadFactor;
		this.genFactor = genFactor;
		this.cellMaxBatLevel = cellMaxBatLevel;
	}

	/**
	 * Retrieves the load factor value.
	 * @return the load factor value.
	 */
	public Integer getLoadFactor() {
		return loadFactor;
	}

	/**
	 * Sets the load factor value.
	 * @param loadFactor the load factor value to set.
	 */
	public void setLoadFactor(Integer loadFactor) {
		this.loadFactor = loadFactor;
	}

	/**
	 * Retrieves the generation factor value.
	 * @return the generation factor value.
	 */
	public Integer getGenFactor() {
		return genFactor;
	}

	/**
	 * Sets the generation factor value.
	 * @param genFactor the generation factor value to set.
	 */
	public void setGenFactor(Integer genFactor) {
		this.genFactor = genFactor;
	}

	/**
	 * Retrieves the maximum battery level value.
	 * @return the maximum battery level value.
	 */
	public Integer getCellMaxBatLevel() {
		return cellMaxBatLevel;
	}

	/**
	 * Sets the maximum battery level value.
	 * @param cellMaxBatLevel the maximum battery level value to set.
	 */
	public void setCellMaxBatLevel(Integer cellMaxBatLevel) {
		this.cellMaxBatLevel = cellMaxBatLevel;
	}
}
