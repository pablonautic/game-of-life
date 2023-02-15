package pl.life.logic.impl;

import java.util.ArrayList;
import java.util.List;

import pl.life.logic.AbstractCell;

public class SquareCell extends AbstractCell {

	private int x, y;
	private SquareGrid parent;

	public SquareCell(int x, int y, SquareGrid parent) {
		super();
		this.x = x;
		this.y = y;
		this.parent = parent;
	}

	public SquareCell(int x, int y, SquareGrid parent, boolean alive) {
		this(x, y, parent);
		this.alive = alive;
	}

	@Override
	public List<? extends AbstractCell> getNeighbours() {
		List<SquareCell> list = new ArrayList<SquareCell>();
		list.add(parent.getCell(x - 1, y - 1));
		list.add(parent.getCell(x + 0, y - 1));
		list.add(parent.getCell(x + 1, y - 1));
		list.add(parent.getCell(x - 1, y + 0));
		list.add(parent.getCell(x + 1, y + 0));
		list.add(parent.getCell(x - 1, y + 1));
		list.add(parent.getCell(x + 0, y + 1));
		list.add(parent.getCell(x + 1, y + 1));
		return list;
	}

}
