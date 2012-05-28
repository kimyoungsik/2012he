package threadPool;

import java.io.*;
import java.net.*;

import network.IMainViewAdapterForNetwork;

public class ChatServer implements Runnable {

    private ChatThreadPoolManager aChatThreadPoolManager; // ThreadPool을 관리하는
    // 관리자
    private ServerSocket aServerSocket; // 사용자 접속 소켓
    private Thread runner; // 사용자 접속을 처리하는 Thread
    private boolean isRun; // 현재 Thread의 running 여부
    private int type;
    private IMainViewAdapterForNetwork iMVAforN;

    public ChatServer(IMainViewAdapterForNetwork iMVAforN, int port, int type) {

        try {
            this.iMVAforN = iMVAforN;
            this.type = type;
            aChatThreadPoolManager = ChatThreadPoolManager.getInstance();
            aServerSocket = new ServerSocket(port);

        } catch (Exception e) {

            e.printStackTrace();
            System.exit(1);

        } // end try

    } // end public ChatServer()

    /**
     * Thread를 시작한다.
     */
    public void start() {

        if (runner == null) {

            runner = new Thread(this, "Chat Server");
            isRun = true;
            runner.start();

        } // end if (runner == null)
        else {

            stop();
            start();

        } // end else

    } // end public void start()

    /**
     * Thread를 종료한다.
     */
    public void stop() {

        if (runner != null) {

            isRun = false;
            runner.interrupt();
            try {
                runner.join();
            } catch (Exception e) {
            }
            runner = null;

        } // end if (runner != null)

    } // end public void stop()

    /**
     * 사용자 접속을 처리한다.
     */
    public void run() {

        ServerThread aServerThread = null; // 사용자를 처리하는 서버 Thread
        Socket aSocket = null; // 사용자의 소켓

        while (isRun == true) {

            try {

                aSocket = aServerSocket.accept();
                aServerThread = (ServerThread) aChatThreadPoolManager.getThreadObject();

                System.out.println("User Accept");

                if (aServerThread != null) {
                    // Pool에서 얻어 왔으면
                    aServerThread.setData(aSocket, this.iMVAforN, type);
                    aServerThread.notifyThread();

                } // end if (aServerThread != null)
                else {

                    sendFullMessage(aSocket);

                } // end else

                runner.yield();

            } catch (Exception e) {

                e.printStackTrace();

            } // end try

        } // end while (isRun == true)

        if (aServerSocket != null) {

            try {
                aServerSocket.close();
            } catch (IOException e) {
            }

        } // end if (aServerSocket != null)

    } // end public void run()

    /**
     * 사용자가 가득차 있다는 메시지를 전달한다.
     *
     * @param socket
     *            전송할 사용자의 Sockt 정보
     */
    public void sendFullMessage(Socket socket) {

        ObjectOutputStream sender = null;

        try {

            sender = new ObjectOutputStream(socket.getOutputStream());
            sender.reset();

            System.out.println("Server Busy");

        } catch (Exception e) {
        } finally {

            try {
                if (sender != null) {
                    sender.close();
                }
            } catch (Exception e) {
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
            }

        } // end finally

    } // end public void sendFullMessage(Socket socket)
}
