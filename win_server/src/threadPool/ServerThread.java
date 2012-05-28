package threadPool;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import network.IMainViewAdapterForNetwork;

//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
public class ServerThread extends threadPool.ThreadObject
        implements Runnable {

    private Socket mySocket; // 나의 소켓
//	private ObjectInputStream reciver; // 클라인트로 부터 오는 바이트 스트림
//	private ObjectOutputStream sender; // 클라이트로 보낼 바이트 스트림
    private Thread runner; // 사용자 접속을 처리하는 Thread
    private boolean isRun; // 현재 Thread의 running 여부
    private Object lockObject; // 동기화 처리를 위해
    private IMainViewAdapterForNetwork iMVAforN;
    static public Boolean boolsender = false;
    private int type;
    static List<DataOutputStream> list = Collections.synchronizedList(new ArrayList<DataOutputStream>());
    private DataOutputStream writer;
    private DataInputStream in;
    // 생성자

    ServerThread() {

        lockObject = new Object();

    } // end ServerThread()

    /**
     * 통신및 사용자와 관리할 각종 객체를 설정한다.
     *
     * @param socket
     *            Client와 통신을 할 Socket
     * @throws Exception
     */
    public void setData(Socket socket, IMainViewAdapterForNetwork iMVAforN, int type) throws Exception {
        this.iMVAforN = iMVAforN;
        System.out.print("set data");
        mySocket = socket;
//		reciver = new ObjectInputStream(mySocket.getInputStream());
//		sender = new ObjectOutputStream(mySocket.getOutputStream());
        this.type = type;
        this.writer = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        list.add(this.writer);

    } // end public void setData(Socket socket) throw Exception

    @Override
    public void run() {

//		TransObject aTransObject = null;
//		int protocol = -1;

        while (isRun) {

            synchronized (lockObject) {
                // synchronized를 try 문 위에서 해주어야 한다.
                // 순서가 바뀌면 lock에서 풀리지 못한다.
                // 주의해야할 사항이다.

                try {


                    if (boolsender == true) {
                        int begin = 0;
                        int length = 0;
                        int share;
                        int denominator = 1;
                        int pos = this.iMVAforN.GetWrittenTextPane().getCaretPosition();
                        // mac
                        if (type == 0) {

                            length = pos;
                            begin = 0;

                        } // iphone/ipad
                        else if (type == 1) {
                            denominator = 200;
                            share = pos / denominator;
                            length = pos % denominator;

                            if (pos != 0 && length == 0) {
                                share--;
                                length = 200;

                            }
                            begin = share * denominator;
                        }


                        String text = this.iMVAforN.GetWrittenTextPane().getText(
                                begin, length);
                        if (length > 185 && length < 190) {
                            text = text.concat("<<cleared>>");
                        }
                        in.readBoolean();

                        this.sendAll(text);
                        boolsender = false;
                    }


//					aTransObject = (TransObject) reciver.readObject();

                    // 사용자의 명령이 무엇인지 알아 낸다.
//					protocol = aTransObject.getProtocol();
//					System.out.println(protocol);

//					switch (protocol) {

//					case 1000:
                    // 사용자 로그인 처리

//						send(1001, "접속 성공.");

//						break;

//					case 1040:
                    // 간단한 메시지 전달
//						send(1041, "받으시요... 받으시요.");

//						break;

//					} // end swtich (protocol)

                    // 자신의 자원을 양보하여 동시 사용을 높인다.
                    // 실제로 IO 부분에서 synchonize를 처리해 주지만 확인 사살 차원이다.
                    Thread.yield();

                } catch (SocketException e) {
                    // 접속 종료
                    disconnect();
                    // break를 부르면 Thread가 종료한다.
                    // break;

                } catch (IOException e) {
                    // 접속 종료
                    disconnect();
                    // break를 부르면 Thread가 종료한다.
                    // break;
                } catch (Exception e) {
                } // end try

            } // end synchronized(lockObject)

        } // end while(true)

    } // end public void run()

    @Override
    public void create() throws NotCreateException {
        // TODO Auto-generated method stub
        waitThread();

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        disconnect();
        stop();

    }

    @Override
    public void waitThread() {
        // TODO Auto-generated method stub
        try {

            lockObject.wait();

        } catch (Exception e) {
        }

    }

    @Override
    public void notifyThread() {
        // TODO Auto-generated method stub
        synchronized (lockObject) {
            // lock을 푼다.
            lockObject.notify();

        } // end synchronized(lockObject)

        if (runner == null) {
            // Thread가 null이 라면 시작한다.
            start();

        } // end if (runner == null)

    }

    public void start() {

        if (runner == null) {
            // 새로 Thread를 생성하여 실행한다.
            runner = new Thread(this, "Server Receiver");
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

            // Thread가 종료할때까지 대기한다.

            try {

                runner.join(1000);

            } catch (Exception e) {
            }

            // stop을 안불러 줘도 괜찮지만 확인 사살이다.
            runner.stop();
        } // end if (runner != null)

    } // end public void stop()

    private void disconnect() {

        try {

            // 소켓을 닫고 쓰레드를 종료한다.
//			try {
//				if (reciver != null)
//					reciver.close();
//			} catch (Exception e) {
//			}
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
            }
            try {
                if (mySocket != null) {
                    mySocket.close();
                }
            } catch (Exception e) {
            }

        } catch (Exception e) {
        } finally {

            try {

                // 순서에 주의
                // 반납후 wait 하여 멈추게 한다.
                ChatThreadPoolManager.getInstance().release(this);
                waitThread();

            } catch (Exception e) {
            }

        } // end try

    } // end private void disconnect()

    /**
     * 자신에게 메시지 전달
     *
     * @param protocol 전송할 Protocol
     * @param sendObject 전송할 Data
     */
    private void sendAll(String str) {
//		for (DataOutputStream writer : list) {

        try {
            writer.writeUTF(str);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
//		}
    }
//	 public void send(int protocol, Object sendObject) {
//
//	  send( new TransObject(protocol, sendObject) );
//
//	 } // end public void send(int protocol, Object sendObject)
    /**
     * 자신에게 메시지 전달
     *
     * @param aTransObject 전송할 객체
     */
//	 public void send(TransObject aTransObject) {
//
//	  try {
//
//	   sender.reset();
//	   sender.writeObject(aTransObject);
//
//	  } catch(IOException e) {
//
//	  } // end try
//
//	 } // end public void send(TransObject aTransObject)
} // end class ServerThread

