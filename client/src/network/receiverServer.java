/*
 * package : network
 * class : receiverServer
 * 상속: 없음 
 * 관련클래스 : network package, IClientAdapter
 * 기능 : 클라이언트 관리
 * 작성일 : 2011년 10월 26일
 * 작성자 : 김영식
 * 
 * 
 */

package network;

import java.net.Socket;

public class receiverServer {

	private IMainViewAdapterForNetwork iMVAforN;

	private String message = "연결안됨";
	private Socket socket;
	private int port;
	private String ip;

	public receiverServer(IMainViewAdapterForNetwork iMVAforN) {
		this.iMVAforN = iMVAforN;
	}

	public void receiverStart() {
		try {

			this.socket = new Socket(this.ip, this.port);
			Thread thread2 = new ReceiverThread(this.iMVAforN, this.socket);

			thread2.start();
			this.message = "연결 성공";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			this.message = "연결 실패";
		}

	}

	public void receiverClose() {
		try {
			this.socket.close();
			this.message = "서버 끊기 성공";
		} catch (Exception ignored) {
			this.message = "서버 끊기 실패";
		}

	}

	public String GetMessage() {
		return this.message;
	}

	public int GetPort() {
		return this.port;
	}

	public void SetPort(int port) {
		this.port = port;
	}

	public String GetIp() {
		return this.ip;
	}

	public void SetIp(String ip) {
		this.ip = ip;
	}

	public receiverServer GetReceiverServer() {
		return this;
	}
}
