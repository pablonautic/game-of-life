package pl.life.logic.impl;

import java.util.ArrayList;
import java.util.List;

import pl.life.logic.IGrid;

public class TriangularGrid implements IGrid<TriangularCell> {

	private List<TriangularCell> grid;
	private final int height;
	private final int width;

	public TriangularGrid(int width, int height) {
		this.width = width;
		this.height = height;
		grid = generateGrid(width, height);
	}

	@Override
	public boolean cellState(int livingNeighbourNumber, boolean isAlive) {
		if (isAlive) {
			switch (livingNeighbourNumber) {
			case 0:
				return false;
			case 1:
				return true;
			case 2:
				return true;
			case 3:
				return false;
			default:
				throw new IndexOutOfBoundsException(
						"invalid number of neighbours");
			}
		} else {
			if (livingNeighbourNumber == 2) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public List<TriangularCell> getLinearList() {
		return grid;
	}

	@Override
	public int getSides() {
		return 3;
	}

	private List<TriangularCell> generateGrid(int width, int height) {
		List<TriangularCell> linearList = new ArrayList<TriangularCell>();

		List<TriangularCell> firstRow, lastRow;

		List<TriangularCell> r1 = generateRow(width);
		List<TriangularCell> r2 = generateRow(width);
		linearList.addAll(r1);
		linearList.addAll(r2);

		firstRow = r1;
		lastRow = r2;

		for (int i = 0; i < width; i += 2) {
			TriangularCell gf1 = r1.get(i);
			TriangularCell gf2 = r2.get(i);
			gf1.setNeighbour(1, gf2);
			gf2.setNeighbour(1, gf1);
		}

		for (int j = 0; j < height / 2 - 1; j++) {

			r1 = generateRow(width);
			r2 = generateRow(width);

			linearList.addAll(r1);
			linearList.addAll(r2);

			for (int i = 0; i < width; i += 2) {
				TriangularCell gf1 = r1.get(i);
				TriangularCell gf2 = r2.get(i);
				gf1.setNeighbour(1, gf2);
				gf2.setNeighbour(1, gf1);
			}

			for (int i = 1; i < width; i += 2) {
				TriangularCell gf1 = r1.get(i);
				TriangularCell gf2 = lastRow.get(i);
				gf1.setNeighbour(1, gf2);
				gf2.setNeighbour(1, gf1);
			}

			lastRow = r2;
		}

		for (int i = 1; i < width; i += 2) {
			TriangularCell gf1 = firstRow.get(i);
			TriangularCell gf2 = lastRow.get(i);
			gf1.setNeighbour(1, gf2);
			gf2.setNeighbour(1, gf1);
		}

		return linearList;
	}

	private List<TriangularCell> generateRow(int width) {

		List<TriangularCell> linearList = new ArrayList<TriangularCell>();

		TriangularCell last, first;

		TriangularCell gf1 = new TriangularCell(getSides());
		TriangularCell gf2 = new TriangularCell(getSides());
		linearList.add(gf1);
		linearList.add(gf2);

		gf1.setNeighbour(0, gf2);
		gf2.setNeighbour(0, gf1);

		first = gf1;
		last = gf2;

		for (int j = 0; j < width / 2 - 1; j++) {

			gf1 = new TriangularCell(getSides());
			gf2 = new TriangularCell(getSides());
			linearList.add(gf1);
			linearList.add(gf2);

			gf1.setNeighbour(0, gf2);
			gf2.setNeighbour(0, gf1);

			last.setNeighbour(2, gf1);
			gf1.setNeighbour(2, last);
			last = gf2;
		}

		first.setNeighbour(2, last);
		last.setNeighbour(2, first);

		return linearList;
	}

	public IGrid<TriangularCell> deepCopy() {
		TriangularGrid tg = new TriangularGrid(width, height);
		for (int i = 0; i < getSize(); i++) {
			tg.getLinearList().get(i).setAlive(grid.get(i).isAlive());
		}
		return tg;
	}

	@Override
	public int getSize() {
		return grid.size();
	}

}
