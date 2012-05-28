
/*
 * package : view
 * class : Network_Client
 * 상속: JFrame
 * 관련클래스 : network package, IServerAdapter
 * 기능 : 네트워크 클라이언트 다이얼로그 뷰
 * 작성일 : 2011년 10월 27일
 * 작성자 : 김영식
 * 
 * 
 */


package view;

import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Network_Client extends JFrame {

    private static final long serialVersionUID = 23432423223912393L;
    protected GridBagConstraints gbc = new GridBagConstraints();
    // private IServerAdapter iSerA;
    private IClientAdapter iCliA;
    JPanel panel_root = new JPanel(new BorderLayout());
    JPanel panel_info = new JPanel(new BorderLayout());
    private JLabel connectStatus = new JLabel("연결상태");
    private JLabel ipAddress = new JLabel("IP주소");
    ;
    private JLabel portName = new JLabel("포트");
    ;
  
    private JTextField text_connectStatus = new JTextField();
    private JTextField text_ipAddress = new JTextField();
    private JTextField text_port = new JTextField();
    private JButton button_connet = new JButton("연결");
    private JButton button_disconnet = new JButton("끊기");

    public Network_Client(IClientAdapter iCliA) {
        super("클라트언트"); 
        this.iCliA = iCliA;
        init();
    }

    public void init() {
        panel_info.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //정보 panel

        panel_info.setLayout(new GridBagLayout());

        //연결안됨
        text_ipAddress.setText("");
        text_port.setText("9000");
            text_connectStatus.setText("연결안됨");




        //연결버튼 눌렀을때
        button_connet.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent event) {
                        text_connectStatus.setText("연결대기중");
                        String ip = text_ipAddress.getText();
                        String port = text_port.getText();
                        if (ip.equals("")) {
                            text_connectStatus.setText("IP Error");
                        } else if (port.equals("")) {
                            text_connectStatus.setText("Port Error");

                        } else {
                            iCliA.SetIp(ip);

                            int iPort = Integer.parseInt(port);
                            iCliA.SetPort(iPort);

                            iCliA.receiverStart();
                            text_connectStatus.setText(iCliA.GetMessage());

                        }

                    }
                });


        //끊기 버튼 눌렀을때 
        button_disconnet.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent event) {

                        iCliA.receiverClose();
                        text_connectStatus.setText(iCliA.GetMessage());



                    }
                });




        //배치

        gbc.fill = GridBagConstraints.HORIZONTAL;


        //상태
        addComponent(connectStatus, 3, 0, 1);
        addComponent(text_connectStatus, 3, 1, 4);
        //버튼 이동가능
        addComponent(button_connet, 4, 3, 1);
        addComponent(button_disconnet, 4, 4, 1);

        //아이피
        addComponent(ipAddress, 0, 0, 1);
        addComponent(text_ipAddress, 0, 1, 4);

        //포트
        addComponent(portName, 1, 0, 1);
        addComponent(text_port, 1, 1, 3);
        //  addComponent(button_port,3,4,1);

        //루트에 붙이기

        panel_root.add(panel_info, BorderLayout.CENTER);


        this.setContentPane(panel_root);
        this.pack();
        this.setSize(220, 140);
        this.setLocation(250, 250);


    }

    private void addComponent(Component comp, int row, int col, int width) {

        gbc.gridy = row;
        gbc.gridx = col;
        gbc.gridwidth = width;
        panel_info.add(comp, gbc);
    }
}
