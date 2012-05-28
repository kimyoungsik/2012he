/*
 * package : network
 * class : Server
 * 상속: 없음 
 * 관련클래스 : network package, IServerAdapter
 * 기능 : 서버 연결 관리
 * 작성일 : 2011년 10월 25일
 * 작성자 : 김영식
 */
package network;

import threadPool.*;

public class Server {

    private IMainViewAdapterForNetwork iMVAforN;
    private String startMessage = "연결안됨";
    private int port = 9000;
    private int type = 0;
    
    public Server(IMainViewAdapterForNetwork iMVAforN) {
        this.iMVAforN = iMVAforN;
    }

    public void ServerStart() {

        try {
            ChatServer aChatServer = new ChatServer(this.iMVAforN, this.port, this.type);
            aChatServer.start();
            this.startMessage = "연결 대기중";
            System.out.println("Server Start");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.startMessage = "연결 실패";
        }
    }

//        finally {
//            try{
//               serverSocket.close();
//                 serverSocket = null;
//                 this.connected = false;
//
//            }
//            catch(Exception ignored)
//            {
//
//            }  
//        }

    public String GetMessage() {
        return this.startMessage;
    }

    public int GetPort() {
        return this.port;
    }

    public void SetPort(int port) {
        this.port = port;
    }

    public void SetType(int type) {
        this.type = type;
    }
}
