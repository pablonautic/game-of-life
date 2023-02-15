package pl.life.logic;

import java.util.List;

public class SimulationRunner {

	IGrid<? extends AbstractCell> grid;

	public SimulationRunner(IGrid<? extends AbstractCell> grid) {
		this.grid = grid;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		IGrid<? extends AbstractCell> grid2 = grid.deepCopy();

		IGrid<? extends AbstractCell> tmpGrid;

		for (int k = 0; k < 200; k++) {

			System.out.println("Starting turn " + k);

			int num = getLivingCellsCount(grid);
			System.out.println("There are " + num + " living cells");
			if (num == 0) {
				System.out.println("\nThe colony died ;(");
				break;
			}

			List<? extends AbstractCell> source = grid.getLinearList();
			List<? extends AbstractCell> dest = grid2.getLinearList();
			resetGrid(grid2);

			for (int i = 0; i < source.size(); i++) {
				dest.get(i).setAlive(
						grid.cellState(getLivingNeighbourCount(source.get(i)),
								source.get(i).isAlive()));
			}

			tmpGrid = grid;
			grid = grid2;
			grid2 = tmpGrid;

		}
	}

	private void resetGrid(IGrid<? extends AbstractCell> grid1) {
		for (AbstractCell cell : grid1.getLinearList()) {
			cell.setAlive(false);
		}
	}

	private int getLivingCellsCount(IGrid<? extends AbstractCell> grid1) {
		int counter = 0;
		for (AbstractCell field : grid1.getLinearList()) {
			if (field.isAlive()) {
				counter++;
			}
		}
		return counter;
	}

	private int getLivingNeighbourCount(AbstractCell cell) {
		int counter = 0;
		List<? extends AbstractCell> neighbours = cell.getNeighbours();
		for (AbstractCell field : neighbours) {
			if (field.isAlive()) {
				counter++;
			}
		}
		return counter;
	}

	public IGrid<? extends AbstractCell> getGrid() {
		return grid;
	}

	public void setGrid(IGrid<? extends AbstractCell> grid) {
		this.grid = grid;
	}

}
