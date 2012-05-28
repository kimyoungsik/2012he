package threadPool;

public abstract class ThreadObject {
	
	 abstract public void create() throws NotCreateException;
	 abstract public void destroy();
	 abstract public void waitThread();
	 abstract public void notifyThread();

}
