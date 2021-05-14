public class UnoCard {
    private String color;
    private int face; /* 0 - 9 nums, 10: skip, 11: reverse, 12: +2, 13: Wild, 14: +4 Wild */

    public UnoCard(String color, int face){
        this.color = color;
        this.face = face;
    }

    public String toString(){
        String faceAdd = "";
        if(face == 13)
            return "Wild!";
        else if(face == 14)
            return "+4 Wild!";
        else if(face == 10)
            faceAdd = "Skip";
        else if(face == 11)
            faceAdd = "Reverse";
        else if(face == 12)
            faceAdd = "+2";
        else
            faceAdd = face+"";
        
        return color + " " + faceAdd;
    }
}
