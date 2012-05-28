package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

//전체 판 jframe
public class AddAbbrResister extends JFrame {

	// 식
	private static final long serialVersionUID = -293873749829323434L;
	private RepoView rv;

	// 전체판이 root panel 을 가지고 있어요
	JPanel root_panel = new JPanel(new FlowLayout());

	private JLabel labelOriginal = new JLabel("단어");
	private JLabel labelAbbr = new JLabel("축약어");;
	private JLabel labelConverter = new JLabel("변형자");;

	private JTextField textOriginal = new JTextField();
	private JTextField textAbbr = new JTextField();

	// 변 콤보 박스를 써요

	private JComboBox comboConverter = new JComboBox();
	private JButton confirmBtn = new JButton("등록");

	String converter = ";";

	public AddAbbrResister(final RepoView rv) {
		super("축약어 등록");
		this.rv = rv;

		init();

	}

	public void init()// 시작 부분인데
	{

		root_panel.add(labelOriginal);

		textOriginal.setPreferredSize(new Dimension(150, 20));
		root_panel.add(textOriginal);

		root_panel.add(labelAbbr);
		textAbbr.setPreferredSize(new Dimension(100, 20));
		root_panel.add(textAbbr);

		root_panel.add(labelConverter);

		comboConverter.addItem(";");
		comboConverter.addItem(".");
		comboConverter.addItem("/");

		comboConverter.setPreferredSize(new Dimension(30, 20));
		comboConverter.setEditable(false);
		comboConverter
				.setUI((ComboBoxUI) MyComboBoxUI.createUI(comboConverter));
		root_panel.add(comboConverter);

		confirmBtn.setPreferredSize(new Dimension(60, 20));

		root_panel.add(confirmBtn);

		this.add(root_panel);

		// 크기 설정

		this.setPreferredSize(new Dimension(600, 70));
		this.setSize(550, 50);
		this.setMaximumSize(new Dimension(600, 70));
		this.setVisible(true);

		comboConverter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				converter = (String) comboConverter.getSelectedItem();
			}
		});

		confirmBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String original = textOriginal.getText();
				String abbr = textAbbr.getText();

				if (!original.isEmpty() && !abbr.isEmpty()) {

					rv.addWord(original, abbr, converter);
				}

			}
		});

	}

	static class MyComboBoxUI extends BasicComboBoxUI {
		public static ComponentUI createUI(JComponent c) {
			return new MyComboBoxUI();
		}

		protected JButton createArrowButton() {
			JButton button = new BasicArrowButton(BasicArrowButton.EAST);
			return button;
		}
	}

}
