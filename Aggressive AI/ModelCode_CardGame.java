
public class ModelCode_CardGame {

    public static final int POCKETSIZE = 25;

    public static CardPool myCardPool;
    public static HandsMaxHeap myMaxHeap;

    public static Card[] myCards, tempCards;
    public static int pocketSize = POCKETSIZE;

    // [Problem 2] Generate All Possible Valid Hands from the Pocket Cards and store them in myMaxHeap
    public static void generateHands(Card[] thisPocket)
    {
        //find the total number of combinations of 5 cards from thisPocket array, using n choose k method
        int allCombs = factorial(thisPocket.length) / (factorial(5) * factorial(thisPocket.length - 5));
        myMaxHeap = new HandsMaxHeap(allCombs); //initialize HandsMax Heap with the number of possible 5 card hands

        // If thisPocket has less than 5 cards, no hand can be generated, thus the heap will be empty
        if (thisPocket.length < 5){}

        // in other case, find all possible valid hands from thisPocket and store them in myMaxHeap
        //all combination of 5 cards are as follows:
        for (int i = 0; i < pocketSize - 4; i++){
            for (int j = i + 1; j < pocketSize - 3; j++){
                for (int k = j + 1; k < pocketSize - 2; k++){
                    for (int l = k + 1; l < pocketSize - 1; l++){
                        for (int m = l + 1; m < pocketSize; m++){

                            Hands hand = new Hands(thisPocket[i], thisPocket[j], thisPocket[k], thisPocket[l], thisPocket[m]);

                            if (hand.isAValidHand()) { //ensure it is valid
                                myMaxHeap.insert(hand);
                            }
                    }
                }

            }
        }
      
        // Pay attention that memory needs to be allocated for the heap!
    }
}

//helper function, used to calcualte total number of combinations (in above code)
    public static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Sorts the array of Cards in ascending order: ascending order of ranks; cards of equal ranks are sorted in ascending order of suits
    public static void sortCards(Card[] cards)
    {
        int j;
        Card temp;        
        int size = cards.length;
        
        for(int i = 1; i < size; i++) 
        { 
            temp = cards[i];		
            for(j = i; j > 0 && cards[j-1].isMyCardLarger(temp); j--) 
                cards[j] = cards[j-1]; 
            cards[j] = temp;
        }  
    }

    public static void main(String args[]) throws Exception
    {
        Hands myMove;        
        
        myCardPool = new CardPool();        
        myCardPool.printPool();

        myCards = new Card[pocketSize];
        myCards = myCardPool.getRandomCards(POCKETSIZE);  
        sortCards(myCards);

        // print cards
        System.out.println("My Pocket Cards:");
        for(int j = 0; j < pocketSize; j++)
        {            
            myCards[j].printCard();
        }
        System.out.println();
        
        System.out.println("heap:");
        generateHands(myCards); // generates all valid hands from myCards and stores them in myMaxHeap
        myMaxHeap.printHeap(); // prints the contents of the initial heap
        System.out.println();

        System.out.println("----------------start:----------------");

        // Print the contents of myMaxHeap
        
        // [Problem 3] Implementing Game Logic Part 1 - Aggresive AI: Always Picks the Strongest Hand
        
        // Step 1:
            // - Check if the Max Heap contains any valid hands 
            //   - if yes, remove the Largest Hand from the heap as the current Move
        for(int i = 0; pocketSize > 4; i++)
        {
            if (!myMaxHeap.isEmpty()) { //if there are items

                myMove = myMaxHeap.removeMax();  //p;ay the highest hand (aggresive)
            }

            //   - otherwise, you are out of valid hands.  Just pick any 5 cards from the pocket as a "Pass Move"
            else {
                myMove = new Hands(myCards[0], myCards[1], myCards[2], myCards[3], myCards[4]); 
            }

            // - Once a move is chosen, print the Hand for confirmation. MUST PRINT FOR VALIDATION!!
            System.out.println("\nhand: ");
            myMove.printMyHand(); //from HandsMaxHeap.java
            
            // Step 2:
            // - Remove the Cards used in the move from the pocket cards and update the Max Heap
            // - Print the remaining cards and the contents of the heap
            myCards = removeUsedCards(myCards, myMove, pocketSize); //helper function below
            pocketSize = myCards.length;

            // Regenerate the heap with the remaining cards
            generateHands(myCards);

            // Print the remaining cards
            System.out.println("\nRemaining Cards:");
            for (Card card : myCards) {
                card.printCard();
            }
            System.out.println();

            // Print the updated heap
            System.out.println("Updated Heap:");
            myMaxHeap.printHeap();
            System.out.println();
        }

        // End of game
        System.out.println("----------Game Over------------");

    }




    // helper function to remove a specific card from the pocket cards
    //create new array to store cards that are still in pocket (excluding used hand), iterate over current pocket, check if its used, otherwise copy
    public static Card[] removeUsedCards(Card[] pocket, Hands usedHand, int pocketSize) {
        Card[] newPocket = new Card[pocketSize - 5]; // because we are removing 5 cards
        int newIndex = 0;

        // Copy cards that are not in the used hand to the new pocket
        for (int i = 0; i < pocketSize; i++) { //each card in original array
            if (!usedHand.hasCard(pocket[i])) { //chekc if current card in pocket array is not part of the used hand
                newPocket[newIndex++] = pocket[i];
            }
        }

        return newPocket;
    }



}



