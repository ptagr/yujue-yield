yujue-yield
===========

This is a simple Java Implemention of an IteratorHelper. An example of its usage with a Range class is shown in the source code.

When an iteratorHelper is first created, it starts a new thread (runs it) which runs the run() method of the master class (Range in this example).

The run method should be implemented like it is done in Python. To generate a value, the yield method of the iteratorHelper is called.

for(Integer i = low; i<high; i++){
	ih.yield(i); //Generate value by calling yield
}

After the values are over, the setFinished() method of iteratorHelper must be called. If this is done, then the threads will not reach their completion, as we will see.

There is a queue in iteratorHelper to store the generated values. The size of the queue at any point depends upon how the threads are scheduled by the scheduler. A volatile "finished" boolean variable keeps track whether all the values have been generated. 

get method:
If the queue is empty and finished is false, then the thread waits until a value is put into the queue by the yield method or the "finished" is set to false by setFinished() method. When either happens, it returns accordingly.

done method:
very similar to get method, except it returns boolean value instead.

yield method: 
this method adds the passed object to the queue and notifies everyone waiting on the queue. 

setFinished method:
this method sets the "finished" variable to true and notifies everyone waiting on the queue. 

