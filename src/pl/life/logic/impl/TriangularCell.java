package pl.life.logic.impl;

import java.util.ArrayList;
import java.util.List;

import pl.life.logic.AbstractCell;

public class TriangularCell extends AbstractCell {

	private List<TriangularCell> neighbours = new ArrayList<TriangularCell>();

	public TriangularCell(int sides) {
		for (int i = 0; i < sides; i++) {
			neighbours.add(null);
		}
	}

	@Override
	public List<TriangularCell> getNeighbours() {
		return neighbours;
	}

	public void setNeighbour(int no, TriangularCell f) {
		neighbours.set(no, f);
	}

}
