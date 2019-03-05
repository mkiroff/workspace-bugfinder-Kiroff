package simpleFindBugs;

/**
 * Troubling Rank Bugs Sample.
 */
public class TroublingRankBugs {

	/**
	 * Detect an empty synchronized block. An empty synchronized block will wait
	 * until nobody else is using that synchronizer. As there is no protected
	 * resource in the block, it doesn't have to make synchronization.
	 * @param start 
	 * @param end 
	 * @param str 
	 */
	void emptySynchronized() {
		synchronized (this) {
			// Forgot implementation
		}
	}

	/**
	 * Detect when Thread.sleep() is called in synchronized. This method calls
	 * Thread.sleep() with a lock held. This may result in very poor performance
	 * and scalability, or a deadlock, since other threads may be waiting to
	 * acquire the lock.
	 */
	void sleepInSynchronized() throws InterruptedException {
		synchronized (this) {
			Thread.sleep(5000);
		}
	}
}

