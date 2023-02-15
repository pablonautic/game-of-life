package pl.life.logic;

import java.util.List;

/**
 * Abstract base class for diffent types of cells
 * 
 * @author pawel
 * 
 */
public abstract class AbstractCell {

	/**
	 * tells if the cell is dead or alive
	 */
	protected boolean alive;

	/**
	 * Returns a list of neighbouring cells
	 * 
	 * @return
	 */
	public abstract List<? extends AbstractCell> getNeighbours();

	private boolean drawn;

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isDrawn() {
		return drawn;
	}

	public void setDrawn(boolean drawn) {
		this.drawn = drawn;
	}

}
