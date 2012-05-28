package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//전체 판 jframe
public class KeyView extends JDialog {

	
	public boolean authorization;
	
	// 전체판이 root panel 을 가지고 있어요
	JPanel root_panel = new JPanel(new FlowLayout());

	private JLabel serialLabel = new JLabel("Serial Number");
	private JLabel warning = new JLabel("입력후 확인을 눌러주세요");
	
	private JTextField firstSerialText = new JTextField();
	private JTextField secondSerialText = new JTextField(); 
	private JTextField thirdSerialText = new JTextField();
	private JTextField lastSerialText = new JTextField();

	// 변 콤보 박스를 써요

	private JButton confirmBtn = new JButton("확인");

	public KeyView() {
		super();
		init();
	}

	public void init()// 시작 부분인데
	{

		root_panel.add(serialLabel);

		firstSerialText.setPreferredSize(new Dimension(100, 20));
		root_panel.add(firstSerialText);

		secondSerialText.setPreferredSize(new Dimension(100, 20));
		root_panel.add(secondSerialText);

		thirdSerialText.setPreferredSize(new Dimension(100, 20));
		root_panel.add(thirdSerialText);
		
		lastSerialText.setPreferredSize(new Dimension(100, 20));
		root_panel.add(lastSerialText);
		
		
		confirmBtn.setPreferredSize(new Dimension(60, 20));

		root_panel.add(confirmBtn);
		
		warning.setPreferredSize(new Dimension(170, 20));
		root_panel.add(warning);

		this.add(root_panel);

		// 크기 설정
		this.setPreferredSize(new Dimension(600, 70));
		this.setSize(760, 50);
		this.setMaximumSize(new Dimension(600, 70));
		this.setLocation(250, 250);
		this.setVisible(true);
		

		confirmBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String first = firstSerialText.getText();
				String second = secondSerialText.getText();
				String third = thirdSerialText.getText();
				String last = lastSerialText.getText();
				
				if (first.equals("1111") && second.equals("1111") && third.equals("1111") && last.equals("1111")) {

					//true
					  String str = "deaf ver 1.0.4";
				        
				     FileWriter fw = null;
					try {
						fw = new FileWriter(System.getProperty("user.home") + File.separator + "Library/deafsoftware.txt");
						fw.write(str);
					    fw.flush();
					    fw.close();
					    authorization = true;
					    
						
					} catch (IOException e1) {
						e1.printStackTrace();
						
					}
				    
				}
				else
				{
					warning.setText("잘못된 번호입니다");

				}
			}
		});

	}
        
        public boolean GetAuthorization()
        {
            return this.authorization;
        }
        public void SetAuthorization(boolean isAuthorization)
        {
            this.authorization = isAuthorization;
        }
        

}
