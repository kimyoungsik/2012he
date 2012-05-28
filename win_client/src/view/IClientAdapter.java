

/*
 * package : view
 * class : IClientAdapter
 * 상속: 없음
 * 기능 :  클라이언트 서버 연결,끊기 위해 mainview(network_Client) 과  receiverClient를 연결해주는 어댑터
 * 작성일 : 2011년 10월 25일
 * 작성자 : 김영식
 * 
 */


package view;
import network.receiverServer;

public interface IClientAdapter {

    public abstract void receiverStart();

    public abstract void receiverClose();

    public abstract String GetMessage();

    public abstract int GetPort();

    public abstract void SetPort(int port);

    public abstract String GetIp();

    public abstract void SetIp(String ip);
    
    public abstract receiverServer GetReceiverServer();
}
