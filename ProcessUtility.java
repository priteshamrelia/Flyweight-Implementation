
/**
* <h1>ProcessUtility.java</h1>
* ProcessUtility class implements a class that provides sorting utility
* for process objects.
* Implements comparator for each process attribute and also support
* ascending and descneding sorting
* Here, merge sort algorithm is used as sorting algorithm which is stable
* in nature and takes O(nLogn) time to sort data.
* 
* @author  Pritesh Amrelia
* @version 1.0
* @since   09-09-2019
*/

class ProcessUtility{
    
    /*
    * ProcessComparator object holds object of any of its children classes
    * accordingly process objects are sorted
    */
    ProcessComparator procComparator;
    
    /*
    * Default constructor
    */
    ProcessUtility(){}
    
    
    /*
    * This method sorts array of process objects as demanded using comparator 
    * @param Process[] Array of process objects to be sorted
    * @param int startIndex is initially first index of array which will be 
    * changed later when recursive call occur.
    * @param int endIndex is initially last index of an array which will be
    * changed later when recursive call occur.
    */
    private void mergeSortProcesses(Process[] arrProc, int startIndex, int endIndex){
        
        /*
        * startIndex becomes equals or greater than endIndex means array is empty
        * No need to do anything, exit condition for recursion
        */
        if(startIndex >= endIndex)
            return;
        
        /*
        * finding middle index to split array in two halves
        */
        int midIndex = startIndex + (endIndex - startIndex)/2;
        
        /*
        * Sort both half arrays recursively
        */
        mergeSortProcesses(arrProc, startIndex, midIndex);
        mergeSortProcesses(arrProc, midIndex+1, endIndex);
        
        /*
        * Merge two sorted halves, overwriting to original one
        */
        this.mergeArray(arrProc, startIndex, midIndex, endIndex);
    }
    
    
    /*
    * This method merges two sorted halves of array of process objects 
    * @param Process[] Array of process objects to be sorted
    * @param int startIndex is index from where first half starts
    * @param int midIndex is index from where first half ends and
    * from next index second half starts
    * @param int endIndex is index from where second half ends
    * array is merged in same array which is passed in argument
    */
    private void mergeArray(Process[] arrProc, int startIndex, int midIndex, int endIndex){
        
        /*
        * Left array is extracted from arrProc using startIndex and midIndex
        * and stored in seperate array
        */
        int leftArrSize = midIndex - startIndex + 1;
        Process[] leftProcArray = new Process[leftArrSize];
        for(int index=0 ; index<leftArrSize; index++){
            leftProcArray[index] = arrProc[startIndex+index];
        }
        
        /*
        * Right array is extracted from arrProc using midIndex and endIndex
        * and stored in seperate array
        */
        int rightArrSize = endIndex - midIndex;
        Process[] rightProcArray = new Process[rightArrSize];
        for(int index=0 ; index<rightArrSize; index++){
            rightProcArray[index] = arrProc[midIndex+1+index];
        }
        
        
        /*
        * First element of both left array and right array is compared
        * whoever is smaller/larger is replaced in original array
        * This continues untill left or right array becomes empty
        */
        int leftProcIndex = 0;
        int rightProcIndex = 0;
        int mergeProcIndex = startIndex;
        
        while(leftProcIndex < leftArrSize && rightProcIndex < rightArrSize){
            if(procComparator.compare(leftProcArray[leftProcIndex],rightProcArray[rightProcIndex]) < 0){
                arrProc[mergeProcIndex++] = leftProcArray[leftProcIndex++];
            }
            else{
                arrProc[mergeProcIndex++] = rightProcArray[rightProcIndex++];
            }
        }
        
        
        /*
        * Replace all left array's elements in original array if right array
        * becomes empty
        */
        while(leftProcIndex < leftArrSize){
            arrProc[mergeProcIndex++] = leftProcArray[leftProcIndex++];
        }
        
        /*
        * Replace all right array's elements in original array if left array
        * becomes empty
        */
        while(rightProcIndex < rightArrSize){
            arrProc[mergeProcIndex++] = rightProcArray[rightProcIndex++];
        }
        
    }
    
    
    /*
    * This method provides entry point to being with sorting of process objects
    * @param Process[] array of process objects
    * @param ProcessComparator comparator object passed by user suggests on which 
    * attribute sorting needs to be applied
    * Process array is merged in same array which is passed in argument
    */
    public void sortProcesses(Process[] arrProc, ProcessComparator procComparator){
        this.procComparator = procComparator;
        this.mergeSortProcesses(arrProc, 0, arrProc.length-1);
    }
}
