import static org.junit.Assert.*;
import org.junit.Test;


/**
* <h1>QueueTester.java</h1>
* This class tests various scenarios by performing various 
* operations on queue object.
*
* @author  Pritesh Amrelia
* @version 1.0
* @since   09-09-2019
*/
public class QueueTester {

	/*
	 * Helper class to compare two processes 
	 * compare each attributes of given two processes
	 */
	public void compareProcesses(Process proc1, Process proc2){
		assertEquals(proc1.getName(), proc2.getName());
		assertEquals(proc1.getPid(), proc2.getPid());
		assertEquals(proc1.getPercntCpuUsage(), proc2.getPercntCpuUsage(), 0.0001);	// precision is added in 3rd argument
		assertEquals(proc1.getTotalCpuTime(), proc2.getTotalCpuTime());
		assertEquals(proc1.getNumberOfThreads(), proc2.getNumberOfThreads());
		assertEquals(proc1.getOwner(), proc2.getOwner());
	}
	
	
	/*
	 * Queue initialization test
	 * check capacity and size after initializing queue
	 */
	@Test
	public void QueueInitTest() {
		
		/*
		 * Most general case
		 * Initialization with capacity two
		 */
		Queue queueCapacityTwo = new Queue(2);
		assertEquals(queueCapacityTwo.getSize(), 0);
		assertEquals(queueCapacityTwo.getCapacity(), 2);
		
		/*
		 * Initialization with capacity four
		 */
		Queue queueCapacityFour = new Queue(4);
		assertEquals(queueCapacityFour.getSize(), 0);
		assertEquals(queueCapacityFour.getCapacity(), 4);

		/*
		 * Once with number which is not power of two
		 * Initialization with capacity three
		 */
		Queue queueCapacityThree = new Queue(3);
		assertEquals(queueCapacityThree.getSize(), 0);
		assertEquals(queueCapacityThree.getCapacity(), 3);
	}
	
	
	/*
	 * Queue enqueue functionality test
	 * Check whether process enqueued is not null
	 * Also verify by comparing each attributes of processes
	 */
	@Test
	public void QueueEnqueueTest() {
		Queue queue = new Queue(2);
		
		Process enqueuedProc = new Process("Eclipse", 291, 3.25, 122, 4, "Pritesh");
		queue.enqueue(enqueuedProc);
		
		/*
		 * Check if process is enqueued or not
		 * by comparing front process with null
		 */
		Process dequeuedProc = queue.getFrontProcess();
		assertNotNull(dequeuedProc);
		
		
		/*
		 * Check each attributes of process
		 * double value compared with 0.0001 precision
		 */
		compareProcesses(enqueuedProc, dequeuedProc);
	}
	
	
	/*
	 * Queue dequeue functionality test
	 * Check whether process dequeued is not null
	 * Also verify by comparing each attributes of processes
	 */
	@Test
	public void QueueDequeueTest() {
		Queue queue = new Queue(2);
		
		Process enqueuedProc = new Process("Chrome", 4025, 6.5, 101, 1, "Admin");
		queue.enqueue(enqueuedProc);

		/*
		 * Check if dequeued process is null or not
		 */
		Process dequeuedProc = queue.dequeue();
		assertNotNull(dequeuedProc);
		
		/*
		 * Check each attributes of dequeued process
		 * double value compared with 0.0001 precision
		 */
		compareProcesses(enqueuedProc, dequeuedProc);
	}
	
	
	/*
	 * Queue getSize functionality test
	 * Check size each time we enqueue process
	 */
	@Test
	public void QueueSizeTest() {
		Queue queue = new Queue(2);
		assertEquals(queue.getSize(), 0);

		/*
		 * Enqueue one process and check size of queue 
		 * Enqueue another process and check size of queue 
		 */
		queue.enqueue(new Process("cmd", 119, 2.0, 23, 2, "System"));
		assertEquals(queue.getSize(), 1);
	
		queue.enqueue(new Process("gnome-termial", 9625, 4.1, 46, 1, "Pritesh"));
		assertEquals(queue.getSize(), 2);
	}
	
	
	/*
	 * Queue getCapacity functionality test
	 * Check capacity each time we enqueue process
	 * Ensure that capacity is doubled when it queue is full
	 */
	@Test
	public void QueueCapacityTest() {
		/*
		 * Initialize queue with capacity equals to two and 
		 * check capacity of queue 
		 */

		Queue queue = new Queue(2);
		assertEquals(queue.getCapacity(), 2);
		
		/*
		 * Enqueue first process and check capacity of queue 
		 * Enqueue second process and check capacity of queue 
		 */
		queue.enqueue(new Process("cmd", 119, 2.0, 23, 2, "System"));
		assertEquals(queue.getCapacity(), 2);
	
		queue.enqueue(new Process("gnome-termial", 9625, 4.1, 46, 1, "Pritesh"));
		assertEquals(queue.getCapacity(), 2);
		
		/*
		 * Enqueue third process and check capacity of queue
		 * check whether capacity is doubled or not
		 * Enqueue up to five process and check capacity of queue 
		 */
		queue.enqueue(new Process("MATLAB", 13, 12.5, 923, 8, "Admin"));
		assertEquals(queue.getCapacity(), 4);
	
		queue.enqueue(new Process("sum.py", 65, 2.1, 40, 4, "Pritesh"));
		assertEquals(queue.getCapacity(), 4);
		
		queue.enqueue(new Process("addition.java", 325, 8.0, 132, 1, "Pritesh"));
		assertEquals(queue.getCapacity(), 8);
		
		/*
		 * Enqueue capacity is not reduced even after process
		 * is dequeued 
		 */
		Process proc = queue.dequeue();
		assertEquals(queue.getCapacity(), 8);
	}
	
	/*
	 * Dequeue on empty queue and check null process
	 * is returned by queue class
	 */	
	@Test
	public void dequeueOnEmptyQueue(){
		Queue queue = new Queue(2);
		
		/*
		 * Check that dequeued process is null
		 */
		Process dequeuedProc = queue.dequeue();
		assertNull(dequeuedProc);
	}
	
	
	/*
	 * Process sort functionality test
	 * Insert few process, sort them and fetch and compare
	 * with expected result process
	 */
	@Test
	public void ProcessSortByNameTest() {
		
		/*
		 * Enqueue few processes in queue
		 * Sort queue and verify sorting of queue
		 * by dequeue and comparing with expected process
		 */
		Queue queue = new Queue(2);
		assertEquals(queue.getCapacity(), 2);
		
		/*
		 * Five processes are enqueued in queue
		 */
		Process cmdProc = new Process("cmd", 119, 2.0, 23, 2, "System");
		Process terminalProc = new Process("termial", 9625, 4.1, 46, 1, "Pritesh");
		Process matlabProc = new Process("matlab", 13, 12.5, 923, 8, "Admin");
		Process pythonProc = new Process("sum.py", 65, 2.1, 40, 4, "Pritesh");
		Process javaProc = new Process("addition.java", 325, 8.0, 132, 1, "Pritesh");
		
		queue.enqueue(cmdProc);
		queue.enqueue(terminalProc);
		queue.enqueue(matlabProc);
		queue.enqueue(pythonProc);
		queue.enqueue(javaProc);
		
		
		/*
		 * Sort queue by name using name comparator. Note that
		 * by default sorting is performed in ascending order.
		 */
		Process sortedByNameProc[] = queue.displaySortedQueue(new NameComparator());
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(javaProc, sortedByNameProc[0]);
		compareProcesses(cmdProc, sortedByNameProc[1]);
		compareProcesses(matlabProc, sortedByNameProc[2]);
		compareProcesses(pythonProc, sortedByNameProc[3]);
		compareProcesses(terminalProc, sortedByNameProc[4]);
		
		
		/*
		 * Sort queue by name in reverse order.
		 */
		Process sortedByNameRevProc[] = queue.displaySortedQueue(new NameComparator(false));
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(javaProc, sortedByNameRevProc[4]);
		compareProcesses(cmdProc, sortedByNameRevProc[3]);
		compareProcesses(matlabProc, sortedByNameRevProc[2]);
		compareProcesses(pythonProc, sortedByNameRevProc[1]);
		compareProcesses(terminalProc, sortedByNameRevProc[0]);
	}
	
	
	/*
	 * Process sort functionality test
	 * Insert few process, sort them and fetch and compare
	 * with expected result process
	 */
	@Test
	public void ProcessSortByPidTest() {
		
		/*
		 * Enqueue few processes in queue
		 * Sort queue and verify sorting of queue
		 * by dequeue and comparing with expected process
		 */
		Queue queue = new Queue(2);
		assertEquals(queue.getCapacity(), 2);
		
		/*
		 * Five processes are enqueued in queue
		 */
		Process cmdProc = new Process("cmd", 119, 2.0, 23, 2, "System");
		Process terminalProc = new Process("termial", 9625, 4.1, 46, 1, "Pritesh");
		Process matlabProc = new Process("matlab", 13, 12.5, 923, 8, "Admin");
		Process pythonProc = new Process("sum.py", 65, 2.1, 40, 4, "Pritesh");
		Process javaProc = new Process("addition.java", 325, 8.0, 132, 1, "Pritesh");
		
		queue.enqueue(cmdProc);
		queue.enqueue(terminalProc);
		queue.enqueue(matlabProc);
		queue.enqueue(pythonProc);
		queue.enqueue(javaProc);
		
		
		/*
		 * Sort queue by pid using pid comparator. Note that
		 * by default sorting is performed in ascending order.
		 */
		Process sortedByPidProc[] = queue.displaySortedQueue(new PidComparator());
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(matlabProc, sortedByPidProc[0]);
		compareProcesses(pythonProc, sortedByPidProc[1]);
		compareProcesses(cmdProc, sortedByPidProc[2]);
		compareProcesses(javaProc, sortedByPidProc[3]);
		compareProcesses(terminalProc, sortedByPidProc[4]);
		
		
		/*
		 * Sort queue by PID in reverse order.
		 */
		Process sortedByPidRevProc[] = queue.displaySortedQueue(new PidComparator(false));
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(terminalProc, sortedByPidRevProc[4]);
		compareProcesses(javaProc, sortedByPidRevProc[3]);
		compareProcesses(cmdProc, sortedByPidRevProc[2]);
		compareProcesses(pythonProc, sortedByPidRevProc[1]);
		compareProcesses(matlabProc, sortedByPidRevProc[0]);
	}
	
	
	/*
	 * Process sort functionality test
	 * Insert few process, sort them and fetch and compare
	 * with expected result process
	 */
	@Test
	public void ProcessSortByOwnerTest() {
		
		/*
		 * Enqueue few processes in queue
		 * Sort queue and verify sorting of queue
		 * by dequeue and comparing with expected process
		 */
		Queue queue = new Queue(2);
		assertEquals(queue.getCapacity(), 2);
		
		/*
		 * Five processes are enqueued in queue
		 */
		Process cmdProc = new Process("cmd", 119, 2.0, 23, 2, "System");
		Process terminalProc = new Process("termial", 9625, 4.1, 46, 1, "Pritesh");
		Process matlabProc = new Process("matlab", 13, 12.5, 923, 8, "Admin");
		Process pythonProc = new Process("sum.py", 65, 2.1, 40, 4, "Pritesh");
		Process javaProc = new Process("addition.java", 325, 8.0, 132, 1, "Pritesh");
		
		queue.enqueue(cmdProc);
		queue.enqueue(terminalProc);
		queue.enqueue(matlabProc);
		queue.enqueue(pythonProc);
		queue.enqueue(javaProc);
		
		
		/*
		 * Sort queue by owner using owner comparator. Note that
		 * by default sorting is performed in ascending order.
		 */
		Process sortedByOwnerProc[] = queue.displaySortedQueue(new OwnerComparator());
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(matlabProc, sortedByOwnerProc[0]);
		compareProcesses(terminalProc, sortedByOwnerProc[1]);
		compareProcesses(pythonProc, sortedByOwnerProc[2]);
		compareProcesses(javaProc, sortedByOwnerProc[3]);
		compareProcesses(cmdProc, sortedByOwnerProc[4]);
		
		
		/*
		 * Sort queue by Owner in reverse order.
		 */
		Process sortedByOwnerRevProc[] = queue.displaySortedQueue(new OwnerComparator(false));
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(cmdProc, sortedByOwnerRevProc[4]);
		compareProcesses(javaProc, sortedByOwnerRevProc[3]);
		compareProcesses(pythonProc, sortedByOwnerRevProc[2]);
		compareProcesses(terminalProc, sortedByOwnerRevProc[1]);
		compareProcesses(matlabProc, sortedByOwnerRevProc[0]);
	}
	
	
	/*
	 * Process sort functionality test
	 * Insert few process, sort them and fetch and compare
	 * with expected result process
	 */
	@Test
	public void ProcessSortByTotalCpuTimeTest() {
		
		/*
		 * Enqueue few processes in queue
		 * Sort queue and verify sorting of queue
		 * by dequeue and comparing with expected process
		 */
		Queue queue = new Queue(2);
		assertEquals(queue.getCapacity(), 2);
		
		/*
		 * Five processes are enqueued in queue
		 */
		Process cmdProc = new Process("cmd", 119, 2.0, 23, 2, "System");
		Process terminalProc = new Process("termial", 9625, 4.1, 46, 1, "Pritesh");
		Process matlabProc = new Process("matlab", 13, 12.5, 923, 8, "Admin");
		Process pythonProc = new Process("sum.py", 65, 2.1, 40, 4, "Pritesh");
		Process javaProc = new Process("addition.java", 325, 8.0, 132, 1, "Pritesh");
		
		queue.enqueue(cmdProc);
		queue.enqueue(terminalProc);
		queue.enqueue(matlabProc);
		queue.enqueue(pythonProc);
		queue.enqueue(javaProc);
		
		
		/*
		 * Sort queue by total cpu time using TotalCpuTime comparator. Note that
		 * by default sorting is performed in ascending order.
		 */
		Process sortedByTotalCpuTimeProc[] = queue.displaySortedQueue(new TotalCpuTimeComparator());
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(cmdProc, sortedByTotalCpuTimeProc[0]);
		compareProcesses(pythonProc, sortedByTotalCpuTimeProc[1]);
		compareProcesses(terminalProc, sortedByTotalCpuTimeProc[2]);
		compareProcesses(javaProc, sortedByTotalCpuTimeProc[3]);
		compareProcesses(matlabProc, sortedByTotalCpuTimeProc[4]);
		
		
		/*
		 * Sort queue by total cpu time in reverse order.
		 */
		Process sortedByTotalCpuTimeRevProc[] = queue.displaySortedQueue(new TotalCpuTimeComparator(false));
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(matlabProc, sortedByTotalCpuTimeRevProc[4]);
		compareProcesses(javaProc, sortedByTotalCpuTimeRevProc[3]);
		compareProcesses(terminalProc, sortedByTotalCpuTimeRevProc[2]);
		compareProcesses(pythonProc, sortedByTotalCpuTimeRevProc[1]);
		compareProcesses(cmdProc, sortedByTotalCpuTimeRevProc[0]);
	}
	
	
	/*
	 * Process sort functionality test
	 * Insert few process, sort them and fetch and compare
	 * with expected result process
	 */
	@Test
	public void ProcessSortByPercntCpuUsageTest() {
		
		/*
		 * Enqueue few processes in queue
		 * Sort queue and verify sorting of queue
		 * by dequeue and comparing with expected process
		 */
		Queue queue = new Queue(2);
		assertEquals(queue.getCapacity(), 2);
		
		/*
		 * Five processes are enqueued in queue
		 */
		Process cmdProc = new Process("cmd", 119, 2.0, 23, 2, "System");
		Process terminalProc = new Process("termial", 9625, 4.1, 46, 1, "Pritesh");
		Process matlabProc = new Process("matlab", 13, 12.5, 923, 8, "Admin");
		Process pythonProc = new Process("sum.py", 65, 2.1, 40, 4, "Pritesh");
		Process javaProc = new Process("addition.java", 325, 8.0, 132, 1, "Pritesh");
		
		queue.enqueue(cmdProc);
		queue.enqueue(terminalProc);
		queue.enqueue(matlabProc);
		queue.enqueue(pythonProc);
		queue.enqueue(javaProc);
		
		
		/*
		 * Sort queue by CPU time in percentage using owner comparator. Note that
		 * by default sorting is performed in ascending order.
		 */
		Process sortedByPercntCpuUsageProc[] = queue.displaySortedQueue(new PercntCpuUsageComparator());
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(cmdProc, sortedByPercntCpuUsageProc[0]);
		compareProcesses(pythonProc, sortedByPercntCpuUsageProc[1]);
		compareProcesses(terminalProc, sortedByPercntCpuUsageProc[2]);
		compareProcesses(javaProc, sortedByPercntCpuUsageProc[3]);
		compareProcesses(matlabProc, sortedByPercntCpuUsageProc[4]);
		
		
		/*
		 * Sort queue by CPU time in percentage in reverse order.
		 */
		Process sortedByPercntCpuUsageRevProc[] = queue.displaySortedQueue(new PercntCpuUsageComparator(false));
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(matlabProc, sortedByPercntCpuUsageRevProc[4]);
		compareProcesses(javaProc, sortedByPercntCpuUsageRevProc[3]);
		compareProcesses(terminalProc, sortedByPercntCpuUsageRevProc[2]);
		compareProcesses(pythonProc, sortedByPercntCpuUsageRevProc[1]);
		compareProcesses(cmdProc, sortedByPercntCpuUsageRevProc[0]);
	}
	
	
	/*
	 * Process sort functionality test
	 * Insert few process, sort them and fetch and compare
	 * with expected result process
	 */
	@Test
	public void ProcessSortByNumberOfThreadsTest() {
		
		/*
		 * Enqueue few processes in queue
		 * Sort queue and verify sorting of queue
		 * by dequeue and comparing with expected process
		 */
		Queue queue = new Queue(2);
		assertEquals(queue.getCapacity(), 2);
		
		/*
		 * Five processes are enqueued in queue
		 */
		Process cmdProc = new Process("cmd", 119, 2.0, 23, 2, "System");
		Process terminalProc = new Process("termial", 9625, 4.1, 46, 1, "Pritesh");
		Process matlabProc = new Process("matlab", 13, 12.5, 923, 8, "Admin");
		Process pythonProc = new Process("sum.py", 65, 2.1, 40, 4, "Pritesh");
		Process javaProc = new Process("addition.java", 325, 8.0, 132, 1, "Pritesh");
		
		queue.enqueue(cmdProc);
		queue.enqueue(terminalProc);
		queue.enqueue(matlabProc);
		queue.enqueue(pythonProc);
		queue.enqueue(javaProc);
		
		
		/*
		 * Sort queue by owner using NumberOfThreads comparator. Note that
		 * by default sorting is performed in ascending order.
		 */
		Process sortedByNumberOfThreadsProc[] = queue.displaySortedQueue(new NumberOfThreadsComparator());
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(terminalProc, sortedByNumberOfThreadsProc[0]);
		compareProcesses(javaProc, sortedByNumberOfThreadsProc[1]);
		compareProcesses(cmdProc, sortedByNumberOfThreadsProc[2]);
		compareProcesses(pythonProc, sortedByNumberOfThreadsProc[3]);
		compareProcesses(matlabProc, sortedByNumberOfThreadsProc[4]);
		
		
		/*
		 * Sort queue by NumberOfThreads in reverse order.
		 */
		Process sortedByNumberOfThreadsRevProc[] = queue.displaySortedQueue(new NumberOfThreadsComparator(false));
		
		/*
		 * Verify sorted process with comparing with expected processes
		 */
		compareProcesses(matlabProc, sortedByNumberOfThreadsRevProc[4]);
		compareProcesses(pythonProc, sortedByNumberOfThreadsRevProc[3]);
		compareProcesses(cmdProc, sortedByNumberOfThreadsRevProc[2]);
		compareProcesses(javaProc, sortedByNumberOfThreadsRevProc[1]);
		compareProcesses(terminalProc, sortedByNumberOfThreadsRevProc[0]);
	}
}
