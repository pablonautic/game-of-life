package pl.life.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import pl.life.logic.AbstractCell;
import pl.life.logic.IGrid;
import pl.life.logic.impl.TriangularCell;
import pl.life.logic.impl.TriangularGrid;

public class MainWindow extends JPanel implements MouseListener {

	private static final long serialVersionUID = -980656967544271658L;

	private Dimension area; // indicates area taken up by graphics
	private Vector<Rectangle> circles; // coordinates used to draw graphics
	private JPanel drawingPane;

	private final Color colors[] = { Color.red, Color.blue, Color.green,
			Color.orange, Color.cyan, Color.magenta, Color.darkGray,
			Color.yellow };
	private final int color_n = colors.length;

	private IGrid<? extends AbstractCell> grid;

	private Triangle start;

	public MainWindow() {
		super(new BorderLayout());

		area = new Dimension(0, 0);
		circles = new Vector<Rectangle>();

		// Set up the drawing area.
		drawingPane = new DrawingPane();
		drawingPane.setBackground(Color.white);
		drawingPane.addMouseListener(this);

		// Put the drawing area in a scroll pane.
		JScrollPane scroller = new JScrollPane(drawingPane);
		scroller.setPreferredSize(new Dimension(1024, 768));

		// Lay out this demo.
		add(scroller, BorderLayout.CENTER);

		JStatusBar sb = new JStatusBar();
		add(sb, BorderLayout.SOUTH);

		grid = new TriangularGrid(2, 3);
		grid.getLinearList().get(grid.getSize() / 2).setAlive(true);

		int side = 50;

		start = new Triangle(Triangle.Orientation.UP);
		start.vs[0] = new Point(0, 0);
		start.vs[1] = new Point(side, 0);
		start.vs[2] = new Point(side / 2, (int) (-side * (Math.sqrt(3) / 2.0)));
		start.translate(200, 200);
	}

	static public class Triangle {

		public static enum Orientation {
			UP, DOWN;

			public Orientation getOther() {
				return this == UP ? DOWN : UP;
			}
		}

		public Point[] vs;

		public Orientation orientation;

		public Triangle(Orientation orientation) {
			vs = new Point[3];
			this.orientation = orientation;
		}

		public Triangle getNeighbour(int i) {
			Triangle t = new Triangle(orientation.getOther());
			switch (i) {
			case 0:
				t.vs[0] = getVertex(vs[1], vs[2], vs[0]);
				t.vs[1] = vs[1];
				t.vs[2] = vs[2];
				break;
			case 1:
				t.vs[1] = getVertex(vs[0], vs[2], vs[1]);
				t.vs[0] = vs[0];
				t.vs[2] = vs[2];
				break;
			case 2:
				t.vs[2] = getVertex(vs[0], vs[1], vs[2]);
				t.vs[1] = vs[1];
				t.vs[0] = vs[0];
				break;
			}
			return t;
		}

		private Point getVertex(Point p1, Point p2, Point sym) {
			return new Point(-sym.x + p1.x + p2.x, -sym.y + p1.y + p2.y);
		}

		public int[] getXs() {
			int[] xs = new int[3];
			for (int i = 0; i < 3; i++) {
				xs[i] = vs[i].x;
			}
			return xs;
		}

		public int[] getYs() {
			int[] ys = new int[3];
			for (int i = 0; i < 3; i++) {
				ys[i] = vs[i].y;
			}
			return ys;
		}

		public void translate(int x, int y) {
			for (int i = 0; i < 3; i++) {
				vs[i].x += x;
				vs[i].y += y;
			}
		}

	}

	private void drawGrid(Graphics g, Triangle tg, TriangularCell cell) {
		cell.setDrawn(true);
		if (cell.isAlive()) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.YELLOW);
		}
		g.fillPolygon(tg.getXs(), tg.getYs(), 3);
		g.setColor(Color.BLACK);
		g.drawPolygon(tg.getXs(), tg.getYs(), 3);

		/*
		  for (int i = 2; i < 3; i++) { if
		  (!cell.getNeighbours().get(i).isDrawn()) { drawGrid(g,
		  tg.getNeighbour(i), cell.getNeighbours().get(i)); } }
		 */
		if (!cell.getNeighbours().get(2).isDrawn()) {
			drawGrid(g, tg.getNeighbour(2), cell.getNeighbours().get(2));
		}
		if (tg.orientation == Triangle.Orientation.UP) {
			if (!cell.getNeighbours().get(0).isDrawn()) {
				drawGrid(g, tg.getNeighbour(1), cell.getNeighbours().get(0));
			}
			if (!cell.getNeighbours().get(1).isDrawn()) {
				drawGrid(g, tg.getNeighbour(0), cell.getNeighbours().get(1));
			}
		} else {

			if (!cell.getNeighbours().get(0).isDrawn()) {
				drawGrid(g, tg.getNeighbour(0), cell.getNeighbours().get(0));
			}
			if (!cell.getNeighbours().get(1).isDrawn()) {
				drawGrid(g, tg.getNeighbour(1), cell.getNeighbours().get(1));
			}

		}

	}

	/** The component inside the scroll pane. */
	public class DrawingPane extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			/*
			 * Rectangle rect; for (int i = 0; i < circles.size(); i++) { rect =
			 * circles.elementAt(i); g.setColor(colors[(i % color_n)]);
			 * g.fillOval(rect.x, rect.y, rect.width, rect.height); }
			 */
			for (AbstractCell cell : grid.getLinearList()) {
				((TriangularCell) cell).setDrawn(false);
			}

			drawGrid(g, start, (TriangularCell) grid.getLinearList().get(
					grid.getSize() / 2));
		}
	}

	// Handle mouse events.
	public void mouseReleased(MouseEvent e) {
		final int W = 100;
		final int H = 100;
		boolean changed = false;
		if (SwingUtilities.isRightMouseButton(e)) {
			// This will clear the graphic objects.
			circles.removeAllElements();
			area.width = 0;
			area.height = 0;
			changed = true;
		} else {
			int x = e.getX() - W / 2;
			int y = e.getY() - H / 2;
			if (x < 0)
				x = 0;
			if (y < 0)
				y = 0;
			Rectangle rect = new Rectangle(x, y, W, H);
			circles.addElement(rect);
			drawingPane.scrollRectToVisible(rect);

			int this_width = (x + W + 2);
			if (this_width > area.width) {
				area.width = this_width;
				changed = true;
			}

			int this_height = (y + H + 2);
			if (this_height > area.height) {
				area.height = this_height;
				changed = true;
			}
		}
		if (changed) {
			// Update client's preferred size because
			// the area taken up by the graphics has
			// gotten larger or smaller (if cleared).
			drawingPane.setPreferredSize(area);

			// Let the scroll pane know to update itself
			// and its scrollbars.
			drawingPane.revalidate();
		}
		drawingPane.repaint();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Game of Life");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(new LifeMenuBar());
		// Create and set up the content pane.

		JComponent newContentPane = new MainWindow();
		newContentPane.setOpaque(true); // content panes must be opaque

		frame.setContentPane(newContentPane);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
