/*
 * package : view
 * class : IServerAdapter
 * 상속: 없음
 * 기능 :  서버 연결,끊기 위해 mainview(network_Server) 과  server를 연결해주는 어댑터
 * 작성일 : 2011년 10월 26일
 * 작성자 : 김영식
 * 
 */
package view;

public interface IServerAdapter {

	public abstract void ServerStart();

	public abstract String GetMessage();

	public abstract int GetPort();

	public abstract void SetPort(int port);
	
    public abstract void  SetType(int type);
}
