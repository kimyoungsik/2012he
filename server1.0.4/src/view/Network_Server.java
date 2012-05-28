/*
 * package : view
 * class : Network_Server
 * 상속: JFrame
 * 관련클래스 : network package, IServerAdapter
 * 기능 : 서버 다이얼로그 뷰
 * 작성일 : 2011년 10월 26일
 * 작성자 : 김영식
 * 
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextField;
import javax.swing.plaf.ComboBoxUI;

import view.AddAbbrResister.MyComboBoxUI;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Network_Server extends JFrame {

	private static final long serialVersionUID = 12192392103912393L;
	protected GridBagConstraints gbc = new GridBagConstraints();
	private IServerAdapter iSerA;

	JPanel panel_root = new JPanel(new BorderLayout());
	JPanel panel_info = new JPanel(new BorderLayout());

//	private JLabel connectCount = new JLabel("연결대수");

	private JLabel connectStatus = new JLabel("연결상태");
	private JLabel ipAddress = new JLabel("IP주소");
	private JLabel portName = new JLabel("포트");
	private JLabel typeName = new JLabel("타입");

//	private JTextField text_connectCount = new JTextField();
	private JTextField text_connectStatus = new JTextField();
	private JTextField text_ipAddress = new JTextField();
	private JTextField text_ip = new JTextField();
	private JTextField text_port = new JTextField();

//	private JButton button_connectCount = new JButton("수정");
	private JButton button_connet = new JButton("연결");
//	private JButton button_disconnet = new JButton("끊기");
	private JButton button_port = new JButton("수정");

	private JComboBox comboType = new JComboBox();

	public Network_Server(IServerAdapter iSerA)

	{
		super("Server");
		this.iSerA = iSerA;
		init();
	}

	public void init() {
		panel_info.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		// 정보 panel
		panel_info.setLayout(new GridBagLayout());
		// 아이피 주소 얻기.

		String str_ipAddress = null;
		try {
			str_ipAddress = "환경설정-네트워크-자체할당IP";
		} catch (Exception e) {
			str_ipAddress = e.getMessage();
			e.printStackTrace();
		}
		

		text_connectStatus.setText(iSerA.GetMessage());
		text_ipAddress.setText(str_ipAddress);
		text_ip.setText(getAddress());
		// 연결버튼 눌렀을때
		button_connet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				iSerA.SetType(comboType.getSelectedIndex());
				text_connectStatus.setText("클라이언트 연결대기중");
				iSerA.ServerStart();
				text_connectStatus.setText(iSerA.GetMessage());
			}
		});

		// 포트

		int port;
		port = iSerA.GetPort();
		String strPort = null;
		strPort = strPort.format("%d", port);

		text_port.setText(strPort);

		// 포트버튼 수정
		button_port.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String changedPort = null;
				changedPort = text_port.getText();
				int intChangedPort = Integer.parseInt(changedPort);
				iSerA.SetPort(intChangedPort);

				int port = iSerA.GetPort();
				String strPort = null;
				strPort = strPort.format("%d", port);

				text_port.setText(strPort);
			}
		});

		// 타
		comboType.addItem("Mac");
		comboType.addItem("iPhone/iPad");
	
		comboType.setPreferredSize(new Dimension(90, 20));
		comboType.setEditable(false);
		comboType.setUI((ComboBoxUI) MyComboBoxUI.createUI(comboType));

		int type = (comboType.getSelectedIndex());
		iSerA.SetType(type);

		gbc.fill = GridBagConstraints.HORIZONTAL;

		// 타입
		addComponent(typeName, 0, 0, 1);
		addComponent(comboType, 0, 1, 3);
		// 버튼 이동가능
		addComponent(button_connet, 0, 4, 1);

		// 상태
		addComponent(connectStatus, 1, 0, 1);
		addComponent(text_connectStatus, 1, 1, 4);

		// 아이피
		addComponent(ipAddress, 2, 0, 1);
		addComponent(text_ipAddress, 2, 1, 4);
		addComponent(text_ip, 3, 1, 4);
		// 포트
		addComponent(portName, 4, 0, 1);
		addComponent(text_port, 4, 1, 3);
		addComponent(button_port, 4, 4, 1);

		// 루트에 붙이기

		panel_root.add(panel_info, BorderLayout.CENTER);

		this.setContentPane(panel_root);
		this.pack();
		this.setSize(280, 180);
		this.setLocation(250, 250);

	}

	private void addComponent(Component comp, int row, int col, int width) {

		gbc.gridy = row;
		gbc.gridx = col;
		gbc.gridwidth = width;
		panel_info.add(comp, gbc);
	}

	private String getAddress() {
		String adAddress = "네트워크생성대기";
		NetworkInterface iface = null;
		try {
			for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces
					.hasMoreElements();) {
				iface = (NetworkInterface) ifaces.nextElement();
				InetAddress ia = null;

				for (Enumeration ips = iface.getInetAddresses(); ips
						.hasMoreElements();) {
					ia = (InetAddress) ips.nextElement();
					String address = ia.getHostAddress();
					if (address.contains(".") == true) {
						String[] all = address.split("\\.");
						int a = all.length;
						// if ("127.0.0.1".compareTo(address) != 0) {
						if ("169".compareTo(all[0]) == 0) {
							adAddress = address;
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return adAddress;
	}

}
