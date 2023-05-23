public class Item {
    private int profit,weight;
    Item(int weight,int profit){
        this.weight=weight;this.profit=profit;
    }
    public int weight(){
        return weight;
    }
    public int profit(){
        return profit;
    }
    public String toString(){
        return weight + " - " +profit;
    }
}
