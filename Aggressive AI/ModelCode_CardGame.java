public class ModelCode_CardGame {

    public static final int POCKETSIZE = 25;

    public static CardPool myCardPool; 
    public static Card[] myCards;
    public static int pocketSize;
      
    public static void sortCards()
    {
        //implementing a selection sort algorithm to sort cards from lowest to highest
        int lowestCard = 0; //will store smallest element

        for (int i = 0; i<pocketSize - 1; i++) { //iterate through myCards, length is pocketSize -1 because the last card does not need sorting
            lowestCard = i; //assume first card is the smallest
            for (int j = i+1; j<pocketSize; j++) { //searches the unsorted part of the array (from i+1 to end of array), j is current position
                if (myCards[j].rank<myCards[lowestCard].rank){ //if the rank of the current card is smaller than rank of lowest card
                    lowestCard = j; //lowestCard is updated to j
                }
            }

            //swaping the order of the cards

            //note to self: above, j serves as pointer that iterates through unsorted array to compare rank with current smallest
            //after inner loop is completed, j is no longer useful
            //swapping with i ensures smallest card is placed at the correct position

            //note to self: tempCard is a variable of tyoe card, holds a reference to object of Card class (like physically holding the card)
            //above, lowestCard is just pointing to a specific card but does not hold the card itself, therefore not a Card object

            Card tempCard = myCards[i]; //card at index i stored in temporary variable
            myCards[i] = myCards[lowestCard]; //card at i is replaced with smallest card
            myCards[lowestCard] = tempCard; //the card originally at i is now smallest card
        }

    
        // testing github connection
        // implement your favourite sorting algorithm to sort 
        // all the cards in their RANK in ASCENDING ORDER

        // must use pocketSize (the variable), NOT the POCKETSIZE (the constant) for sorting iteration bound
    }

    public static void main(String args[]) throws Exception
    {
        pocketSize = POCKETSIZE;

        myCardPool = new CardPool();        
        myCardPool.printPool();

        myCards = myCardPool.getRandomCards(pocketSize);    
        
        sortCards();

        System.out.println("My Pocket Cards are:");
        for(int j = 0; j < pocketSize; j++)
        {            
            myCards[j].printCard();
        }
        System.out.println();
    }

}
