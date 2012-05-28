/*
 * 작성일 : 2011년 10월 30일 일
 * 작성자 : 김영식
 * 기능 : 네트워크와 메인뷰를 이어주는 어댑터.
 */
package network;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 *
 * @author developer
 */
public interface IMainViewAdapterForNetwork {
    //쓰여지는 텍스트부분
    public abstract JTextPane GetWrittenTextPane();
    
   //보여주는 택스트  
    public abstract JTextArea GetShowTextPane();
    
    //최대 제한 글자 줄 수 
    public abstract int GetLimitedLine();
   
}
