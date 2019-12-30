
import java.util.Comparator;

/**
* <h1>Process.java</h1>
* Process class implements a class that holds process attributes
* like process name, process PID, CPU Usage by process in percentage, 
* total CPU time taken by process, number of threads used by process, 
* and owner of process.
*
* @author  Pritesh Amrelia
* @version 1.0
* @since   09-09-2019
*/
class Process{
    String name;
    int pid;
    double percntCpuUsage;
    int totalCpuTime;
    int numberOfThreads;
    String owner;
    
    /**
   * Process Constructor to create an object of process with below attributes.
   * @param name                String  Name of Process
   * @param pid                 int     PID of Process
   * @param percntCpuUsage      double  CPU time taken by Process in Percentage
   * @param totalCpuTime        int     Total CPU time taken by Process
   * @param numberOfThreads     int     Number of threads being used by Process
   * @param owner               String  Owner of Process
   */
    Process(String name, int pid, double percntCpuUsage, int totalCpuTime, 
                int numberOfThreads, String owner){
        this.name = name;
        this.pid = pid;
        this.percntCpuUsage = percntCpuUsage;
        this.totalCpuTime = totalCpuTime;
        this.numberOfThreads = numberOfThreads;
        this.owner = owner;
    }
    
    /**
   * This method is used to get process name.
   * @return String name of process hold by current object.
   */
    public String getName(){
        return this.name;
    }
    
    /**
   * This method is used to get PID of process.
   * @return int PID of process hold by current object.
   */
    public int getPid(){
        return this.pid;
    }
    
    /**
   * This method is used to get CPU usage by process in percentage.
   * @return double CPU usage by process in percentage.
   */
    public double getPercntCpuUsage(){
        return this.percntCpuUsage;
    }
    
    /**
   * This method is used to get total time taken by process.
   * @return int Total CPU time taken by process.
   */
    public int getTotalCpuTime(){
        return this.totalCpuTime;
    }
    
    /**
   * This method is used to get number of threads being used by process.
   * @return int Total number of threads used by process.
   */
    public int getNumberOfThreads(){
        return this.numberOfThreads;
    }
    
    /**
   * This method is used to get process owner.
   * @return int Owner of Process hold by current process object.
   */
    public String getOwner(){
        return this.owner;
    }
    
    /**
   * This method is used to display process information on standard output.
   */
    public void printInformation(){
        System.out.println(name + "\t" + pid + "\t" + percntCpuUsage + "\t" + 
            totalCpuTime + "\t" + numberOfThreads + "\t" + owner);
    }
}



/**
* ProcessComparator class is an abstract class to compare process objects
* in terms of attributes defined by class Process.
*/
abstract class ProcessComparator{
    
    /**
     * Boolean attribute to suggest order of comparison
     * true value suggests comparison in ascending order
    */ 
    boolean isCompareOrderAscend;
    
    /**
     * Initialization of comparator object
     * By default, comparison performed in ascending order
    */
    ProcessComparator(){
        this.isCompareOrderAscend = true;
    }

    /**
     * abstract method is implemented by child classes to compare by different
     * attributes
    */
    abstract int compare(Process proc1, Process proc2);
}

/**
* NameComparator class inherits ProcessComparator and implement comparison
* mechanism based on Process Name.
*/
class NameComparator extends ProcessComparator implements Comparator<Process>{

    /**
     * default constructor
    */ 
    public NameComparator(){}
    
    /**
     * Constructor to override default one
     * @param boolean parameter to set whether compare order of name is 
     * ascending or descending
    */
    public NameComparator(boolean isCompareOrderAscend){
        this.isCompareOrderAscend = isCompareOrderAscend;
    }
    
    /**
     * Overridden compare method
     * @param1 Process object
     * @param2 Process object
     * @return int  0 if both process have same name, 1 if first process has 
     * lexicographically larger name, and -1 if second process has 
     * lexicographically larger name
    */ 
    public int compare(Process proc1, Process proc2){
        if(this.isCompareOrderAscend){
            return proc1.getName().compareTo(proc2.getName());
        }
        else{
            return proc2.getName().compareTo(proc1.getName());
        }
    }
}

/**
* PidComparator class inherits ProcessComparator and implement comparison
* mechanism based on PID of Process.
*/
class PidComparator extends ProcessComparator implements Comparator<Process>{
    
    /**
     * default constructor
    */ 
    public PidComparator(){}
    
    /**
     * Constructor to override default one
     * @param boolean parameter to set whether compare order of PID is 
     * ascending or descending
    */
    public PidComparator(boolean isCompareOrderAscend){
        this.isCompareOrderAscend = isCompareOrderAscend;
    }
    
    /**
     * Overridden compare method
     * @param1 Process object
     * @param2 Process object
     * @return int  0 if both have same PID, 1 if PID of first process is 
     * greater, and -1 if PID of second process is greater
    */ 
    public int compare(Process proc1, Process proc2){
        int compareFactor;
        
        if(proc1.getPid() < proc2.getPid()){
            compareFactor = -1;
        }
        else if(proc1.getPid() > proc2.getPid()){
            compareFactor = 1;
        }
        else{
            compareFactor = 0;
        }
        
        return this.isCompareOrderAscend ? compareFactor : -1*compareFactor;
    }
}

/**
* PercntCpuUsageComparator class inherits ProcessComparator and implement 
* comparison mechanism based on cpu time taken by Process in percentage.
*/
class PercntCpuUsageComparator extends ProcessComparator implements Comparator<Process>{
    
    /**
     * default constructor
    */ 
    public PercntCpuUsageComparator(){}
    
    /**
     * Constructor to override default one
     * @param boolean parameter to set whether compare order of percentage
     * cpu time taken by process is ascending or descending
    */
    public PercntCpuUsageComparator(boolean isCompareOrderAscend){
        this.isCompareOrderAscend = isCompareOrderAscend;
    }
    
    /**
     * Overridden compare method
     * @param1 Process object
     * @param2 Process object
     * @return int  0 if both have same percentage usage, 1 if percentage usage
     * of first process is greater, and -1 if percentage usage of second process
     * is greater
    */ 
    public int compare(Process proc1, Process proc2){
        int compareFactor;
        
        if(proc1.getPercntCpuUsage() < proc2.getPercntCpuUsage()){
            compareFactor = -1;
        }
        else if(proc1.getPercntCpuUsage() > proc2.getPercntCpuUsage()){
            compareFactor = 1;
        }
        else{
            compareFactor = 0;
        }
        
        return this.isCompareOrderAscend ? compareFactor : -1*compareFactor;
    }
}

/**
* TotalCpuTimeComparator class inherits ProcessComparator and implement 
* comparison mechanism based on total cpu time taken by Process.
*/
class TotalCpuTimeComparator extends ProcessComparator implements Comparator<Process>{
    
    /**
     * default constructor
    */ 
    public TotalCpuTimeComparator(){}
    
    /**
     * Constructor to override default one
     * @param boolean parameter to set whether compare order of cpu time is 
     * ascending or descending
    */
    public TotalCpuTimeComparator(boolean isCompareOrderAscend){
        this.isCompareOrderAscend = isCompareOrderAscend;
    }
    
    /**
     * Overridden compare method
     * @param1 Process object
     * @param2 Process object
     * @return int  0 if both have same total cpu time, 1 if total cpu time of
     * first process is greater, and -1 if total cpu time of second process is 
     * greater
    */ 
    public int compare(Process proc1, Process proc2){
        int compareFactor;
        
        if(proc1.getTotalCpuTime() < proc2.getTotalCpuTime()){
            compareFactor = -1;
        }
        else if(proc1.getTotalCpuTime() > proc2.getTotalCpuTime()){
            compareFactor = 1;
        }
        else{
            compareFactor = 0;
        }
        
        return this.isCompareOrderAscend ? compareFactor : -1*compareFactor;
    }
}

/**
* NameComparator class inherits ProcessComparator to implement comparison
* mechanism based on Process Name.
*/
class NumberOfThreadsComparator extends ProcessComparator implements Comparator<Process>{
    
    /**
     * default constructor
    */ 
    public NumberOfThreadsComparator(){}
    
    /**
     * Constructor to override default one
     * @param boolean parameter to set whether compare order of thread number
     * is ascending or descending
    */
    public NumberOfThreadsComparator(boolean isCompareOrderAscend){
        this.isCompareOrderAscend = isCompareOrderAscend;
    }
    
    /**
     * Overridden compare method
     * @param1 Process object
     * @param2 Process object
     * @return int  0 if both have same number of threads, 1 if number of 
     * threads of first process is greater, and -1 if number of threads of 
     * second process is greater
    */ 
    public int compare(Process proc1, Process proc2){
        int compareFactor;
        
        if(proc1.getNumberOfThreads() < proc2.getNumberOfThreads()){
            compareFactor = -1;
        }
        else if(proc1.getNumberOfThreads() > proc2.getNumberOfThreads()){
            compareFactor = 1;
        }
        else{
            compareFactor = 0;
        }
        
        return this.isCompareOrderAscend ? compareFactor : -1*compareFactor;
    }
}

/**
* NameComparator class inherits ProcessComparator to implement comparison
* mechanism based on Process Name.
*/
class OwnerComparator extends ProcessComparator implements Comparator<Process>{
    
    /**
     * default constructor
    */ 
    public OwnerComparator(){}
    
    /**
     * Constructor to override default one
     * @param boolean parameter to set whether compare order of owner is 
     * ascending or descending
    */
    public OwnerComparator(boolean isCompareOrderAscend){
        this.isCompareOrderAscend = isCompareOrderAscend;
    }
    
    /**
     * Overridden compare method
     * @param1 Process object
     * @param2 Process object
     * @return int  0 if both process have same owner name, 1 if first process 
     * has lexicographically larger owner name, and -1 if second process has 
     * lexicographically larger owner name
    */ 
    public int compare(Process proc1, Process proc2){
        if(this.isCompareOrderAscend){
            return proc1.getOwner().compareTo(proc2.getOwner());
        }
        else{
            return proc2.getOwner().compareTo(proc1.getOwner());
        }
    }
}
