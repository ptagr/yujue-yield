
public class Range implements Runnable{

	private int low, high;
	IteratorHelper<Integer> ih  = new IteratorHelper<Integer>(this);
	
	
	public Range(int l, int h){
		low = l;
		high = h; 
		ih.start();
	}
	
	public boolean hasNext() {
		return ih.done();
	}
	
	public Integer next(){
		return ih.get();
	}
	
	@Override
	public void run() {
		for(Integer i = low; i<high; i++){
			ih.yield(i);
		}
	}

	public static void main(String[] args) {
		Range r = new Range(1, 3);
		while(r.hasNext()){
			System.out.println(r.next());
		}
	}
}
