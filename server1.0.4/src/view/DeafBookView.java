package view;

//import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Library;
import model.Student;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class DeafBookView extends javax.swing.JFrame {

    protected MainView mv;
    protected IDeafBookAdapter iDeafBA;
    protected ITESModelAdapter modelA;
    private String addName;
    private String libraryName;
    private String libraryAddress;
    private Student previousStudent;
    private Student currentStudent;
    //적용 클릭시 가져오는 
    private String currentLibraryName = "";
    private String currentLibraryAddress = "";

    public DeafBookView(MainView mv, IDeafBookAdapter iDeafBA,ITESModelAdapter modelA) {

        this.mv = mv;
        this.iDeafBA = iDeafBA;
        this.modelA = modelA;
        this.setLocation(250, 100);
        initComponents();
        Init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        LIST_NAME = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        LIST_LIBRARY = new javax.swing.JTable();
        TEXT_NAME = new javax.swing.JTextField();
        TEXT_LIBRARY = new javax.swing.JTextField();
        BTN_ADD_NAME = new javax.swing.JButton();
        BTN_DELETE_NAME = new javax.swing.JButton();
        BTN_LIBRARY = new javax.swing.JButton();
        BTN_DELETE_LIBRARY = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        BTN_APPLY = new javax.swing.JButton();
        BTN_CANCEL = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        LABEL_ERROR = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LIST_NAME.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "이름"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        LIST_NAME.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LIST_NAMEMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(LIST_NAME);
        LIST_NAME.getColumnModel().getColumn(0).setResizable(false);

        LIST_LIBRARY.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "라이브러리"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        LIST_LIBRARY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LIST_LIBRARYMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(LIST_LIBRARY);
        LIST_LIBRARY.getColumnModel().getColumn(0).setResizable(false);

        TEXT_NAME.setEditable(false);
        TEXT_NAME.setText("                     ");
        TEXT_NAME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TEXT_NAMEActionPerformed(evt);
            }
        });

        TEXT_LIBRARY.setEditable(false);
        TEXT_LIBRARY.setText("                  ");
        TEXT_LIBRARY.setName("TEXT_LIBRARY"); // NOI18N
        TEXT_LIBRARY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TEXT_LIBRARYActionPerformed(evt);
            }
        });

        BTN_ADD_NAME.setText("+");
        BTN_ADD_NAME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_ADD_NAMEActionPerformed(evt);
            }
        });

        BTN_DELETE_NAME.setText("-");
        BTN_DELETE_NAME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_DELETE_NAMEActionPerformed(evt);
            }
        });

        BTN_LIBRARY.setText("+");
        BTN_LIBRARY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_LIBRARYActionPerformed(evt);
            }
        });

        BTN_DELETE_LIBRARY.setText("-");
        BTN_DELETE_LIBRARY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_DELETE_LIBRARYActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(21, 21, 21)
                .add(BTN_ADD_NAME, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(BTN_DELETE_NAME, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 138, Short.MAX_VALUE)
                .add(BTN_LIBRARY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(BTN_DELETE_LIBRARY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(119, 119, 119))
            .add(jPanel1Layout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(1, 1, 1)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(jPanel1Layout.createSequentialGroup()
                .add(TEXT_NAME, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(TEXT_LIBRARY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 231, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(TEXT_NAME, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(TEXT_LIBRARY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, 0, 0, Short.MAX_VALUE)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 307, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(BTN_ADD_NAME, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(BTN_DELETE_NAME, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(BTN_LIBRARY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(BTN_DELETE_LIBRARY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        BTN_APPLY.setText("적용");
        BTN_APPLY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_APPLYActionPerformed(evt);
            }
        });

        BTN_CANCEL.setText("취소");
        BTN_CANCEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_CANCELActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(BTN_APPLY, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(BTN_CANCEL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(BTN_APPLY)
                .add(BTN_CANCEL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setText("축약 라이브러리 설정");

        LABEL_ERROR.setText(" ");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(32, 32, 32)
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanel1, 0, 334, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(LABEL_ERROR, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 175, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 141, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(LABEL_ERROR)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void TEXT_NAMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TEXT_NAMEActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_TEXT_NAMEActionPerformed

private void TEXT_LIBRARYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TEXT_LIBRARYActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_TEXT_LIBRARYActionPerformed

private void BTN_CANCELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_CANCELActionPerformed
    this.iDeafBA.Save();
    dispose();
}//GEN-LAST:event_BTN_CANCELActionPerformed

private void BTN_APPLYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_APPLYActionPerformed

    if (!"".equals(this.currentLibraryName) && !"".equals(this.currentLibraryAddress)) {
        if (mv.createRepo(currentLibraryName, currentLibraryAddress) != null) {
            
            //데이터베이스 저장 
            this.iDeafBA.Save();
            
            
            //자동서식
            String dateTime = getDateTime();
            String date = getDate();
            String name = this.currentStudent.GetName();
            String title = date+"_"+name+"_"+this.currentLibraryName;
            
            mv.setTextPane("");
//            mv.setTitle(title);
	    String str = "속기자명: "+name+"\n교과목명: "+this.currentLibraryName+"\n담당교수:\n날짜(연/월/일/시간): "+dateTime+"\n이용정책: 이 원고는 교육지원 서비스를 받는 학생을 위해 제공되었습니다.\n해당자 이외의 사람에 의한 사용은 금지되어 있습니다. 이 문서에 포함된 정보에는 특권이나 저작권이 있는, 또는 그 밖의 기밀사항을 포함합니다.\n";
	    mv.setTextPane(str);
               
                    //저장
                    
                    //같은 이름이 있으면. 2,3 붙여서
                    
//                try {   
                  String titleName =  this.modelA.saveTextfiles(title);
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(DeafBookView.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(DeafBookView.class.getName()).log(Level.SEVERE, null, ex);
//                }
            
            
            
                    mv.setTitle(titleName);
            dispose();
        }
    } else {
        this.LABEL_ERROR.setText("선택되지 않았습니다.");
    }
}//GEN-LAST:event_BTN_APPLYActionPerformed

    public void Init() {

        //리스트 뷰 지우기 
        this.DeleteAllListItems();

        //내부 데이터 채워넣기 //처음 켰을때로이동 
///   this.iDeafBA.Load();


        int nameCount = this.iDeafBA.GetLength();

        for (int i = 0; i < nameCount; i++) {
            //Student student = this.iDeafBA.GetAt(i);
            String name = this.iDeafBA.GetAt(i).GetName();
            this.LIST_NAME.setValueAt(name, i, 0);

        }


    }
//이름 추가 버튼 누르면 
private void BTN_ADD_NAMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_ADD_NAMEActionPerformed

//    if( !"".equals(this.TEXT_ADD_NAME.getText()))
//    {
    this.addName = "";

    AddNameView dialog = new AddNameView(
            DeafBookView.this);
    dialog.setVisible(true);



    if (!"".equals(addName)) {
        //데이터에 적는다.
        int index = this.iDeafBA.Record(this.addName);

        Student student = new Student();
        student = this.iDeafBA.GetAt(index);


        this.LIST_NAME.setValueAt(student.GetName(), index, 0);

        this.iDeafBA.Save();

    }


}//GEN-LAST:event_BTN_ADD_NAMEActionPerformed
//라이브러리 추가 버튼
private void BTN_LIBRARYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_LIBRARYActionPerformed

    if (this.currentStudent != null) {
        this.libraryName = "";
        this.libraryAddress = "";
        String[] blanks = {""};
        FindRepoDialog dialog = new FindRepoDialog(
                DeafBookView.this);
        dialog.setVisible(true);



        if (!"".equals(libraryName) && !"".equals(libraryAddress)) {
            //사람을 가져온다. 빈칸이면 라이브러리 추가 안함

            int length = this.currentStudent.GetLength();

            for (int i = 0; i < length; i++) {
                ((DefaultTableModel) this.LIST_LIBRARY.getModel()).removeRow(i);
                ((DefaultTableModel) this.LIST_LIBRARY.getModel()).addRow(blanks);
            }
            

            
            this.currentStudent.Record(libraryName, libraryAddress);

            for (int i = 0; i < this.currentStudent.GetLength(); i++) {
                Library library = new Library();
                library = this.currentStudent.GetAt(i);

                this.LIST_LIBRARY.setValueAt(library.GetName(), i, 0);
            }
            this.iDeafBA.Save();
        }
    } else {
        this.LABEL_ERROR.setText("이름이 선택되지 않았습니다.");
    }

}//GEN-LAST:event_BTN_LIBRARYActionPerformed
//이름리스트 클릭시 
private void LIST_NAMEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LIST_NAMEMouseClicked


    if (evt.getClickCount() == 1) {

        int seletedNameIndex = this.LIST_NAME.getSelectedRow();


        String[] blanks = {""};

        // 범위내의 것을 선택했을때 
        if (seletedNameIndex < this.iDeafBA.GetLength()) {
            this.currentStudent = this.iDeafBA.GetAt(seletedNameIndex);
            this.TEXT_NAME.setText(this.currentStudent.GetName());
            if (this.previousStudent != null) {
                int previousLength = this.previousStudent.GetLength();

                for (int i = 0; i < previousLength; i++) {
                    ((DefaultTableModel) this.LIST_LIBRARY.getModel()).removeRow(i);
                    ((DefaultTableModel) this.LIST_LIBRARY.getModel()).addRow(blanks);

                }

                int currentLength = this.currentStudent.GetLength();

                int i;
                for (i = 0; i < currentLength; i++) {
                    Library library = new Library();
                    library = this.currentStudent.GetAt(i);
                    this.LIST_LIBRARY.setValueAt(library.GetName(), i, 0);
                }

                if (currentLength < previousLength) {
                    while (i < previousLength) {
                        this.LIST_LIBRARY.setValueAt("", i, 0);
                        i++;
                    }
                }
            } //처음 클릭했을때 (이전에 선택한 학생이 없을때)
            else {
                int currentLength = this.currentStudent.GetLength();

                int i;
                for (i = 0; i < currentLength; i++) {
                    Library library = new Library();
                    library = this.currentStudent.GetAt(i);
                    this.LIST_LIBRARY.setValueAt(library.GetName(), i, 0);
                }

            }
            this.previousStudent = this.currentStudent;
            currentLibraryName = "";
            currentLibraryAddress = "";

            this.TEXT_LIBRARY.setText("");

        }
    }


}//GEN-LAST:event_LIST_NAMEMouseClicked

//라이브러리 리스트 클릭시
private void LIST_LIBRARYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LIST_LIBRARYMouseClicked

    if (evt.getClickCount() == 1) {

        int seletedLibraryIndex = this.LIST_LIBRARY.getSelectedRow();

        if (seletedLibraryIndex < this.currentStudent.GetLength()) {
            Library library = this.currentStudent.GetAt(seletedLibraryIndex);
            currentLibraryName = library.GetName();
            currentLibraryAddress = library.GetAddress();
            this.TEXT_LIBRARY.setText(currentLibraryName);
        }
    }
}//GEN-LAST:event_LIST_LIBRARYMouseClicked

//이름 제거 
private void BTN_DELETE_NAMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_DELETE_NAMEActionPerformed

    int seletedNameIndex = this.LIST_NAME.getSelectedRow();

    if (seletedNameIndex != -1) {
        String[] blanks = {""};
        this.currentStudent = this.iDeafBA.GetAt(seletedNameIndex);


        //라이브러리 지우기
        int length = this.currentStudent.GetLength();


        for (int i = 0; i < length; i++) {
            ((DefaultTableModel) this.LIST_LIBRARY.getModel()).removeRow(i);
            ((DefaultTableModel) this.LIST_LIBRARY.getModel()).addRow(blanks);
        }
        for (int i = 0; i < length; i++) {

            this.LIST_LIBRARY.setValueAt("", i, 0);

        }

        //이름 리스트 지우기 
        int previousLength = this.iDeafBA.GetLength();


        for (int i = 0; i < previousLength; i++) {
            ((DefaultTableModel) this.LIST_NAME.getModel()).removeRow(i);
            ((DefaultTableModel) this.LIST_NAME.getModel()).addRow(blanks);
        }


        //라이브러리 지우기 
        this.currentStudent.EraseAll();
        //이름 지우기
        this.iDeafBA.Erase(seletedNameIndex);


        int currentLength = this.iDeafBA.GetLength();

        for (int i = 0; i < currentLength; i++) {
            Student student = new Student();
            student = this.iDeafBA.GetAt(i);

            this.LIST_NAME.setValueAt(student.GetName(), i, 0);
        }


        //현재 이름 선택 null

        this.currentStudent = null;
        this.previousStudent = null;

        //텍스트에 지우기
        this.TEXT_NAME.setText("");
        this.TEXT_LIBRARY.setText("");


        this.iDeafBA.Save();
    } else {
        this.LABEL_ERROR.setText("이름이 선택되지 않았습니다.");
    }


    //바로 위의것을 현재 학생으로 바꾸기

//    if (seletedNameIndex > 0) {
//        this.currentStudent = this.iDeafBA.GetAt(seletedNameIndex - 1);
//        
//        this.TEXT_NAME.setText(this.currentStudent.GetName());
//        this.TEXT_LIBRARY.setText("");
//        
//        int changedLength = this.currentStudent.GetLength();
//
//        int i;
//        for (i = 0; i < changedLength; i++) {
//            Library library = new Library();
//            library = this.currentStudent.GetAt(i);
//            this.LIST_LIBRARY.setValueAt(library.GetName(), i, 0);
//        }
//
//
//
//    }
//    else
//    {

//        
//    }
//라이브러리 제거 
}//GEN-LAST:event_BTN_DELETE_NAMEActionPerformed

private void BTN_DELETE_LIBRARYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_DELETE_LIBRARYActionPerformed

    int seletedLibraryIndex = this.LIST_LIBRARY.getSelectedRow();

    if (seletedLibraryIndex != -1) {
        String[] blanks = {""};

        int length = this.currentStudent.GetLength();


        for (int i = 0; i < length; i++) {
            ((DefaultTableModel) this.LIST_LIBRARY.getModel()).removeRow(i);
            ((DefaultTableModel) this.LIST_LIBRARY.getModel()).addRow(blanks);
        }


        this.currentStudent.Erase(seletedLibraryIndex);

        for (int i = 0; i < this.currentStudent.GetLength(); i++) {
            Library library = new Library();
            library = this.currentStudent.GetAt(i);
            this.LIST_LIBRARY.setValueAt(library.GetName(), i, 0);
        }
        this.iDeafBA.Save();
    } else {
        this.LABEL_ERROR.setText("라이브러리를 선택 해 주세요.");
    }

   

}//GEN-LAST:event_BTN_DELETE_LIBRARYActionPerformed

    public void DeleteAllListItems() {
        String[] blanks = {""};
        int previousName = this.LIST_NAME.getRowCount();

        for (int i = 0; i < previousName; i++) {
            ((DefaultTableModel) this.LIST_NAME.getModel()).removeRow(i);
            ((DefaultTableModel) this.LIST_NAME.getModel()).addRow(blanks);
        }

        int previousLibrary = this.LIST_LIBRARY.getRowCount();

        for (int i = 0; i < previousLibrary; i++) {
            ((DefaultTableModel) this.LIST_LIBRARY.getModel()).removeRow(i);
            ((DefaultTableModel) this.LIST_LIBRARY.getModel()).addRow(blanks);
        }

    }
//    public void Save(String fileName) throws FileNotFoundException, IOException
//    { 
//        
//      String newFileName = fileName.concat(".txt");
//      String name = "Documents/DeafDoc/";
//      name = name.concat(newFileName);
//      
//      OutputStreamWriter out = null;
//      
//      File f = new File(System.getProperty("user.home") + File.separator + name);
//      
//      //기존에 같은파일이 있으면
//      
//      String useName;
//      if(f.isFile())
//      {
//          String tempName = null;
//          int i = 2;
//          while(f.isFile())
//          {
//              String reFileName = fileName.concat(Integer.toString(i)).concat(".txt");
//               tempName= "Documents/DeafDoc/".concat(reFileName);
//
//              f = new File(System.getProperty("user.home") + File.separator + tempName);
//              i++;
//          }
//          useName = tempName;
//      }
//      else
//      {
//          useName = name;
//      }
//      
//      
//      
//      FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + File.separator + useName);
//
//      out = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
//
//
//      try{
//          out.write(mv.getTextPane());
//      }
//       catch (IOException e) {
//               System.out.println(e.toString());
//      }
//
//
//      if (out != null) {
//            try {
//                this.modelA.SetCurrentFile(f);
//                out.close();
//            } catch (IOException e) {
//                System.out.println(e.toString());
//            }
//      }
//        
//    }
        
        
        
    public String GetAddName() {
        return this.addName;
    }

    public void SetAddName(String name) {
        this.addName = name;
    }

    public void SetLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public void SetLibraryAddress(String libraryAddress) {

        this.libraryAddress = libraryAddress;
    }
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(DeafBookView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(DeafBookView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(DeafBookView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(DeafBookView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            
//            
//            public void run() {
//                new DeafBookView(this.mv
//                      ).setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_ADD_NAME;
    private javax.swing.JButton BTN_APPLY;
    private javax.swing.JButton BTN_CANCEL;
    private javax.swing.JButton BTN_DELETE_LIBRARY;
    private javax.swing.JButton BTN_DELETE_NAME;
    private javax.swing.JButton BTN_LIBRARY;
    private javax.swing.JLabel LABEL_ERROR;
    private javax.swing.JTable LIST_LIBRARY;
    private javax.swing.JTable LIST_NAME;
    private javax.swing.JTextField TEXT_LIBRARY;
    private javax.swing.JTextField TEXT_NAME;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
