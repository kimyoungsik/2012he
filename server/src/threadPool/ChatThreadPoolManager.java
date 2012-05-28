package threadPool;

public class ChatThreadPoolManager extends PoolManager {

	protected ChatThreadPoolManager() throws NotCreateException {
		super();
		// TODO Auto-generated constructor stub
	}

	/** Singleton 기법을 위해 */
	static private ChatThreadPoolManager aChatThreadPoolManager;

//	@Override
//	protected ThreadObject createThreadObject() throws NotCreateException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public static synchronized ChatThreadPoolManager getInstance()
			throws NotCreateException {

		if (aChatThreadPoolManager == null) {

			aChatThreadPoolManager = new ChatThreadPoolManager();

		} // end public static synchronized ChatThreadPoolManager getInstance()
			// throws NotCreateException

		return aChatThreadPoolManager;

	} // end public static synchronized ChatThreadPoolManager getInstance()
		// throws NotCreateException
	
	 /**
	  * 새로운 ThreadObject 객체를 얻어온다.
	  *
	  * @return 생성된 ThreadObject 객체
	  * @throws NotCreateException
	  */

	 protected synchronized ThreadObject createThreadObject() throws NotCreateException {

	  ThreadObject obj = new ServerThread();
	  obj.create();
	  pool.add( obj );
	  currentSize++;

	  return obj;

	 } // end private ThreadObject createThreadObject() throws NotCreateException
	
	

}
