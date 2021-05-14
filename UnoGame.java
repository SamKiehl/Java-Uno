import java.util.Collections;
import java.util.ArrayList;
public class UnoGame{
    private ArrayList<UnoCard> deck;
    
    public UnoGame(){
        this.deck = new ArrayList<UnoCard>();
    }

    public void fillDeck(){
        String[] colors = {"Red", "Yellow", "Green", "Blue"};
        for(int i = 0; i < 4; i++){
            for(int j = 0; j <= 13; j++){
                UnoCard card = new UnoCard(colors[i], j);
                this.deck.add(card);
            }
        }
        for(int i = 0; i < 4; i++){
            for(int j = 1; j <= 14; j++){
                if(j != 13){
                    UnoCard card = new UnoCard(colors[i], j);
                    this.deck.add(card);
                }
            }
        }
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

    public void shuffle(){
        Collections.shuffle(this.deck);
    }

    public static void main(String[] args){
        UnoGame g = new UnoGame();
        g.fillDeck();
        System.out.println(g);
        g.shuffle();
        System.out.println(g);
    }
}