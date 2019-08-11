package proj3;  // Gradescope needs this.


/**
 * This is a class that creates a Linked List, a data structure that relies on links between
 * nodes of data rather than indexing. Users can search, add and remove nodes using this class.
 *  @author Laura Marlin
 *  @version October 13, 2018
 *
 *   I affirm that I have carried out the attached academic endeavors with full academic honesty,
 *   in accordance with the Union College Honor Code and the course syllabus.
 */
public class LinkedList
{
    private int length;
    private ListNode firstNode;

    /**
     * This is a constructor for the Linked List Class
     */
    public LinkedList()
    {
        length=0;
        firstNode=null;
    }





    // GETTERS AND SETTERS
    //------------------------------------------------------------------------------------------------------

    /**
     *
     * @return the length of the Linked List
     */
    public int getLength()
    {
        return length;
    }


    /**
     * This sets the length instance variable to a new value
     * @param newLength the new length of the Linked List
     */
    private void setLength(int newLength){
        if(newLength>=0){
            length= newLength;
        }

    }



    /**
     *
     * @return The link to the firstNode of the Linked List
     */
    private ListNode getFirstNode(){
        return firstNode;

    }



    /**
     * This replaces the Node located in FirstNode
     * @param newFirst ListNode to replace the current firstNode
     */
    private void setFirstNode(ListNode newFirst){
        firstNode= newFirst;
    }


    /**
     * This method moves through the linkedlist until it reaches a desired numbered position.
     * If the position is not in the list then null is returned.
     *
     * Note: the positions begin at 1, as this is not an array.
     * @param position This is the numbered location that the user desired to gain access to
     * @return The listnode at the numbered position provided
     */
    public ListNode getItemAtLocation(int position){
        if(isValidLocation(position)){
            ListNode runner= getFirstNode();
            int counter=0;
            while(runner!=null && counter!=position){
                runner=runner.next;
                counter++;
            }
            return runner;
        }
        else{
            return null;
        }

    }


    /**
     * This searches a LinkedList for a desired value. If the value is found, the listnode
     * containing that value is returned. If it doesn't exist null is returned.
     * @param desiredData value that is to be searched for within the LinkedList
     * @return Listnode that contains the desired data
     */
    public ListNode getLocationOfItem(String desiredData){
        int size= getLength();
        if(size>0){
            ListNode runner= getFirstNode();
            while(runner!=null && !runner.data.equals(desiredData)){
                runner=runner.next;
            }
            return runner;
        }
        return null;

    }


    /**
     * This retrieves the data within a node in the LinkedList from a given position
     * @param position number location of the node desired
     * @return The string value that is held at that position
     */
    public String getDatafromLocation(int position){
        if(isValidLocation(position)) {
            ListNode node = getItemAtLocation(position);
            String currentData = node.data;
            return currentData;
        }
        return null;

    }




    //BOOLEAN TESTS
    //------------------------------------------------------------------------------------------------------

    /**
     * This test ensures that the inputted value is between the start (1) and end (length of the list +1) of the
     * Linked list.
     *
     * Note: getLength()+1 is used as then the user can add to the end of the list.
     * @param spot location to be tested
     * @return true if the value is within the length of the list
     */
    private boolean isValidLocation(int spot){
        int endOfList= getLength();
        return spot>= 0 && spot<=endOfList;

    }

    /**
     * This checks to see if the inputted value is the same value as the location of the last item in the list
     * @param spot location to be tested
     * @return true if the spot given matches the length of the list, and false otherwise
     */
    private boolean isAtEnd(int spot){
        int lastSpot= getLength()-1;
        return spot==lastSpot;
    }










    // ADDING
    //------------------------------------------------------------------------------------------------------

    /**
     * This adds a new node to the front of the linked list
     * @param data: value to be added in the node
     */
    public void insertAtHead(String data) {
        ListNode newnode = new ListNode(data);
        int size= getLength();
        if (size == 0)
        {
            setFirstNode(newnode);
        }
        else
        {
            newnode.next=firstNode;
            setFirstNode(newnode);
        }
        setLength(size+1);
    }


    /**
     * This adds a new node to the end of the linked list
     * @param data: value to be added in the node
     */
    public void insertAtEnd(String data){
        int size= getLength();
        insertAtSpot(data, size);
    }








    /**
     * This adds a newNode to the list at a desired numerical location.
     *
     * Note: When the inputted numerical location does not occur in the list then nothing occurs
     * @param data: value to be added as a listnode
     * @param position number location that the node should be once it is added
     */
    public void insertAtSpot(String data, int position){
        if(isValidLocation(position)){
            int size= getLength();
            if(position==0 || size==0){
                insertAtHead(data);
            }
            else{
                ListNode priorNode= getItemAtLocation(position-1);
                ListNode newNode= new ListNode(data);
                newNode.next= priorNode.next;
                priorNode.next= newNode;

                setLength(size+1);
            }


        }
    }


    /**
     *This adds the nodes from a different linkedList to the new LinkedList
     *
     * Note: The new nodes are added to the front of the linked list due to efficency.
     * @param other This is a seperate LinkedList that is to be added to the original linkedList
     */
    public void combineLists(LinkedList other){
        int numItemsToAdd= other.getLength()-1;
        LinkedList safeCopy= other.clone();
        String currentData;
        for(int i=numItemsToAdd; i>=0; i--){
            currentData= safeCopy.getDatafromLocation(i);
            insertAtHead(currentData);
        }


    }







    //REMOVING
    //------------------------------------------------------------------------------------------------------

    /**
     * This removes the first node of the Linked List
     */
    public void removeFromHead(){
        int size= getLength();
        if(size>0) {
            setFirstNode(firstNode.next);
            setLength(size-1);

        }
    }


    /**
     * This removes the last node in the list
     */
    public void removeFromEnd(){
        int size= getLength();
        if(size>0) {
            if(size==1){
                removeFromHead();
            }
            else {
                int lastSpot = getLength()-1;
                ListNode newLast = getItemAtLocation(lastSpot - 1);
                newLast.next = null;

            }
            setLength(size - 1);
        }


    }


    /**
     * This removes a node from a specified position in the list by numberical value
     * @param position this is the numerical value of a desired node
     */
    public void removeFromSpot(int position){

        if(isValidLocation(position)) {
            if(position==0){
                removeFromHead();
            }
            else if(isAtEnd(position)){
                removeFromEnd();
            }
            else{
                int size= getLength();
                ListNode priorNode = getItemAtLocation(position - 1);
                priorNode.next = priorNode.next.next;
                setLength(size-1);
            }

        }

    }


    /**
     * This clears the linked list, resetting its length and its contents
     */
    public void clear(){
        setFirstNode(null);
        setLength(0);
    }





    //MICCELANEOUS
    //------------------------------------------------------------------------------------------------------


    /**
     * This creates a clone of the current linked list
     * @return replica of the current linkedlist
     */
    public LinkedList clone(){
        LinkedList copy= new LinkedList();
        int totalItems= getLength()-1;
        for(int i=totalItems; i>=0; i--){
            String currentData= getDatafromLocation(i);
            copy.insertAtHead(currentData);
        }
        return copy;

    }


    /**
     * this creates a printable version of the linkedlist
     * @return String version of linked list
     */
    public String toString(){
		String toReturn = "(";
		ListNode runner = firstNode;
		while(runner != null){
			toReturn = toReturn + runner;
			runner = runner.next;
			if(runner != null){
				toReturn = toReturn + ", ";
			}
		}
		toReturn = toReturn + ")";
		return toReturn;
	}

}






