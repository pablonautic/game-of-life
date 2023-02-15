package pl.life.ui;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JStatusBar extends JPanel {

	private JLabel label;

	public JStatusBar() {
		this(" ", null, javax.swing.SwingConstants.LEADING);
	}

	public JStatusBar(String text) {
		this(text, null, javax.swing.SwingConstants.LEADING);
	}

	public JStatusBar(String text, Icon icon, int horizontalAlignment) {
		label = new JLabel(text, icon, horizontalAlignment);
		setLayout(new BorderLayout());
		add(label, BorderLayout.CENTER);
	}

	public Icon getIcon() {
		return label.getIcon();
	}

	public String getText() {
		return label.getText();
	}

	public void setIcon(Icon icon) {
		label.setIcon(icon);
	}

	public void setText(String text) {
		label.setText(text);
	}

	private static final long serialVersionUID = 9073839168722355565L;

}
