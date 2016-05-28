package testRND;

import pseudoRandom.AtomicPseudoRandom;
import pseudoRandom.ReentrantLockPseudoRandom;

public class TestRNDShort {
	static ReentrantLockPseudoRandom myRLPR = new ReentrantLockPseudoRandom(10);
	static AtomicPseudoRandom myAPR = new AtomicPseudoRandom(10);

	static final int HOW_LONG_TO_RUN = 1000;
	static final int NUMBER_OF_THREADS = 5;
	
	
	private static class RLPR implements Runnable {
		public int counter=0;
		public void run() {
			while (true) {
				myRLPR.nextInt(100); counter++;
			}
		}
	}
	
	private static class APR implements Runnable {
		public int counter=0;
		public void run() {
			while (true) {
				myAPR.nextInt(100); counter++;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		long startTime, endTime;
		Thread[] threads;
		int numberGenerated;

		
		
		RLPR[] rlprs = new RLPR[NUMBER_OF_THREADS];
		threads = new Thread[NUMBER_OF_THREADS];
		numberGenerated = 0;
		startTime = System.currentTimeMillis();
		for (int i=0; i<NUMBER_OF_THREADS; i++) {
			rlprs[i] = new RLPR();
			threads[i] = new Thread(rlprs[i]);
		}
		
		for (int i=0; i<NUMBER_OF_THREADS; i++) {
			threads[i].start();
		}
		Thread.sleep(HOW_LONG_TO_RUN);
		for (int i=0; i<NUMBER_OF_THREADS; i++) {
			threads[i].stop(); //NOTE: Deprecated but used just for a clean way to do some timing tests.
			numberGenerated += rlprs[i].counter;
		}
		endTime = System.currentTimeMillis();
		System.out.println("Locking version generated     " + numberGenerated + " random numbers in " + (endTime-startTime) + " ms");



		APR[] aprs = new APR[NUMBER_OF_THREADS];
		threads = new Thread[NUMBER_OF_THREADS];
		numberGenerated = 0;
		startTime = System.currentTimeMillis();
		for (int i=0; i<NUMBER_OF_THREADS; i++) {
			aprs[i] = new APR();
			threads[i] = new Thread(aprs[i]);
		}
		
		for (int i=0; i<NUMBER_OF_THREADS; i++) {
			threads[i].start();
		}
		Thread.sleep(HOW_LONG_TO_RUN);
		for (int i=0; i<NUMBER_OF_THREADS; i++) {
			threads[i].stop(); //NOTE: Deprecated but used just for a clean way to do some timing tests.
			numberGenerated += aprs[i].counter;
		}
		endTime = System.currentTimeMillis();
		System.out.println("Non-locking version generated " + numberGenerated + " random numbers in " + (endTime-startTime) + " ms");
	}

}