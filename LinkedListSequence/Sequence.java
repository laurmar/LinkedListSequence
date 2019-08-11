package proj3;  // Gradescope needs this.

/**

  *
  * @author Laura Marlin
  *  @version October 13, 2018
 *
 *   I affirm that I have carried out the attached academic endeavors with full academic honesty,
 *   in accordance with the Union College Honor Code and the course syllabus.
 *
Invariants "Data Structures" derived from pg. 150:
 *  *  "1. The number of elements in a sequence is stored in the instance variable manyItems
 *  *  2. For an empty sequence we do not care what is stored in any of data; for a nonempty sequence,
 *  *  the elements of the sequence are stored from the front to the end in contents at index 0 to contents at manyItems-1
 *  *  and we dont care what is stored in the rest of data
 *  *  3. if there is a current element then it lies in data at currentIndex; if there is no current element
 *  *  then currentIndex equals manyItems"
 *
 */

public class Sequence
{
    // INSTANCE VARIABLES AND CONSTANTS
    //-----------------------------------------------------------------------------------------------------


    private LinkedList contents;
    private int manyItems;
    private int currentIndex;
    private int capacity;

    private static final int DEFAULT_CAPACITY=10;







    // CONSTRUCTORS
    //-----------------------------------------------------------------------------------------------------

    /**
     * Creates a new sequence with initial capacity 10.
     */
    public Sequence() {
        manyItems=0;
        currentIndex=0;
        capacity= DEFAULT_CAPACITY;
        contents= new LinkedList();
    	
    
    }
    

    /**
     * Creates a new sequence.
     * 
     * @param initialCapacity the initial capacity of the sequence.
     */
    public Sequence(int initialCapacity){
        manyItems=0;
        currentIndex=0;
        capacity= initialCapacity;
        contents= new LinkedList();

    }




    //GETTERS AND SETTERS
    //-----------------------------------------------------------------------------------------------------

    /**
     * @return the capacity of the sequence.
     */
    public int getCapacity()
    {
        return capacity;
    }





    /**
     * @return the number of elements stored in the sequence.
     */
    public int size()
    {
        return manyItems;
    }



    /**
     * @return the element at the current location in the sequence, or
     * null if there is no current element.
     */
    public String getCurrent()
    {
        if(isCurrent()){
            int currentSpot= getCurrentIndex();
            String item= getItemInContents(currentSpot);
            return item;
        }
        return null;


    }


    //-------------------- private methods--------------------------

    /**
     * This obtains the data from the sequence
     * @return the linkedList where the data is held
     */
    private LinkedList getContents(){
        return contents;
    }

    /**
     * This switches out the data in the sequence for a new set
     * @param newContents a new linkedlist to replace the old data
     */
    private void setContents(LinkedList newContents){
        contents= newContents;
    }

    /**
     * sets the index of current to a new value
     * @param newIndex new value for the index of current
     */
    private void setCurrentIndex(int newIndex){
        currentIndex= newIndex;
    }


    /**
     * Returns the index where current is currently at
     * @return index of current
     */
    private int getCurrentIndex(){
        return currentIndex;
    }


    /**
     * Changes the instance variable manyItems to a new value
     * @param newCount the new value of manyItems
     */
    private void setManyItems(int newCount){
        if(newCount >= 0) {
            manyItems = newCount;
        }
    }


    /**
     * Changes the value of an item at a specific location in data
     * @param index: location in the data array to set
     * @param item: value to set the item in data too
     */
    private void addItemInContents(int index, String item){
        contents.insertAtSpot(item, index);
    }


    /**
     * Obtains the value of an item in contents at a specific location
     * @param index location in data to obtain from
     * @return the item contained within data at the specified index
     */
    private String getItemInContents(int index){
        String item= contents.getDatafromLocation(index);
        return item;
    }

    /**
     * This function sets the capacity of the sequence to a new value
     * @param newCapacity new value for capacity
     */
    private void setCapacity(int newCapacity){
        if(isValidCapacity(newCapacity)) {
            capacity = newCapacity;
        }

    }






    //ADDING TO THE SEQUENCE
    //-----------------------------------------------------------------------------------------------------


    /**
     * Adds a string to the sequence in the location before the
     * current element. If the sequence has no current element, the
     * string is added to the beginning of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addBefore(String value)
    {
        int currentNumItems= size();

        if(isFull()){

            ensureCapacity(currentNumItems*2+1);
        }

        if(!isCurrent()){
            int beginning=0;
            setCurrentIndex(beginning);

        }
        int currentSpot= getCurrentIndex();
        addItemInContents(currentSpot, value);
        setManyItems(currentNumItems+1);
    }









    
    /**
     * Adds a string to the sequence in the location after the current
     * element. If the sequence has no current element, the string is
     * added to the end of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addAfter(String value)
    {
        int currentNumItems= size();

        if(isFull()){
            ensureCapacity(currentNumItems*2+1);
        }


        if(!isCurrent() || isAtEnd()){
            addItemInContents(currentNumItems, value);
            makeInvalidCurrent();

        }

        else {
            advance();
            int newCurrentIndex= getCurrentIndex();
            addItemInContents(newCurrentIndex, value);

        }
        setManyItems(currentNumItems+1);
    }




    /**
     * Places the contents of another sequence at the end of this sequence.
     *
     * If adding all elements of the other sequence would exceed the
     * capacity of this sequence, the capacity is changed to make (just enough) room for
     * all of the elements to be added.
     *
     * Postcondition: NO SIDE EFFECTS!  the other sequence should be left
     * unchanged.  The current element of both sequences should remain
     * where they are. (When this method ends, the current element
     * should refer to the same element that it did at the time this method
     * started.)
     *
     * @param another the sequence whose contents should be added.
     */
    public void addAll(Sequence another)
    {

        Sequence anotherCopy= another.clone();
        int currentNumItems= size();
        int otherNumItems= anotherCopy.size();

        int currentSpace= getCapacity();
        int totalNeededSpace= currentNumItems+otherNumItems;

        if(totalNeededSpace> currentSpace){
            ensureCapacity(totalNeededSpace);
        }

        // here we are evaluating to see if the original list has a current, and if it doesnt we put the current to the
        // very end of the total space the combined sequence would take up to ensure it stays invalid
        if(!isCurrent()){
            setCurrentIndex(totalNeededSpace);
        }

        int originalCurrentSpot= getCurrentIndex();

        advanceToEnd();
        for(int i=0; i<otherNumItems; i++){
            String otherItem= anotherCopy.getItemInContents(i);
            addAfter(otherItem);
        }

        // ensuring that the current stays in its original location
        setCurrentIndex(originalCurrentSpot);



    }





    // TRUE OR FALSE
    //-----------------------------------------------------------------------------------------------------



    /**
     * @return true if and only if the sequence has a current element.
     */
    public boolean isCurrent()
    {
        return isValidIndex() && isSpotFilled();

    }




    /**
     * Checks whether another sequence is equal to this one.  To be
     * considered equal, the other sequence must have the same size
     * as this sequence, have the same elements, in the same
     * order, and with the same element marked
     * current.  The capacity can differ.
     *
     * Postcondition: NO SIDE EFFECTS!  this sequence and the
     * other sequence should remain unchanged, including the
     * current element.
     *
     * @param other the other Sequence with which to compare
     * @return true iff the other sequence is equal to this one.
     */
    public boolean equals(Sequence other)
    {
        int currentSize= this.size();
        int otherSize = other.size();
        int currentSpot= this.getCurrentIndex();
        int otherSpot= other.getCurrentIndex();

        if(currentSize== otherSize && currentSpot==otherSpot) {
            int index = 0;
            while (index < currentSize && this.getItemInContents(index).equals(other.getItemInContents(index))) {
                index++;
            }

            // Did the computer make it to the end of the sequence without coming across any differences?
            return index==currentSize;
        }

        else{
            return false;
        }
    }





    /**
     *
     * @return true if Sequence empty, else false
     */
    public boolean isEmpty()
    {
        int currentSize= size();
        return currentSize<=0;
    }



    //------------------------ private methods---------------------------------------

    /**
     * This ensures that the inputted capacity is valid
     * @param attemptedCapacity: value for the capacity to change too
     */
    private boolean isValidCapacity(int attemptedCapacity){
        return attemptedCapacity>=0;

    }

    /**
     * This checks to see if the array is full or not by comparing the quantity of items in the list
     * to the total size of the array
     */
    private boolean isFull(){
        int totalItems= size();
        int totalSpace= getCapacity();
        return totalItems==totalSpace;


    }




    /**
     *This checks to see if the item in the array at current has a string in it
     */
    private boolean isSpotFilled(){
        int attemptedIndex= getCurrentIndex();
        LinkedList currentData= getContents();
        String item= currentData.getDatafromLocation(attemptedIndex);
        return item !=null;



    }


    /**
     *This checks to see if current is at the end of the sequence by checking its location against
     * the quantity of items in the array
     */
    private boolean isAtEnd(){
        int current= getCurrentIndex();
        int lastItem= size();
        int lastSpot= lastItem-1;
        return current== lastSpot;




    }

    /**
     *This checks to see if the index is within the active section of the array
     */
    private boolean isValidIndex(){
        int nowIndex= getCurrentIndex();
        int getUsedSize= size();
        return 0<=nowIndex && nowIndex< getUsedSize;


    }






    // REMOVING FROM THE SEQUENCE
    //-----------------------------------------------------------------------------------------------------

    /**
     * Remove the current element from this sequence.  The following
     * element, if there was one, becomes the current element.  If
     * there was no following element (current was at the end of the
     * sequence), the sequence now has no current element.
     *
     * If there is no current element, does nothing.
     */
    public void removeCurrent()
    {
        if(isCurrent()) {
            int target= getCurrentIndex();
            int numOfItems= size();

            if (isAtEnd()) {
                makeInvalidCurrent();
            }

            else {
                removeFromData(target);
            }
            setManyItems(numOfItems-1);
        }
    }


    /**
     *  empty the sequence.  There should be no current element.
     */
    public void clear()
    {
        setManyItems(0);
        makeInvalidCurrent();
    }

    //------------------- private methods------------------------

    /**
     * This removes from the data source a specific node
     * @param index the index of the node to be removed
     */
    private void removeFromData(int index){
        contents.removeFromSpot(index);

    }




// FUNCTIONS CHANGING LENGTH OF ARRAY
    //-----------------------------------------------------------------------------------------------------

    /**
     * Reduce the current capacity to its actual size, so that it has
     * capacity to store only the elements currently stored.
     */
    public void trimToSize()
    {
        int currentNumItems= size();
        setCapacity(currentNumItems);

    }








    /**
     * Increase the sequence's capacity to be
     * at least minCapacity.  Does nothing
     * if current capacity is already >= minCapacity.
     *
     * @param minCapacity the minimum capacity that the sequence
     * should now have.
     */
    public void ensureCapacity(int minCapacity)
    {
        int currentCapacity= getCapacity();
        if(currentCapacity< minCapacity){
            setCapacity(minCapacity);
        }
    }










// FUNCTIONS WITH MOVEMENT
    //-----------------------------------------------------------------------------------------------------

    /**
     * Sets the current element to the start of the sequence.  If the
     * sequence is empty, the sequence has no current element.
     */
    public void start()
    {
        if(isEmpty()){
            makeInvalidCurrent();
        }
        else{
            setCurrentIndex(0);
        }
    }









    /**
     * Move forward in the sequence so that the current element is now
     * the next element in the sequence.
     *
     * If the current element was already the end of the sequence,
     * then advancing causes there to be no current element.
     *
     * If there is no current element to begin with, do nothing.
     */
    public void advance()
    {
            int currentSpot= getCurrentIndex();
            setCurrentIndex(currentSpot+1);
    }

    //---------------- private functions---------------------

    /**
     * This move current to be at the end of the used section of the array.
     */
    private void advanceToEnd(){
        int end= size()-1;
        while(getCurrentIndex()< end){
            advance();
        }
    }















// MICILLANEOUS FUNCTIONS
    //-----------------------------------------------------------------------------------------------------


    
    /**
     * Make a copy of this sequence.  Subsequence changes to the copy
     * do not affect the current sequence, and vice versa.
     * 
     * Postcondition: NO SIDE EFFECTS!  This sequence's current
     * element should remain unchanged.  The clone's current
     * element will correspond to the same place as in the original.
     *
     * @return the copy of this sequence.
     */
    public Sequence clone()
    {
        // getting the original sequences information
        int originalCapacity= getCapacity();
        int originalCurrentSpot= getCurrentIndex();
        int originalnumItems= size();


        // making the copy
        Sequence copy= new Sequence(originalCapacity);
        LinkedList newContents= contents.clone();
        copy.setContents(newContents);

        //ensuring the copy has the same information as the original
        copy.setCurrentIndex(originalCurrentSpot);
        copy.setManyItems(originalnumItems);
        copy.setCapacity(originalCapacity);

        // ensuring that the original has no changes to its information
        setCurrentIndex(originalCurrentSpot);
        setManyItems(originalnumItems);
        setCapacity(originalCapacity);

        return copy;
    }











    
    /**
     * Produce a string representation of this sequence.  The current
     * location is indicated by a >.  For example, a sequence with "A"
     * followed by "B", where "B" is the current element, and the
     * capacity is 5, would print as:
     * 
     *    {A, >B} (capacity = 5)
     * 
     * The string you create should be formatted like the above example,
     * with a comma following each element, no comma following the
     * last element, and all on a single line.  An empty sequence
     * should give back "{}" followed by its capacity.
     * 
     * @return a string representation of this sequence.
     */
    public String toString() 
    {
        int currentSpot= getCurrentIndex();
        int currentCapacity= getCapacity();
        int totalItemCount= size();

        String beginning= "{";
        String ending= "} (capacity = "+currentCapacity+")";

        String middle="";


        for(int i=0; i<totalItemCount; i++){
            String currentItem= getItemInContents(i);
            // is it the current item
            if(i== currentSpot){
                middle=middle+">";
            }

            // adding in the string
            middle= middle+currentItem;

            // middle item formatting
            if(i != totalItemCount-1){
                middle= middle+ ", ";
            }

        }

        return beginning+middle+ending;
    }

    //-----------------------------------------PRIVATE METHODS----------------

    /**
     * This makes the instance variable, current,  invalid
     */
    private void makeInvalidCurrent(){
        int endOfList= size();
        setCurrentIndex(endOfList);
    }




    public static void main(String[] args) {
        Sequence ne= new Sequence();
        ne.addBefore("a");
        ne.addBefore("b");
        ne.addBefore("c");
        System.out.println(ne.toString());
        System.out.println(ne.contents);

    }




}