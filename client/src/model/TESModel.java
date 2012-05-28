package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import AbbrRepository.AbbrRepository;

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

	public void start() {
		newDocument();
	}

	// 열다
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

	// 잘못되었음 HDR파일 열다.
	public void newDocument() {

		currentFile = null;

		viewA.setTextPane("");
		viewA.setTitle("제목없음");

	}

	// 열다 - 어댑터에 이것으로 연결되어 있음
	public void loadDocument() throws IOException {
		fileChooser.setFileFilter(new CustomTxtFilter());
		int returnVal = fileChooser.showOpenDialog(viewA.getMainView());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentFile = fileChooser.getSelectedFile();
			StringBuilder buffer = new StringBuilder();

			try {
				FileInputStream fis = new FileInputStream(currentFile);
				InputStreamReader isr = new InputStreamReader(fis,
						Charset.forName("UTF-8"));
				Reader in = new BufferedReader(isr);

				int ch;
				while ((ch = in.read()) > -1) {
					buffer.append((char) ch);
				}
				in.close();

				viewA.setTextPane(buffer);
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
				out.write(viewA.getTextPane());
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
				String str=viewA.getTextPane();
				str = str.replace("\n","\r\n");
            
				out.write(str);
			} finally {
				if (out != null) {
					out.close();
				}
			}
			viewA.setTitle(currentFile.getName());
		}
	}

	private static class CustomTxtFilter extends
			javax.swing.filechooser.FileFilter {

		public boolean accept(File file) {

			return file.isDirectory()
					|| file.getAbsolutePath().endsWith(".txt");
		}

		public String getDescription() {

			return "Text documents (*.txt)";
		}
	}

	private static class CustomHdrFilter extends
			javax.swing.filechooser.FileFilter {

		public boolean accept(File file) {

			return file.isDirectory()
					|| file.getAbsolutePath().endsWith(".hdr");
		}

		public String getDescription() {

			return "Header file for DEAF Editor (*.hdr)";
		}
	}
}
