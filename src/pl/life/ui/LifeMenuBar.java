package pl.life.ui;

// IntroExample.java
//
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class LifeMenuBar extends JMenuBar {

	String[] fileItems = new String[] { "New", "Open", "Save", "Exit" };
	String[] editItems = new String[] { "Undo", "Cut", "Copy", "Paste" };
	char[] fileShortcuts = { 'N', 'O', 'S', 'X' };
	char[] editShortcuts = { 'Z', 'X', 'C', 'V' };

	public LifeMenuBar() {

		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu otherMenu = new JMenu("Other");
		JMenu subMenu = new JMenu("SubMenu");
		JMenu subMenu2 = new JMenu("SubMenu2");

		// Assemble the File menus with mnemonics.
		ActionListener printListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Menu item [" + event.getActionCommand()
						+ "] was pressed.");
			}
		};
		for (int i = 0; i < fileItems.length; i++) {
			JMenuItem item = new JMenuItem(fileItems[i], fileShortcuts[i]);
			item.addActionListener(printListener);
			fileMenu.add(item);
		}

		// Assemble the File menus with keyboard accelerators.
		for (int i = 0; i < editItems.length; i++) {
			JMenuItem item = new JMenuItem(editItems[i]);
			item.setAccelerator(KeyStroke
					.getKeyStroke(editShortcuts[i], Toolkit.getDefaultToolkit()
							.getMenuShortcutKeyMask(), false));
			item.addActionListener(printListener);
			editMenu.add(item);
		}

		// Insert a separator in the Edit menu in Position 1 after "Undo".
		editMenu.insertSeparator(1);

		// Assemble the submenus of the Other menu.
		JMenuItem item;
		subMenu2.add(item = new JMenuItem("Extra 2"));
		item.addActionListener(printListener);
		subMenu.add(item = new JMenuItem("Extra 1"));
		item.addActionListener(printListener);
		subMenu.add(subMenu2);

		// Assemble the Other menu itself.
		otherMenu.add(subMenu);
		otherMenu.add(item = new JCheckBoxMenuItem("Check Me"));
		item.addActionListener(printListener);
		otherMenu.addSeparator();
		ButtonGroup buttonGroup = new ButtonGroup();
		otherMenu.add(item = new JRadioButtonMenuItem("Radio 1"));
		item.addActionListener(printListener);
		buttonGroup.add(item);
		otherMenu.add(item = new JRadioButtonMenuItem("Radio 2"));
		item.addActionListener(printListener);
		buttonGroup.add(item);
		otherMenu.addSeparator();
		otherMenu.add(item = new JMenuItem("Potted Plant", new ImageIcon(
				"image.gif")));
		item.addActionListener(printListener);

		// Finally, add all the menus to the menu bar.
		add(fileMenu);
		add(editMenu);
		add(otherMenu);
	}

}