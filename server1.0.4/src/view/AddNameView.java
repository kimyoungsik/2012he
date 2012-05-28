package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
//import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddNameView extends JDialog {



	private DeafBookView owner;
	private GridBagConstraints gbc = new GridBagConstraints();

	// 필드
	private JTextField titleField = new JTextField();


	private JLabel warning = new JLabel("");
	private JButton confirmBtn = new JButton("확인");
	private JButton cancelBtn = new JButton("취소");

	public AddNameView(DeafBookView owner) {
		super(owner, "성명추가", true);
		
		this.owner = owner;
		init();
	}

	private void init() {

		this.setSize(new Dimension(240, 100));
		this.setLayout(new GridBagLayout());
                this.setLocation(250,250);

		titleField.setText(null);


		gbc.fill = GridBagConstraints.HORIZONTAL;

		// 다이얼 로그에 배치하기
		addComponent(new JLabel("성명: "), 0, 0, 1);
		addComponent(titleField, 0, 1, 4);



		// 확인버튼을 클릭했을
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 타이틀필드가 비어있을떄
				if (titleField.getText().trim().isEmpty()) {
					warning.setText("성명을 적어주세요.");
				}
				else {
                                        owner.SetAddName(titleField.getText());
                                        owner.SetLibraryName(titleField.getText());
					
					dispose();
					
				}
			}
		});
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		addComponent(confirmBtn, 2, 3, 1);
		addComponent(cancelBtn, 2, 2, 1);
		addComponent(warning, 3, 0, 4);
	}

	private void addComponent(Component comp, int row, int col, int width) {
		gbc.gridx = col;
		gbc.gridy = row;
		gbc.gridwidth = width;
		add(comp, gbc);
	}

	
}
