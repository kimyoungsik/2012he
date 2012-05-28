package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class AddAbbrShortcut extends JFrame {

	private static final long serialVersionUID = -7308345618188026866L;
	private JTextField text;
	private RepoView rv;
	private String orig;

	public AddAbbrShortcut(final RepoView rv, String orig) {
		super("새로운 축약어를 입력하세요.");
		this.rv = rv;
		this.orig = orig;
		text = new JTextField();

		init();
	}

	private void init() {
		setSize(300, 50);

		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!text.getText().isEmpty())
						close();
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					AddAbbrShortcut.this.dispose();
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		add(text);
	}

	private void close() {
		String abbr = text.getText();
		String conv = ";";
		if (abbr.endsWith(";")) {
			conv = ";";
			int end = abbr.length() - 1;
			abbr = abbr.substring(0, end);
		} else if (abbr.endsWith(".")) {
			int end = abbr.length() - 1;
			abbr = abbr.substring(0, end);
			conv = ".";
		} else if (abbr.endsWith("/")) {
			int end = abbr.length() - 1;
			abbr = abbr.substring(0, end);
			conv = "/";
		}

		rv.addWord(orig, abbr, conv);
		dispose();
	}

}
