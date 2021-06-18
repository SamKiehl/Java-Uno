public class UnoCard implements Comparable<UnoCard>{
    private String color;
    private int face; /* 1 - 9 nums, 10: skip, 11: reverse, 12: +2, 13: Wild, 14: +4 Wild */

    public UnoCard(String color, int face){
        this.color = color;
        this.face = face;
    }

    public String getColor(){return this.color;}

    public int getFace(){return this.face;}

    public int compareTo(UnoCard other){
        if(this.face == other.face && this.color.equals(other.color))
            return 0;
        return -1;
    }

    public boolean equals(UnoCard other){
        if(this.compareTo(other) == 0)
            return true;
        return false;
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
        else if(face < 0 || face > 14)
            faceAdd = "";
        else
            faceAdd = face + "";
        
        return color + " " + faceAdd;
    }
}
