import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;
public class UnoGame{
    private static final String[] COLORS = {"Red", "Yellow", "Green", "Blue"};
    private char cPlayer;
    private ArrayList<UnoCard> deck, userHand, cpuHand;
    private Scanner input;
    private UnoCard currentCard;
    
    public UnoGame(){
        this.cPlayer = 'u';
        this.deck = new ArrayList<UnoCard>();
        this.userHand = new ArrayList<UnoCard>();
        this.cpuHand = new ArrayList<UnoCard>();
        this.input = new Scanner(System.in);
    }

    public void fillDeck(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j <= 13; j++){
                UnoCard card = new UnoCard(COLORS[i], j);
                this.deck.add(card);
            }
        }
        for(int i = 0; i < 4; i++){
            for(int j = 1; j <= 14; j++){
                if(j != 13){
                    UnoCard card = new UnoCard(COLORS[i], j);
                    this.deck.add(card);
                }
            }
        }
    }

    public void play1P(){ // Possibly make a play2p() method.
        this.fillDeck();
        this.shuffle();
        this.deal();
        int randColor = (int)Math.floor(Math.random() * 4);
        int randFace = (int)Math.floor(Math.random() * 9);
        currentCard = new UnoCard(COLORS[randColor], randFace);
        System.out.println("\nStarting card is: " + currentCard);
        String choice = "start";
        boolean hasPlayed;
        while(!choice.equals("quit")){
            hasPlayed = false;
            if(cPlayer == 'u'){
                System.out.println("It is your turn!");
                System.out.print("Your Cards: ");
                printArrList(userHand);
                if(hasPlayableCard(userHand)){
                    System.out.print("Please choose a valid card (type 'quit' to quit): ");
                    choice = input.nextLine();
                    if(choice.equals("quit"))
                            return;
                    int ind = Integer.parseInt(choice); // TODO add input protection.
                    hasPlayed = this.checkAndPlay(ind);
                }else{
                    //  draw more cards from deck
                    while(!hasPlayableCard(userHand))
                        this.drawUntilPlayable();
                    System.out.print("Your Cards: ");
                    printArrList(userHand);
                    if(hasPlayableCard(userHand)){
                        System.out.print("Please choose a valid card (type 'quit' to quit): ");
                        choice = input.nextLine();
                        if(choice.equals("quit"))
                            return;
                        int ind = Integer.parseInt(choice); // TODO add input protection.
                        hasPlayed = this.checkAndPlay(ind);
                    }
                }
            }
            else if(cPlayer == 'c'){
                System.out.println("Computer's Turn.");
                for(int i = 0; i < this.cpuHand.size(); i++){
                    if(this.canPlace(cpuHand.get(i))){
                        System.out.println("Computer Plays a " + cpuHand.get(i) + ".");
                        hasPlayed = this.checkAndPlay(i);
                        break;
                    }
                }
                if(!hasPlayed){
                    while(!hasPlayableCard(cpuHand))
                        this.drawUntilPlayable();
                }
            }
            if(userHand.size() == 0){
                System.out.println("User wins!");
                return;
            }
            if(cpuHand.size() == 0){
                System.out.println("Computer wins!");
                return;
            }
            if(this.currentCard.getFace() == 12 || this.currentCard.getFace() == 14)
                plusCard();
            if(hasPlayed && this.currentCard.getFace() != 10 && this.currentCard.getFace() != 11) // 10 and 11 are the indices for skip and reverse (in 2 player uno, reverse acts as a skip).
                this.changePlayer();
            System.out.println("\nCurrent Card: " + currentCard + ".");
        }
    }

    public boolean checkAndPlay(int index){ // TODO wild, reverse, wild +4, skip, etc
        if(this.cPlayer == 'u'){
            if(this.canPlace(userHand.get(index))){
                if(userHand.get(index).getFace() == 13 || userHand.get(index).getFace() == 14){
                    wild();
                    userHand.remove(index);
                }else{
                    currentCard = userHand.get(index);
                    userHand.remove(index);
                }
                return true;
            }
            return false;
        }else{
            if(this.canPlace(cpuHand.get(index))){
                if(cpuHand.get(index).getFace() == 13 || cpuHand.get(index).getFace() == 14){
                    wild();
                    cpuHand.remove(index);
                }else{
                    currentCard = cpuHand.get(index);
                    cpuHand.remove(index);
                }
                return true;
            }
            return false;
        }
    }

    public boolean canPlace(UnoCard c){
        if(c.getFace() == 13 || c.getFace() == 14 || c.getColor().equals(this.currentCard.getColor()) || c.getFace() == this.currentCard.getFace())
            return true;
        return false;
    }

    public boolean hasPlayableCard(ArrayList<UnoCard> hand){
        for(UnoCard c : hand)
            if(this.canPlace(c))
                return true;
        return false;
    }

    public void drawUntilPlayable(){
        if(cPlayer == 'u'){
            while(!this.hasPlayableCard(userHand)){
                System.out.println(">  User draws " + this.deck.get(0));
                this.userHand.add(this.deck.get(0));
                this.deck.remove(0);
            }
        }
        else if(cPlayer == 'c'){
            while(!this.hasPlayableCard(cpuHand)){
                System.out.println(">  Computer draws a card.");
                this.cpuHand.add(this.deck.get(0));
                this.deck.remove(0);
            }
        }
    }

    public void deal(){
        for(int i = 0; i < 7; i++){
            userHand.add(deck.get(0));
            deck.remove(0);
            cpuHand.add(deck.get(0));
            deck.remove(0);
        }
    }

    public char getOpponent(){
        if(this.cPlayer == 'u')
            return 'c';
        return 'u';
    }

    public void changePlayer(){
        if(this.cPlayer == 'u')
            this.cPlayer = 'c';
        else
            this.cPlayer = 'u';
    }

    public void plusCard(){
        char opponent = this.getOpponent();
        int cards = 0;
        if(this.currentCard.getFace() == 12)
            cards = 2;
        else if(this.currentCard.getFace() == 14)
            cards = 4;
        if(opponent == 'c'){
            for(int i = 0; i < cards; i++){
                this.cpuHand.add(deck.get(0));
                System.out.println(">  Computer draws a card.");
                deck.remove(0);
            }
        }else if(opponent == 'u'){
            for(int i = 0; i < cards; i++){
                this.userHand.add(deck.get(0));
                System.out.println(">  You draw a " + deck.get(0) + ".");
                deck.remove(0);
            }
        }
    }

    public void wild(){
        String newColor = "start";
        if(cPlayer == 'u'){
            while(!(newColor.equals("red") || newColor.equals("blue") || newColor.equals("green") || newColor.equals("yellow"))){
                System.out.println("Please choose a new color: ");
                newColor = input.nextLine().toLowerCase();
            }
            System.out.println("You chose " + newColor + " as the new color!");
            newColor = newColor.substring(0, 1).toUpperCase() + newColor.substring(1).toLowerCase(); // Scuffed but we ain't stressin.
        }else{
            int rand = (int)Math.floor(Math.random() * 3);
            newColor = COLORS[rand].substring(0, 1).toUpperCase() + COLORS[rand].substring(1).toLowerCase();
            System.out.println("Computer chose " + newColor + " as the new color!");
        }
        currentCard = new UnoCard(newColor, 15); // 15 is an arbitrary number for a card. No card will have a face value of 15.
    }

    public void shuffle(){
        Collections.shuffle(this.deck);
    }

    public static void printArrList(ArrayList<UnoCard> list){
        String output = "[";
        for(int i = 0; i < list.size(); i++){
            output += "(" + i + ")" + list.get(i);
            if(i < list.size() - 1)
                output += ", ";
        }
        output += "]";
        System.out.println(output);    
    }

    public String toString(){
        String output = "[";
        for(int i = 0; i < this.deck.size(); i++){
            output += this.deck.get(i).toString();
            if(i < this.deck.size() - 1)
                output += ", ";
        }
        output += "]";
        return output;
    }

    public static void main(String[] args){
        UnoGame g = new UnoGame();
        g.play1P();
    }
}