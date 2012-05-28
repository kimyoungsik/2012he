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

		// 메인 프래임 생성
		mainFrame = new JFrame();

		// Panel생성
		JPanel rootPanel = new JPanel(new BorderLayout());

		JPanel chooserPanel = new JPanel(new BorderLayout());
		// >>하위
		JPanel sizePanel = new JPanel(new BorderLayout());
		JPanel fontPanel = new JPanel(new BorderLayout());
		JPanel colorPanel = new JPanel(new BorderLayout());

		JPanel btnPanel = new JPanel();

		chooserPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		fontPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		btnPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		// 하위것 적용
		chooserPanel.add(sizePanel, BorderLayout.WEST);
		chooserPanel.add(fontPanel, BorderLayout.CENTER);
		chooserPanel.add(colorPanel, BorderLayout.EAST);

		// 최종적으로 적용
		rootPanel.add(chooserPanel, BorderLayout.CENTER);
		rootPanel.add(btnPanel, BorderLayout.SOUTH);

		// 폰트 사이즈 리스트 생성
		fontSizeChoice = new JList(fontSizes);

		// 폰트 사이즈를 위한 PANEL
		JPanel tmpPanel = new JPanel(new BorderLayout());
		tmpPanel.add(new JLabel("Size", JLabel.CENTER), BorderLayout.NORTH);
		// 그곳에 택스트 넣고
		sizeTextField = new JTextField(3);
		tmpPanel.add(sizeTextField, BorderLayout.SOUTH);

		// 사이즈는 임시PANEL넣음
		sizePanel.add(tmpPanel, BorderLayout.NORTH);
		sizePanel.add(new JScrollPane(fontSizeChoice), BorderLayout.CENTER);

		// 사이즈를 선택했을때
		fontSizeChoice.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// 텍스트가 그곳에 나타나게 함
				sizeTextField.setText(fontSizeChoice.getSelectedValue()
						.toString());
			}
		});

		// 폰트이름 설정 리스트
		fontNameChoice = new JList(GraphicsEnvironment
				.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());

		fontPanel.add(new JLabel("Font", JLabel.CENTER), BorderLayout.NORTH);
		fontPanel.add(new JScrollPane(fontNameChoice));

		// 색변환
		colorPanel.add(new JLabel("Color", JLabel.CENTER),
				BorderLayout.PAGE_START);

		// 테스트라는 테스트용 가운데
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

		// 글자색 선택
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

		// 배경색선택
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

		// ?
		textColorLabel.setBorder(BorderFactory
				.createTitledBorder("Color Preview"));

		JButton okButton = new JButton("Apply");
		btnPanel.add(okButton);

		// OK버튼을 눌렀을때
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Font newFont = getUpdatedFont();
				Color newForeColor = textColorLabel.getForeground();
				Color newBackColor = textColorLabel.getBackground();

				// 화면에 SET한다

				mv.setTextFontTracker(new FontTracker(newFont, newForeColor,
						newBackColor));

				mainFrame.dispose();
				mainFrame.setVisible(false);
			}

			private Font getUpdatedFont() {
				// 폰트 크기는 텍스트에서 가져오고
				int resultSize = Integer.parseInt(sizeTextField.getText());
				// 리스트에서 폰트 가져옴
				String resultFontName = (String) fontNameChoice
						.getSelectedValue();

				return new Font(resultFontName, Font.PLAIN, resultSize);
			}
		});

		// 취소버튼
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
