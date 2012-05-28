package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FontChooserView extends JFrame {

	private static final long serialVersionUID = 6609284918912419504L;
	protected JFrame mainFrame;
	protected String resultName;
	protected int resultSize;
	protected MainView mv;

	// Working fields
	protected String fontSizes[] = { "8", "10", "12", "13", "14", "16", "18",
			"20", "24", "30", "36", "40", "48", "60", "72" };

	JList fontNameChoice;
	JList fontSizeChoice;

	JTextField sizeTextField;
	JLabel textColorLabel;

	public FontChooserView(MainView mv) {
		this.mv = mv;
		init();
	}

	public void init() {

		mainFrame = new JFrame();

		JPanel rootPanel = new JPanel(new BorderLayout());

		JPanel chooserPanel = new JPanel(new BorderLayout());

		JPanel sizePanel = new JPanel(new BorderLayout());
		JPanel fontPanel = new JPanel(new BorderLayout());
		JPanel colorPanel = new JPanel(new BorderLayout());

		JPanel btnPanel = new JPanel();

		chooserPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		fontPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		btnPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		chooserPanel.add(sizePanel, BorderLayout.WEST);
		chooserPanel.add(fontPanel, BorderLayout.CENTER);
		chooserPanel.add(colorPanel, BorderLayout.EAST);

		rootPanel.add(chooserPanel, BorderLayout.CENTER);
		rootPanel.add(btnPanel, BorderLayout.SOUTH);

		fontSizeChoice = new JList(fontSizes);

		JPanel tmpPanel = new JPanel(new BorderLayout());
		tmpPanel.add(new JLabel("Size", JLabel.CENTER), BorderLayout.NORTH);

		sizeTextField = new JTextField(3);
		tmpPanel.add(sizeTextField, BorderLayout.SOUTH);

		sizePanel.add(tmpPanel, BorderLayout.NORTH);
		sizePanel.add(new JScrollPane(fontSizeChoice), BorderLayout.CENTER);

		fontSizeChoice.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				sizeTextField.setText(fontSizeChoice.getSelectedValue()
						.toString());
			}
		});

		fontNameChoice = new JList(GraphicsEnvironment
				.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());

		fontPanel.add(new JLabel("Font", JLabel.CENTER), BorderLayout.NORTH);
		fontPanel.add(new JScrollPane(fontNameChoice));

		colorPanel.add(new JLabel("Color", JLabel.CENTER),
				BorderLayout.PAGE_START);

		textColorLabel = new JLabel("TEST", JLabel.CENTER);
		textColorLabel.setOpaque(true);

		colorPanel.add(textColorLabel, BorderLayout.CENTER);

		JPanel colorBtnPanel = new JPanel(new BorderLayout());

		JButton textColorButton = new JButton();

		textColorButton.setLayout(new BorderLayout());
		textColorButton.add(BorderLayout.NORTH, new JLabel("Select",
				JLabel.CENTER));
		textColorButton.add(BorderLayout.SOUTH, new JLabel("Text Color",
				JLabel.CENTER));

		textColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Color newColor = JColorChooser.showDialog(mainFrame,
						"Choose Text Color",

						textColorLabel.getForeground());
				if (newColor != null) {
					textColorLabel.setForeground(newColor);
				}
			}
		});
		JButton bgColorButton = new JButton();
		bgColorButton.setLayout(new BorderLayout());
		bgColorButton.add(BorderLayout.NORTH, new JLabel("Select",
				JLabel.CENTER));
		bgColorButton.add(BorderLayout.SOUTH, new JLabel("Background",
				JLabel.CENTER));
		bgColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Color newColor = JColorChooser.showDialog(mainFrame,
						"Choose Background Color",
						textColorLabel.getBackground());
				if (newColor != null) {
					textColorLabel.setBackground(newColor);
				}
			}
		});

		colorBtnPanel.add(textColorButton, BorderLayout.NORTH);
		colorBtnPanel.add(bgColorButton, BorderLayout.SOUTH);
		colorPanel.add(colorBtnPanel, BorderLayout.PAGE_END);

		textColorLabel.setBorder(BorderFactory
				.createTitledBorder("Color Preview"));

		JButton okButton = new JButton("Apply");
		btnPanel.add(okButton);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Font newFont = getUpdatedFont();
				Color newForeColor = textColorLabel.getForeground();
				Color newBackColor = textColorLabel.getBackground();

				mv.setTextFontTracker(new FontTracker(newFont, newForeColor,
						newBackColor));

				mainFrame.dispose();
				mainFrame.setVisible(false);
			}

			private Font getUpdatedFont() {
				int resultSize = Integer.parseInt(sizeTextField.getText());
				String resultFontName = (String) fontNameChoice
						.getSelectedValue();

				return new Font(resultFontName, Font.PLAIN, resultSize);
			}
		});

		JButton canButton = new JButton("Cancel");
		btnPanel.add(canButton);
		canButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set all values to null. Better: restore previous.
				resultName = null;
				resultSize = 0;

				mainFrame.dispose();
				mainFrame.setVisible(false);
			}
		});

		mainFrame.setContentPane(rootPanel);
		mainFrame.pack();
		mainFrame.setSize(400, 400);
		mainFrame.setLocation(250, 250);
	}

	public void setVisible(boolean b) {
		mainFrame.setVisible(b);
	}

	public void setSelectedTextFont(FontTracker fontTracker) {
		int size = fontTracker.getTextFont().getSize();
		String fontName = fontTracker.getTextFont().getName();

		sizeTextField.setText(Integer.toString(size));
		fontSizeChoice.setSelectedValue(Integer.toString(size), true);
		fontNameChoice.setSelectedValue(fontName, true);
		textColorLabel.setForeground(fontTracker.getForegroundColor());
		textColorLabel.setBackground(fontTracker.getBackgroundColor());
	}
}
