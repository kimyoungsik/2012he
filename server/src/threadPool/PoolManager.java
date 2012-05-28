package threadPool;

import java.io.*;
//import java.net.*;
import java.util.*;

public abstract class PoolManager {

	protected int initSize = 2; // 초기 Pool Size
	protected int maxSize = 5; // 최대 Pool Size
	protected int currentSize; // 현재 Pool Size
	protected int usedCount = 0; // 현재 사용중인 갯수
	protected int waitingTime = 2000; // Pool을 다 썼을 경우 대기 시간
	protected java.util.Vector readyPool; // 사용 가능한 ThreadObject 관리
	protected java.util.Vector pool; // 전체 ThreadObject 관리

	private static PoolManager manager = null;

	protected PoolManager() throws NotCreateException {

		readyPool = new java.util.Vector();
		pool = new java.util.Vector();

		readProperties();
		initPool();

		System.out.println("현재 전체 " + currentSize + "개 Pooling 되어졌으며 "
				+ usedCount + "개 사용중입니다.");

	} // end private PoolManager()

	/**
	 * 환경 설정 파일을 읽어 설정한다.
	 */

	private void readProperties() {

		// Properties 파일을 얻어온다.

//		InputStream is = getClass().getResourceAsStream("/pool.properties");
//		Properties properties = new Properties();
//
//		int tmpInitSize;
//		int tmpMaxSize;
//		int tmpWaitingTime;
//
//		try {
//			// Properties 값을 통해 값을 설정해 준다.
//			properties.load(is);
//			tmpInitSize = Integer.parseInt(properties.getProperty("init_size",
//					Integer.toString(initSize)));
//			tmpMaxSize = Integer.parseInt(properties.getProperty("max_size",
//					Integer.toString(maxSize)));
//			tmpWaitingTime = Integer.parseInt(properties.getProperty(
//					"waiting_time", Integer.toString(waitingTime)));
//
//			initSize = tmpInitSize;
//			maxSize = tmpMaxSize;
//			waitingTime = tmpWaitingTime;
//
//			System.out.println("초기화 : init_size = " + initSize
//					+ ", max_size = " + maxSize + ", waiting_time = "
//					+ waitingTime);
//
//		} catch (Exception e) {
//
//			// e.printStackTrace();
//			System.out.println("properties file을 찾을수 없습니다.");
//			System.out.println("'pool.properties'파일을 CLASSPATH안에 만들어 주세요.");
//			System.out.println("Default 설정값으로 초기화 : init_size = " + initSize
//					+ ", max_size = " + maxSize + ", waiting_time = "
//					+ waitingTime);
//
//		} // end try
	
	System.out.println("Default 설정값으로 초기화 : init_size = " + initSize
			+ ", max_size = " + maxSize + ", waiting_time = "
			+ waitingTime);

	} // end private void readProperties()

	/**
	 * 각종 설정값을 초기화 한다.
	 */

	private void initPool() throws NotCreateException {

		// 초기값 만큼 풀을 생성한다.
		ThreadObject obj = null;
		currentSize = 0;
		usedCount = 0;

		for (int i = 0; i < initSize; i++) {

			try {

				obj = createThreadObject();
				obj.create();
				readyPool.add(obj);
				pool.add(obj);

			} catch (NotCreateException e) {
			} // end try

		} // end for

		if (currentSize == 0) {
//			throw new NotCreateException("ThreadObject 객체를 하나도 생성하지 못했습니다.");
			System.out.println("ThreadObject 객체를 하나도 생성하지 못했습니다.");
		} // end if (currentSize == 0)

	} // end private void initPool() throws NotCreateException

	/**
	 * Thread Object를 생성한다.
	 */

	protected abstract ThreadObject createThreadObject()
			throws NotCreateException;

	/**
	 * 전체 ThreadObject을 닫는다.
	 */

	private void destroyAll() {

		System.out.println("전체 ThreadObject을 닫습니다.");

		ThreadObject obj;
		int length = pool.size();

		for (int i = 0; i < length; i++) {

			obj = (ThreadObject) pool.elementAt(i);
			obj.destroy();

		} // end for

	} // end private destroyAll()

	/**
	 * ThreadObject을 새로 연결한다.
	 * 
	 * @throws NotCreateException
	 */

	public synchronized void reset() throws NotCreateException {

		System.out.println("전체 ThreadObject을 재 설정 합니다.");

		// 전체 ThreadObject을 닫는다.
		destroyAll();

		// 기본 풀들을 초기화 한다.
		readyPool.clear();
		pool.clear();

		// 새롭게 풀들을 설정한다.
		initPool();

	} // end public void reset() throws NotCreateException

	/**
	 * Pool에 있는 ThreadObject 하나를 얻어 온다. (만일 없으면 null을 리턴)
	 * 
	 * @return Pool에 있는 ThreadObject
	 * @throws NotCreateException
	 */

	private ThreadObject getObject() throws NotCreateException {

		ThreadObject obj = null;

		if (readyPool.size() > 0) {
			// readyPool에 남는 것이 있다면 할당한다.
			obj = (ThreadObject) readyPool.firstElement();
			readyPool.remove(0);

		} // end if (readyPool.size() > 0)

		else if (maxSize == 0 || currentSize < maxSize) {
			// 남는것이 없고 현재 최대 개수만큼 생성되지 않았다면 새로 생성한다.
			obj = createThreadObject();

		} // end else if (maxSize == 0 || currentSize < maxSize)

		if (obj != null) {

			usedCount++;

		} // end if (obj != null)

		return obj;

	} // end private ThreasdfadObject getObject() throws NotCreateException

	/**
	 * Pool에 있는 ThreadObject 하나를 얻어 온다. (만일 waitingTime값 만큼 기다린 다음 반납 된것이 없으면
	 * null을 리턴)
	 * 
	 * @return
	 * @throws NotCreateException
	 */

	public synchronized ThreadObject getThreadObject()
			throws NotCreateException {

		ThreadObject obj = null;
		long startTime = System.currentTimeMillis();

		while ((obj = getObject()) == null) {

			try {
				// 기다리는 시간동안 wait() 한다.
				// 하지만 기다리는 시간내에 반납되면 notify 하면 깨어난다.
				// 따라서 기다리는 시간 이내에 깨어 날 수도 있다.
				wait(waitingTime);

			} catch (InterruptedException e) {
			}

			if ((System.currentTimeMillis() - startTime) >= waitingTime) {
				// 기다리는 시간이 넘었다면 null을 리턴
				System.out.println("time out");
				return null;

			} // end if ((System.currentTimeMillis() - startTime) >= timeout)

		} // end while ((obj = getObject()) == null)

		System.out.println("현재 전체 " + currentSize + "개 Pooling 되어졌으며 "
				+ usedCount + "개 사용중입니다.");

		return obj;

	} // end public synchronized ThreadObject getThreadObject()

	/**
	 * ThreadObject를 Pool에 반납한다.
	 * 
	 * @param obj
	 *            반납한 객체
	 */

	public synchronized void release(ThreadObject obj) {

		readyPool.add(obj);
		usedCount--;

		// notify();
		notifyAll();

		System.out.println("반납 성공 : 현재 사용 개수 " + usedCount);

	} // end public synchronized void release(ThreadObject obj)

} // end public abstract class PoolManager

