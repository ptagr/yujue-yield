import java.util.LinkedList;
import java.util.NoSuchElementException;

public class IteratorHelper<T> {
	private Thread t;
	
	/* The temporary holder for the current item */
	final LinkedList<T> queue = new LinkedList<T>();

	public IteratorHelper(Runnable r) {
		t = new Thread(r);
	}

	public void start() {
		t.start();
	}

	public T get() {
		synchronized (queue) {
			while (queue.isEmpty()) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					throw new NoSuchElementException();
				}

			}
			if (!queue.isEmpty()){
                return queue.removeFirst();
            }

		}
		return null;

	}

	public void yield(T v) {
		synchronized (queue) {
			queue.addLast(v);
			queue.notifyAll();
		}
	}

	public boolean done() {
		synchronized (queue) {
			while (queue.isEmpty()) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					throw new NoSuchElementException();
				}

			}
			if (!queue.isEmpty()){
                return true;
            }

		}
		return false;
	}
	
	public void setFinished() {
		synchronized (queue) {
			queue.notifyAll();
		}
		
	}
}
