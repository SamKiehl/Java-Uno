import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;
public class UnoGame{
    private static final String[] COLORS = {"Red", "Yellow", "Green", "Blue"};
    private char cPlayer;
    private ArrayList<UnoCard> deck, userHand, cpuHand;
    private Scanner input;
    private UnoCard lastCard;
    
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

    public void play1P(){
        this.fillDeck();
        this.shuffle();
        this.deal();
        int randColor = (int)Math.floor(Math.random() * 4);
        int randFace = (int)Math.floor(Math.random() * 9);
        lastCard = new UnoCard(COLORS[randColor], randFace);
        System.out.println("Starting card is: " + lastCard);
        if(cPlayer == 'u'){
            System.out.print("Your Cards: ");
            printArrList(userHand);
            if(hasPlayableCard(userHand)){
                System.out.print("Please choose a valid card: ");
                String choice = input.nextLine();
                int ind = Integer.parseInt(choice); // TODO add input protection.
                if(this.canPlace(userHand.get(ind))){// TODO wild cards, reverse, skip.
                    lastCard = userHand.get(ind);
                    userHand.remove(ind);
                }
            }else{
                //  draw more cards from deck
                this.drawUntilPlayable();
            }
        }    //printArrList(userHand);
        System.out.println(lastCard);
        
    }

    public boolean canPlace(UnoCard c){
        if(c.getFace() == 13 || c.getFace() == 14 || c.getColor().equals(this.lastCard.getColor()) || c.getFace() == this.lastCard.getFace())
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
                this.userHand.add(this.deck.get(0));
                this.deck.remove(0);
            }
        }
        else if(cPlayer == 'c'){
            while(!this.hasPlayableCard(cpuHand)){
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

    public void changePlayer(){
        if(this.cPlayer == 'u')
            this.cPlayer = 'c';
        else
            this.cPlayer = 'u';
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