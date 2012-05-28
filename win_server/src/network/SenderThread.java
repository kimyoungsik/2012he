///*
// * package : network
// * class : SenderThread
// * 상속: 없음
// * 관련클래스 :
// * 기능 : 서버에 연결된 스레드로 계속 글자를 보내줌
// * 작성일 : 2011년 10월 25일
// * 작성자 : 김영식
// *
// *
// * 문제점
// * sender이 전역변수로 사용. 그렇게 사용하지 않으려 했으나. 맥이 서버 역활을 할때 문제 생김
// *
// *
// *
// * 11.11.1
// * 전체 사용자에게 보여지는
// * SendAll 추가
// */
//package network;
//
//
////import java.io.BufferedReader;
////import java.io.InputStreamReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class SenderThread extends Thread {
//
//    //메인뷰 어댑터
//    IMainViewAdapterForNetwork iMVAforN;
//    static public Boolean sender=false;
//
//    Socket socket;
//    static List<DataOutputStream> list =
//             Collections.synchronizedList(new ArrayList<DataOutputStream>());
//
//    static DataOutputStream writer;
//
//
//    public SenderThread(IMainViewAdapterForNetwork iMVAforN,Socket socket) {
//        this.socket = socket;
//        this.iMVAforN = iMVAforN;
//
//        try{
//             writer = new DataOutputStream(socket.getOutputStream());
//             list.add(writer);
//
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void SetSender()
//    {
//        this.sender = true;
//    }
//
//    public void run()
//    {
//        try{
//
//       //     PrintWriter writer = new PrintWriter(socket.getOutputStream());
//            while(true)
//            {
//                Thread.sleep(100);
//                if(sender==true)
//               {
//                //글자를 보낸
//                String str =this.iMVAforN.GetWrittenTextPane().getText();
//                str=str.replace("\n","/n/");
//                str=str.replace("\r", "/r/");
//
//                str=str.concat("\r\n");
//
//          //      if(str.equals("bye"))
//         //           break;
//
//                this.sendAll(str);
//
//                sender = false;
//                }
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println(e.getMessage());
//
//        }
//        finally
//        {
//            try
//            {
//                socket.close();
//            }
//            catch(Exception ignored)
//            {
//
//            }
//
//        }
//    }
//
//    private void sendAll(String str)
//    {
//        for(DataOutputStream writer:list)
//        {
//
//
//
//            try {
//				writer.writeChars(str);
//				 writer.flush();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//              //
//        }
//    }
//}
