package view;

import java.io.File;

public interface ITESModelAdapter {

	public abstract Object createRepo(String title, String source);

        public abstract void SetCurrentFile(File currentFile);
        
        public abstract File GetCurrentFile();
        
        public abstract void saveDocument();
        
        public abstract String saveTextfiles(String fileName);
        
        public abstract void saveOnefile(String fileName);
        
        
        
}
