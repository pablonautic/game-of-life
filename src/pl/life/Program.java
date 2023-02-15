package pl.life;

import pl.life.logic.AbstractCell;
import pl.life.logic.SimulationRunner;
import pl.life.logic.impl.SquareGrid;
import pl.life.logic.impl.TriangularCell;
import pl.life.logic.impl.TriangularGrid;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TriangularGrid tg1 = new TriangularGrid(10, 10);

		TriangularCell gf = tg1.getLinearList().get(0);
		gf.setAlive(true);
		for (AbstractCell tmp : gf.getNeighbours()) {
			tmp.setAlive(true);
		}

		SquareGrid sg = new SquareGrid(10, 10);
		sg.setCellState(0, 0, true);
		sg.setCellState(1, 0, true);
		sg.setCellState(2, 0, true);

		SimulationRunner sr = new SimulationRunner(sg);
		sr.run();

		sr.setGrid(tg1);
		// sr.run();
	}
}
