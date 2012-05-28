/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

/**
 *
 * @author headflow
 */
public class PrintView  implements Printable{
     static AttributedString myStyledText = null;

    
    public void printPage (String fileName)
    {
//        String fileName = "C:\Temp\abc.txt";
 
        String mText = readContentFromFileToPrint(fileName);
 
        myStyledText = new AttributedString(mText);
 
        printToPrinter();

    }
    
     private static String readContentFromFileToPrint(String fileName) {
        String dataToPrint = "";
 
        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = input.readLine()) != null) {
                dataToPrint += line + "\r\n";
            }
        }     catch (Exception e) {
            return dataToPrint;
        }
        return dataToPrint;
    }
 
    public static void printToPrinter() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
 
//        PageFormat pf = new PageFormat();
//          Paper paper = new Paper();

//          paper.setSize(612.0, 216.0);
//          paper.setImageableArea(72,72, 540.0,144.0);

//          pf.setPaper(paper);
          
        Book book = new Book();
 
//            PageFormat pageFormat = new PageFormat();
            
        PageFormat pf = new PageFormat();
          Paper paper = new Paper();

          paper.setSize(612.0, 216.0);
//          paper.setImageableArea(72,72, 540.0,144.0);

          pf.setPaper(paper);
          
        
        book.append(new PrintView(),pf);
 
        printerJob.setPageable(book);
        
//        printerJob.setPrintable(book, pf);
//        printerJob.setPrintable(book);
//        printerJob.setP
        
//        PageFormat pForm = new PageFormat();
//    Paper paper = new Paper();
//    paper.setSize(594.936, 841.536);
//    paper.setImageableArea(43, 43, 509, 756);
//            
//    pForm.setPaper(paper);
    
    
//    printerJob.setPrintable(book.getPrintable(0), pForm);            
// printerJob.print();

        boolean doPrint = printerJob.printDialog();
 
        if (doPrint) {
            try {
//                printerJob.defaultPage(pForm);
                printerJob.print();
                           }  catch (PrinterException ex) {
                System.err.println("Error occurred while trying to Print: " + ex);
            }
        }
    
    }
   
    public int print(Graphics g, PageFormat format, int pageIndex) {
        Graphics2D graphics2d = (Graphics2D) g;
 
        graphics2d.translate(format.getImageableX(), format.getImageableY());
 
        graphics2d.setPaint(Color.black);
 
        Point2D.Float pen = new Point2D.Float();
        AttributedCharacterIterator charIterator = myStyledText.getIterator();
        LineBreakMeasurer measurer = new LineBreakMeasurer(charIterator, graphics2d.getFontRenderContext());
        float wrappingWidth = (float)format.getImageableWidth();
        while (measurer.getPosition() < charIterator.getEndIndex()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            pen.y += layout.getAscent();
            float dx = layout.isLeftToRight() ? 0 : (wrappingWidth - layout.getAdvance());
            layout.draw(graphics2d, pen.x + dx, pen.y);
            pen.y += layout.getDescent() + layout.getLeading();           
        }
        return 0;
    }

//     @Override
//    public int print(Graphics grphcs, PageFormat pf, int i) throws PrinterException {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

}
