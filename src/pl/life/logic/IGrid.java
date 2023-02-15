package pl.life.logic;

import java.util.List;

/**
 * The grid (map of cells) interface
 * 
 * @author pawel
 * 
 * @param <T>
 */
public interface IGrid<T extends AbstractCell> {

	/**
	 * Returns a list of all cells contained in the map (in rather undefined
	 * order)
	 * 
	 * @return
	 */
	public List<T> getLinearList();

	/**
	 * A static-like method defining the rules of the game
	 * 
	 * @param livingNeighbourNumber
	 *            number of live neighbours of the cell
	 * @param isAlive
	 *            current state of the cell
	 * @return new cell state (isAlive)
	 */
	public boolean cellState(int livingNeighbourNumber, boolean isAlive);

	/**
	 * Returns the number of sides of a single grid field (3 for triangular
	 * etc.)
	 * 
	 * @return
	 */
	public int getSides();

	/**
	 * Total number of fields, in most cases equal to getLinearList().size()
	 * 
	 * @return
	 */
	public int getSize();

	/**
	 * Cloning method used for making a 'back-buffer' for the purposes of the
	 * game engine, it is crucial that is makes a deep copy
	 * 
	 * @return
	 */
	public IGrid<T> deepCopy();

}
