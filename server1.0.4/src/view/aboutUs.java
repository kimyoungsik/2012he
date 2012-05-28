package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class aboutUs extends JDialog implements ActionListener {
	public aboutUs(JFrame parent) {
		super(parent, "만든이", true);
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		JButton image = new JButton("");
		image.setIcon(new javax.swing.ImageIcon("img/aboutUs.png"));
		image.setPreferredSize(new Dimension(460, 170));
		image.setSize(460, 170);

		getContentPane().add(image, BorderLayout.CENTER);

		JPanel messagePane = new JPanel();
		JLabel version = new JLabel("DEAF - Ver 1.0.3");
		JLabel copyright = new JLabel(
				"Copyright ©2012 HeadFlow, All Rights reserved.");
		messagePane.add(version);

		messagePane.add(copyright);
		getContentPane().add(messagePane, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		this.setPreferredSize(new Dimension(460, 170));
		this.setSize(460, 170);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		dispose();
	}

}