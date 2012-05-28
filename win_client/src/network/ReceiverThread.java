
/*
 * package : network
 * class : ReceiverThread
 * 상속: 없음 
 * 관련클래스 : 
 * 기능 : 클라이언트 스레드로, 문자를 계속 받음
 * 작성일 : 2011년 10월 26일
 * 작성자 : 김영식
 * 
 * 문제점
 * allString이 전역변수로 사용되고 있음. 
 * 
 */
package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.text.BadLocationException;
//import view.MainView;

public class ReceiverThread extends Thread {

    private IMainViewAdapterForNetwork iMVAforN;
    private Socket socket;
    private int length = 0;
    private int limetedLine;
    private boolean endint = false;
    static public String allString = "";
    static DataInputStream in = null;
    static DataOutputStream writer = null;

    public ReceiverThread(IMainViewAdapterForNetwork iMVAforN, Socket socket) {
        this.socket = socket;
        this.iMVAforN = iMVAforN;
    }

    public void run() {
        //  this.limetedLine = MainView.limitedLine;
        try {
            //  BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            in = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());

            while (true) {

                writer.writeBoolean(endint);
                String str = in.readUTF();
                if (!str.equals("")) {
//                    str = str.replace("/n/", "\n");
//                    str = str.replace("/r/", "\r");
                    this.iMVAforN.GetWrittenTextPane().setText(str);
                    this.iMVAforN.GetShowTextPane().setText(str);
                    this.allString = str;

                    this.limetedLine = this.iMVAforN.GetLimitedLine();
                    int iLine = this.iMVAforN.GetShowTextPane().getLineCount();

                    if (iLine != 0) {
                        int iEndOffset = 0;
                        int removeOffset = 0;
                        try {
                            iEndOffset = this.iMVAforN.GetShowTextPane().getLineEndOffset(iLine - this.limetedLine - 1);
                        } catch (BadLocationException e) {
                            //line이 0보다 작을때는 무시
                        }

                        if (iEndOffset != 0) {

                            this.iMVAforN.GetShowTextPane().replaceRange("", 0, iEndOffset);


                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    public String GetAllText() {
        return this.allString;
    }
}
