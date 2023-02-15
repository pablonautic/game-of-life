package pl.life.logic.impl;

import java.util.ArrayList;
import java.util.List;

import pl.life.logic.IGrid;

public class SquareGrid implements IGrid<SquareCell> {

	private SquareCell[][] cells;
	private final int width;
	private final int height;

	public SquareGrid(int width, int heigth) {
		this.width = width;
		this.height = heigth;
		cells = new SquareCell[width][heigth];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				cells[i][j] = new SquareCell(i, j, this);
			}
		}
	}

	@Override
	public boolean cellState(int livingNeighbourNumber, boolean isAlive) {
		if (isAlive) {
			switch (livingNeighbourNumber) {
			case 0:
				return false;
			case 1:
				return false;
			case 2:
				return true;
			case 3:
				return true;
			default: // 4 - 8
				return false;
			}

		} else {
			if (livingNeighbourNumber == 3) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public List<SquareCell> getLinearList() {
		List<SquareCell> result = new ArrayList<SquareCell>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				result.add(cells[i][j]);
			}
		}
		return result;
	}

	@Override
	public int getSides() {
		return 8;
	}

	@Override
	public int getSize() {
		return width * height;
	}

	@Override
	public IGrid<SquareCell> deepCopy() {
		SquareGrid sg = new SquareGrid(width, height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				sg.cells[i][j] = new SquareCell(i, j, sg, cells[i][j].isAlive());
			}
		}
		return sg;
	}

	public SquareCell getCell(int x, int y) {
		return cells[(x + width) % width][(y + height) % height];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setCellState(int x, int y, boolean isAlive) {
		getCell(x, y).setAlive(isAlive);
	}

}
