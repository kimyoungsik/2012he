package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

//import view.AddAbbrResister;
//import view.RepoView;
//import view.KeyView;

import AbbrRepository.AbbrRepository;
import java.io.LineNumberReader;

public class TESModel {

    private IMainViewAdapter viewA;
    private File currentFile = null;
    private final JFileChooser fileChooser;
    private Map<String, AbbrRepository> repoList = new HashMap<String, AbbrRepository>();

    public TESModel(IMainViewAdapter viewA) {
        this.viewA = viewA;
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Documents/DeafDoc"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }
//	if ( System.getProperty("os.name").contains("Linux") {
//	     saver.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Documents"));
//	} else if ( System.getProperty("os.name").contains("Windows") {
//	     saver.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "My Documents"));
//	} else { // For Mac, which I don't know....
//	     // Handle Mac file system here.
//	} // End of if()...else if()...else block.

    public void start() {

        newDocument();


    }

    private String openHeader() {
        String header_path = new String();
        fileChooser.setFileFilter(new CustomHdrFilter());
        int returnVal = fileChooser.showOpenDialog(viewA.getMainView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File hdr = fileChooser.getSelectedFile();
            header_path = hdr.getPath();
        }
        return header_path;
    }

    public void newDocument() {

        currentFile = null;

        viewA.setTextPane("");
        viewA.setTitle("제목없음");
        String str = "속기자명:\n교과목명: \n담당교수:\n날짜(연/월/일):\n이용정책: 이 원고는 교육지원 서비스를 받는 학생을 위해 제공되었습니다.\n해당자 이외의 사람에 의한 사용은 금지되어 있습니다. 이 문서에 포함된 정보에는 특권이나 저작권이 있는, 또는 그 밖의 기밀사항을 포함합니다.\n";

        viewA.setTextPane(str);

    }

//	// 열다 - 어댑터에 이것으로 연결되어 있음
//	public void loadDocument() throws IOException {
//                
////                if(this.currentFile != null)
////                {
//            //파일이 있을때만 자동저장 
////                    viewA.AutoSave();
////                }
//                        
//            
//            
//		fileChooser.setFileFilter(new CustomTxtFilter());
//		int returnVal = fileChooser.showOpenDialog(viewA.getMainView());
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			currentFile = fileChooser.getSelectedFile();
//			StringBuilder buffer = new StringBuilder();
//
//			try {
//				FileInputStream fis = new FileInputStream(currentFile);
//				InputStreamReader isr = new InputStreamReader(fis,
//						Charset.forName("UTF-8"));
//				Reader in = new BufferedReader(isr);
//
//				int ch;
//				while ((ch = in.read()) > -1) {
//					buffer.append((char) ch);
//				}
//				in.close();
//
//				viewA.setTextPane(buffer);
//				viewA.setTitle(currentFile.getName());
//			} catch (FileNotFoundException fne) {
//				JOptionPane.showMessageDialog(viewA.getMainView(),
//						"File Not Found Exception.", "warning",
//						JOptionPane.WARNING_MESSAGE);
//				fne.printStackTrace();
//			}
//		}
//	}
    // 열다 - 어댑터에 이것으로 연결되어 있음
    public void loadDocument() throws IOException {

//                if(this.currentFile != null)
//                {
        //파일이 있을때만 자동저장 
        viewA.AutoSave();
//                }

        fileChooser.setFileFilter(new CustomTxtFilter());
        int returnVal = fileChooser.showOpenDialog(viewA.getMainView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
//			StringBuilder buffer = new StringBuilder();

            try {
                FileInputStream fis = new FileInputStream(currentFile);
                InputStreamReader isr = new InputStreamReader(fis,
                        Charset.forName("UTF-8"));
                BufferedReader in = new BufferedReader(isr);

                String allText = "";
                String lineString = "";

                LineNumberReader lineIn = new LineNumberReader(in);

                while ((lineString = lineIn.readLine()) != null) {

                    allText = allText.concat(lineString).concat("\n");
                }



                viewA.setTextPane(allText);
                viewA.setTitle(currentFile.getName());
            } catch (FileNotFoundException fne) {
                JOptionPane.showMessageDialog(viewA.getMainView(),
                        "File Not Found Exception.", "warning",
                        JOptionPane.WARNING_MESSAGE);
                fne.printStackTrace();
            }
        }
    }

    public void saveDocument() throws IOException {

        if (currentFile == null) {
            saveAsDocument();
        } else {
            OutputStreamWriter out = null;
            FileOutputStream fos = new FileOutputStream(currentFile);
            out = new OutputStreamWriter(fos, Charset.forName("UTF-8"));

            try {
                String saveString = viewA.getTextPane();
                saveString = saveString.replace("\n", "\r\n");
                out.write(saveString);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            viewA.setTitle(currentFile.getName());
        }
    }

    public void saveAsDocument() throws IOException {
        fileChooser.setFileFilter(new CustomTxtFilter());
        int returnVal = fileChooser.showSaveDialog(viewA.getMainView());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            // check the chosen file's extension
            if (!currentFile.getPath().endsWith(".txt")) {
                String filename = currentFile.getPath();
                filename = filename + ".txt";
                currentFile = new File(filename);
            }
            FileOutputStream fos = new FileOutputStream(currentFile);
            OutputStreamWriter out = new OutputStreamWriter(fos,
                    Charset.forName("UTF-8"));
            try {
                String saveString = viewA.getTextPane();
                saveString = saveString.replace("\n", "\r\n");
                out.write(saveString);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            viewA.setTitle(currentFile.getName());
        }
    }

    //반환 : 텍스트이름
    public String saveTextfiles(String fileName) throws FileNotFoundException, IOException {

        String newFileName = fileName.concat(".txt");
        String name = "Documents/DeafDoc/";
        name = name.concat(newFileName);

        OutputStreamWriter out = null;

        File f = new File(System.getProperty("user.home") + File.separator + name);
        //기존에 같은파일이 있으면

        String useName;
        if (f.isFile()) {
            String tempName = null;
            int i = 2;
            while (f.isFile()) {
                newFileName = fileName.concat(Integer.toString(i)).concat(".txt");
                tempName = "Documents/DeafDoc/".concat(newFileName);

                f = new File(System.getProperty("user.home") + File.separator + tempName);
                i++;
            }
            useName = tempName;
        } else {
            useName = name;
        }



        FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + File.separator + useName);

        out = new OutputStreamWriter(fos, Charset.forName("UTF-8"));


        try {
            out.write(viewA.getTextPane());
        } catch (IOException e) {
            System.out.println(e.toString());
        }


        if (out != null) {
            try {
                this.currentFile = f;
                out.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }   
            
        }

        return newFileName;
    }
    
    public void saveOnefile(String fileName) throws FileNotFoundException, IOException {

        String newFileName = fileName.concat(".txt");
        String name = "Documents/DeafDoc/";
        name = name.concat(newFileName);

        OutputStreamWriter out = null;

        FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + File.separator + name);

        out = new OutputStreamWriter(fos, Charset.forName("UTF-8"));


        try {
            out.write(viewA.getTextPane());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }   
            
        }
    }

    private static class CustomTxtFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File file) {
            // Allow just directories and files with ".txt" extension...
            return file.isDirectory()
                    || file.getAbsolutePath().endsWith(".txt");
        }

        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Text documents (*.txt)";
        }
    }

    private static class CustomHdrFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File file) {
            // Allow just directories and files with ".txt" extension...
            return file.isDirectory()
                    || file.getAbsolutePath().endsWith(".hdr");
        }

        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Header file for DEAF Editor (*.hdr)";
        }
    }

    public void SetCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    public File GetCurrentFile() {
        return this.currentFile;
    }

    public RepoCtrlManager createRepo(String title, String source) {
        RepoCtrlManager rcm = new RepoCtrlManager(this, title, source);

        repoList.put(title, rcm.getRepo());
        viewA.addRepoTab(title, rcm);
        return rcm;
    }

    public void updateRepo(AbbrRepository repo) {
        repoList.put(repo.getName(), repo);
    }

    public void removeRepo(AbbrRepository repo) {
        repoList.remove(repo.getName());
    }
}
