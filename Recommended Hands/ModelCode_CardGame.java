import java.util.*;

public class ModelCode_CardGame {

    public static final int POCKETSIZE = 25;
    public static Scanner myInputScanner;          
    
    public static void main(String args[]) throws Exception
    {        
        CardPool myCardPool;
        Card[] aiCards, myCards;        
        Hands aiHand, myHand;
        
        HandsMaxHeap aiBST; // replace this with your own HandsMaxHeap for improved performance.
        HandsRBT myRBT;
                
        int aiPocketSize = POCKETSIZE, myPocketSize = POCKETSIZE;
        int aiScore = 0, playerScore = 0; 
        
        myCardPool = new CardPool(); 
        
        

        // Lab 2 - Turn-base AI (Aggresive) vs Player

        // General Rules
        // AI and Player get 25 cards each, 5 turns.  Player and AI Score init to zero.
        // In each turn
        //      Print AI Cards, then Numbered My Cards
        //      Player makes a choice - proceed when valid
        //      AI makes a move, compare Player's hand vs. AI's hand
        //      Update score
        //      Players and AI cannot make INVALID moves until they are out of valid hands
        // At the end of the game, report winner with score


        // You can upgrade your Lab 1 algorithm to Lab 2 to complete this game
        // You can also redesign the entire game loop logic

        // Step 1 - Initialization
        //  - Given the CardPool instance, get 25 cards (POCKETSIZE) for both AI and Player.
        myCards = myCardPool.getRandomCards(POCKETSIZE);
        aiCards = myCardPool.getRandomCards(POCKETSIZE);

        //  - Sort their cards using sortCards() from Lab 0/1. Assign a serial number to Player's cards
        sortCards(myCards);
        sortCards(aiCards);

        //  - Instantiate a HandsRBT for the player and invoke generateHandsIntoBST() 
        //    to populate the player RBT with all possible hands from the pocket card.
        myRBT = new HandsRBT();
        generateHandsIntoRBT(myCards, myRBT);

        //  - If you have successfully completed Lab 1, you can replace HandsBST with your own HandsMaxHeap
        //    to improve the program performance.

        // Step 2 - Game Loop Logic
        //  - Given POCKETSIZE = 25, and 5-card hand is consumed each round, our game loop should only 
        //    repeat 5 times.  You can optionally parameterize the iteration count for scalability.

        for (int turn = 0; turn < 5; turn ++){
            // Step 2-1 : Print Both AI and Player Pocket Cards for Strategy Analysis
            //            - When printing the Player pocket cards, you **MUST** print with serial number.
                System.out.println("Current Turn" + (turn+1));
                System.out.println("AI pocket cards:");
                
                for (int i = 0; i < aiPocketSize; i++){
                    aiCards[i].printCard();
                }

                System.out.println();

                System.out.println("Player pocket cards:");
                for (int i = 0; i < myPocketSize; i++){
                    System.out.print("[" + (i + 1) + "] ");  // Print index first
                    myCards[i].printCard();  // Then print the card details
                }

                //            - Also check if RBT is empty.  If yes, notify player that he/she is out of moves.
                if (myRBT.isEmpty()){
                    System.out.println("There are no more valid moves");
                    break;
                }
                
            // Step 2-2 : Use the provided getUserHand() method to allow player to pick the 5-card hand from
            //            the pocket cards.
                myHand = getUserHand(myCards, myPocketSize);

            //              - After the hand is chosen, check if this hand can be found in the RBT
            //              -  If this hand is not in the RBT and the RBT is not empty
            //                 notify the player that there are still valid 5-card hands and cannot pass.
            //                 Wait for Player to input another hand
            while ((myRBT.findNode(myHand) == null) && !myHand.isAValidHand()) {
                System.out.println("Invalid, please choose a valid 5-card hand.");
                myHand = getUserHand(myCards, myPocketSize);
            }

            // Step 2-3 : Save the chosen hand as "PLAYERHAND", and update pocket card and RBT
            //            - Delete the invalid hands from the RBT using deleteInvalidHands()
            //            - Remove the consumed 5 cards from the pocket cards. 
            //            - Remember to reduce the pocket size by 5.

                myRBT.deleteInvalidHands(myHand);
                for (int i = 0; i < myCards.length; i++) {
                    if (myCards[i] != null && myHand.hasCard(myCards[i])) {
                        myCards[i] = null; // Mark the card as used
                    }
                }
                myRBT.delete(myHand);
                //updatePocketCards(myCards, myHand);

            // Step 2-4 : Using the logic from Lab 1, construct the Aggressive AI Logic
            //            - If you have completed Lab 1, use HandsMaxHeap.  
            //              Otherwise, you can use the provided HandsBST. (apply knowledge from 2SI3)
            //            - For every 5-card move made, remove the consumed 5 cards from AI pocket cards, reduce the pocket size
            //              then regenerate the MaxHeap 
            //            - Save the chosen move as the "AIHAND"
            //            - Remember, once out of valid hands, AI can pick any cards to form a 5-card pass move.



            // Change to use correct pocket length
            int allCombs = factorial(aiCards.length) / (factorial(5) * factorial(aiCards.length - 5)); // aiCards is used as the pocket here

            aiBST = new HandsMaxHeap(allCombs); //initialize HandsMax Heap with the number of possible 5 card hands
            if (!aiBST.isEmpty()){
                aiHand = aiBST.removeMax();
            }
            else{
                aiHand = new Hands(aiCards[0], aiCards[1], aiCards[2], aiCards[3], aiCards[4]);
            }

            aiCards = removeUsedCards(aiCards, aiHand, aiPocketSize);
            // Step 2-5 : Determine the Win/Lose result for this round, and update the scores for AI or Player
            //            - Print both PLAYERHAND and AIHAND for visual confirmation
            //            - Compare hands, and increase the score for the respective party who wins the round
            //            - An unlikely Draw (no winner) condition will result in no score increase for either party

                if (aiHand.isMyHandLarger(myHand)){
                    aiScore ++;
                    System.out.println("AI wins round");
                }
                else if (aiHand.isMyHandSmaller(myHand)){
                    playerScore ++;
                    System.out.println("Player wins round");

                }
                else{
                    System.out.println("its a draw");
                }

                }
        // Step 3 - Report the Results
        //  - This part is easy.  Refer to the provided sample executable for printout format
        
        myInputScanner.close();
    }

    // Factorial method moved outside of the main method
    public static int factorial(int n) {
        if (n <= 1) return 1;
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

   /*  public static void generateHandsIntoBST(Card[] cards, HandsBST thisBST)
    {
        // Implement this if you are using the BST version for the Aggressive AI
    
    } */

    public static void generateHandsIntoRBT(Card[] cards, HandsRBT thisRBT)
    {
        // Populate all valid hands into the RBT; generate efficiently the valid hands to get the full mark
        for (int i = 0; i < cards.length - 4; i++) {
            for (int j = i + 1; j < cards.length - 3; j++) {
                for (int k = j + 1; k < cards.length - 2; k++) {
                    for (int l = k + 1; l < cards.length - 1; l++) {
                        for (int m = l + 1; m < cards.length; m++) {
                            Hands hand = new Hands(cards[i], cards[j], cards[k], cards[l], cards[m]);
                            if (hand.isAValidHand()) {
                                thisRBT.insert(hand);  
                            }
                        }
                    }
                }
            }
        }
    }
    

    public static void sortCards(Card[] myCards) {
        // Implementing a selection sort algorithm to sort cards from lowest to highest
        int lowestCard = 0; // will store smallest element
    
        for (int i = 0; i < myCards.length - 1; i++) {  
            lowestCard = i;  
            for (int j = i + 1; j < myCards.length; j++) {  
                if (myCards[j].rank < myCards[lowestCard].rank) {  
                    lowestCard = j;  
                }
            }
    
            // Swap the cards
            Card tempCard = myCards[i];  // card at index i stored in temporary variable
            myCards[i] = myCards[lowestCard];  // card at i is replaced with smallest card
            myCards[lowestCard] = tempCard;  // the card originally at i is now smallest card
        }
    }

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
    


    // This method enables Player to use the numerical key entries to select
    // the 5 cards to form a hand as a tentative move.
    public static Hands getUserHand(Card[] myCards, int size)
    {
        int[] mySelection = new int[5];  
        myInputScanner = new Scanner(System.in);

        System.out.println();
        for(int i = 0; i < 5; i++)
        {            
            System.out.printf("Card Choice #%d: ", i + 1);
            mySelection[i] = myInputScanner.nextInt() - 1;
            if(mySelection[i] > size) mySelection[i] = size - 1;
            if(mySelection[i] < 0) mySelection[i] = 0;            
        }
        
        Hands newHand = new Hands(  myCards[mySelection[0]], 
                                    myCards[mySelection[1]], 
                                    myCards[mySelection[2]], 
                                    myCards[mySelection[3]], 
                                    myCards[mySelection[4]]);

        return newHand;
    }

}
