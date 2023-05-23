import java.util.Iterator;
//file run: tinyItem.txt
public class Knapsack_opt implements Iterable<Item> {
    private Queue<Item> all = new Queue<>();
    private int weight_knapsack;
    private int weight_all=0;
    private int profit_all=0;
    public Knapsack_opt(int weight_knapsack, Queue<Item> item) {
        this.weight_knapsack=weight_knapsack;
        int N = item.size(),W=weight_knapsack;
        Item list_item[] = new Item[N+1];
        for(int i=1;i<=N;i++){
            list_item[i]=item.dequeue();
        }
        // opt[n][w] = max profit of packing items 1..n with weight limit w
        // sol[n][w] = does opt solution to pack items 1..n with weight limit w include item n?
        int[][] opt = new int[N+1][W+1];
        boolean[][] sol = new boolean[N+1][W+1];
        for (int n = 1; n <= N; n++) {
            for (int w = 1; w <= W; w++) {
                // don't take item n
                int option1 = opt[n-1][w];

                // take item n
                int option2 = Integer.MIN_VALUE;
                if (list_item[n].weight() <= w) option2 = list_item[n].profit() + opt[n-1][w-list_item[n].weight()];

                // select better of two options
                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = (option2 > option1);
            }
        }

        for (int n = N, w = W; n > 0; n--) {
            if (sol[n][w]) {
                all.enqueue(list_item[n]);
                weight_all+=list_item[n].weight();
                profit_all+=list_item[n].profit();
                w = w - list_item[n].weight();
            }
        }
        
    }
    
    public Iterator<Item> iterator() {
        return all.iterator();
    }
    public int weight_knapsack(){return weight_knapsack;}
    public int profit_all(){return profit_all;}
    public int weight_all(){return weight_all;}

    public static void main(String[] args) {
        In in = new In(args[0]);
        Queue<Item> item = new Queue<>();
        int N = in.readInt();
        int W = in.readInt();
        for(int i=0;i<N;i++){
            Item x = new Item(in.readInt(),in.readInt());
            item.enqueue(x);
        }
        Knapsack_opt balo = new Knapsack_opt(W,item);
        StdOut.printf("weight of knapsack: %d\nmax profit knapsack can take: %d\nweight of all item knapsack take: %d\n",
            balo.weight_knapsack(),
            balo.profit_all(),
            balo.weight_all()
        );
        StdOut.println("weight and profit of item in knapsack");
        for(Item x:balo){
            StdOut.println(x);
        }
    }
}
