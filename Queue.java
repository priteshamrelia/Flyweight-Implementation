
/*
* <h1>Queue.java</h1>
*
* This class represents a queue (FIFO) of process objects.
* It supports primary queue functionalities such as enqueue and dequeue, 
* Also it provides implementation of for getting the first item, sorting 
* queue elements and iterating through the items in FIFO order.
* <p>
* Implementation of queue uses a doubly linked list. Linked List uses 
* Node class which privately defined within Queue class.
* Queue also supports resizing functinality. Reaching size of queue to capacity,
* queue extends itself by 100%. Thus amortized cost of n enqueue operation on 
* queue is O(n). So we can say that each operation takes constant time.
* Dequeue, getFront, getSize and getCapacity functions takes O(1) time.
* Queue also provides sort functionalities for process objects and uses merge 
* sort which takes O(nLogn) time.
* 
* @author  Pritesh Amrelia
* @version 1.0
* @since   09-09-2019
*/

class Queue{

    Node front;     // First element of queue
    Node rear;      // Last element of queue
    int size;       // Actual number of elements present in queue
    int capacity;   // Actual number of elements can be filled in queue
    
    
    /*
    * Private Node class that holds process object
    * As queue uses doubly linked list, node provides two pointer to Node
    * object
    */
    private class Node{
        Process proc;
        Node next;
        Node prev;

        /*
        * Node object initialization, it initialize both node pointers 
        * to null which are assigned node later
        */
        Node(Process proc){
            this.proc = proc;
            this.next = null;
            this.prev = null;
        }
        
        /*
        * Method provides bidirection link between two Nodes.
        * First node's next is linked to Second node
        * Second node's prev is linked to First node
        */
        private void createLink(Node nextNode){
            this.next = nextNode;
            nextNode.prev = this;
        }
    }
    
    /*
    * Empty queue with given capacity (initially 2) is initialized with 
    * empty nodes
    */
    Queue(int capacity){
        this.capacity = 0;
        this.size = 0;
        this.front = this.rear = createEmptyNodes(capacity);
    }

    /*
    * Mehods creates empty nodes and links each other bidirectionally
    * Also last node is linked to first node to create circular linked list
    * Number of nodes created are equal to capacity given.
    * @param int how many empty nodes to be created
    * @return Node head pointer of created circular doubly linked list
    */
    private Node createEmptyNodes(int capacity){
        this.capacity += capacity;
        
        Node headNode = new Node(null);
        
        Node prevNode = headNode;
        Node newNode = null;
        
        int count = 1;
        while(count<capacity){
            newNode = new Node(null);
            prevNode.createLink(newNode);
            
            prevNode = newNode;
            count+=1;
        }
        
        // Link last node to first node to create circular linked list
        newNode.createLink(headNode);
            
        // return head pointer of empty circular doubly linked list
        return headNode;
    }
    
    
    /*
    * Mehods enlarge queue by 100% upon request by creating extra empty nodes
    * and links each other bidirectionally
    * Also creates appropriate links between created empty linked list and 
    * existing filled linked list 
    */
    private void enlargeQueue(){

        // Creates empty doubly linked nodes equals to capacity
        Node headNode = createEmptyNodes(this.getCapacity());
        
        Node filledLastNode = this.rear.prev;
        Node filledFirstNode = this.front;
        
        Node emptyFirstNode = headNode;
        Node emptyLastNode = headNode.prev;
        
        // Creates link between empty linked list and filled linked list
        filledLastNode.createLink(emptyFirstNode);
        emptyLastNode.createLink(filledFirstNode);
        
        this.rear = emptyFirstNode;
    }
    
    /*
    * Method gives actual number of elements in queue
    * @return int number of elements present in queue
    */
    public int getSize(){
        return this.size;
    }
    
    /*
    * Method gives capacity of queue
    * @return int number of elements can be filled in queue
    */
    public int getCapacity(){
        return this.capacity;
    }
    
    /*
    * Method enqueues Process object at rear side of queue
    * Upon reaching size of queue to full, it enlarge queue by 100%
    * Amortized cost of n enqueue operation is O(n), thus each operation
    * takes average O(1) time.
    * @param Process object to be enqueued in queue
    */
    public void enqueue(Process proc){
        
        // Ensuring size is not reached to its capacity
        // If so, double the capacity of queue
        if(this.getSize() == this.getCapacity()){
            this.enlargeQueue();
        }
        
        // New process elements is added at rear of queue
        this.rear.proc = proc;
        this.rear = this.rear.next;
        this.size += 1;
    }
    
    
    /*
    * Method dequeues Process object from front of queue
    * Dequeue operation takes O(1) time
    * @return Process object dequeued from queue
    */
    public Process dequeue(){
        
        // Return null element if queue is empty
        if(0 == this.getSize()){
            return null;
        }
        
        // Dequeue process element from front of queue
        Process proc = this.front.proc;
        this.front.proc = null;
        this.front = this.front.next;
        this.size -= 1;
        
        return proc;
    }
    
    /*
    * Method returns front process object from queue
    * Operation takes O(1) time
    * Front element is not removed from queue like dequeue operation
    * @return Process object from front of queue
    */
    public Process getFrontProcess(){
        
        // Return null element if queue is empty
    	if(0 == this.getSize()){
            return null;
        }
        
        // Return front process element of queue
        Process proc = this.front.proc;
        return proc;
    }
    
    
    /*
    * Method traverses queue and print information of each process element 
    * Operation takes O(n) time to visit each node
    */
    public void printQueue(){
        
        // Return and print nothing in case, queue is empty
        if(0 == this.getSize())
            return;
        
        System.out.println("------------");
        
        // start travering from front element of queue
        Node itrNode = this.front;
        do{
            Process proc = itrNode.proc;
            
            // if node does not contain null process, 
            // display process information
            if(null != proc){
                proc.printInformation();
            }
            itrNode = itrNode.next;
        
        // traverse till rear(last) node of queue
        }while(itrNode != this.rear);
        
        System.out.println("------------");
    }


    /*
    * Method convert queue to array of elements of processes 
    * Operation takes O(n) time to visit each node and store it
    * in process array
    * @return Process[] array of process elements
    */
    public Process[] queueToArray(){
        
        // Array of process element is created with size of queue size
        // if queue is empty, return null array
        int numberOfProcess = this.getSize();
        Process[] arrProc = new Process[numberOfProcess];
        if(0 == numberOfProcess)
            return arrProc;

        // starts from front of queue and store it in process array
        // untill we reach to rear node
        int procIndex = 0;
        Node itrNode = this.front;
        do{
            arrProc[procIndex++] = itrNode.proc;
            itrNode = itrNode.next;
        }while(itrNode != this.rear);

        // return array of process elements
        return arrProc;
    }


    /*
    * Method sorts queue by attribute according to class type of comparator
    * Operation takes O(nlogn) time to sort array of process
    * @return Process[] sorted array of process elements
    */
    public Process[] displaySortedQueue(ProcessComparator procComparator){
        
        // Store each elements of queue in array of process elements
        // Further operations on array will not disturb original queue
        Process arrProc[] = this.queueToArray();
        
        // If queue is empty, return empty array of process element
        if(0 == arrProc.length){
            return arrProc;
        }
        
        // call sort method from utility class
        // takes comparator to sort process by particular element
        ProcessUtility utilObj = new ProcessUtility();
        utilObj.sortProcesses(arrProc, procComparator);

        // display sorted processes
        System.out.println();
        for(Process proc:arrProc){
			proc.printInformation();
		}
        System.out.println();
        
        // return sorted array of process elements
        return arrProc;
    }
}
