import java.util.LinkedList;
import java.util.NoSuchElementException;

public class IteratorHelper<T> {
	private Thread t;

	/* The temporary holder for the current item */
	private final LinkedList<T> queue = new LinkedList<T>();
	
	/* Finished indicator */
	private volatile Boolean finished = new Boolean(false);

	public IteratorHelper(Runnable r) {
		t = new Thread(r);
	}

	public void start() {
		t.start();
	}

	/**
	 * 
	 * @return
	 */
	public T get() {
		synchronized (queue) {
			while (queue.isEmpty() && !finished) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					throw new NoSuchElementException();
				}

			}
			
			if (!queue.isEmpty()) {
				return queue.remove();
			}
			
			if (finished) {
				throw new NoSuchElementException();
			}

		}
		return null;

	}

	public void yield(T v) {
		synchronized (queue) {
			queue.add(v);
			queue.notifyAll();
		}
	}

	public boolean done() {
		synchronized (queue) {
			while (queue.isEmpty() && !finished) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					throw new NoSuchElementException();
				}

			}
			
			if (!queue.isEmpty()) {
				return false;
			}
			
			if (finished) {
				return true;
			}


		}
		return false;
	}

	public void setFinished() {
		synchronized (queue) {
			finished = true;
			queue.notifyAll();
		}

	}
}
