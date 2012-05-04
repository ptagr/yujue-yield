import java.util.LinkedList;
import java.util.NoSuchElementException;


public class IteratorHelper<T> {
	private Thread t;
	final LinkedList<T> queue = new LinkedList<T>();

	public IteratorHelper(Runnable r) {
		t = new Thread(r);
	}

	public void start() {
		t.start();
	}

	public T get() {
		synchronized (queue) {
			if (queue.isEmpty()) {
				throw new NoSuchElementException();
			}else{
				T elem = queue.removeFirst();
				queue.notifyAll();
				return elem;
			}
		}

	}

	public void yield(T v) {
		synchronized (queue) {
			queue.addLast(v);
			try {
				queue.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean done() {
		synchronized (queue) {
			return queue.isEmpty();
		}
	}
}
