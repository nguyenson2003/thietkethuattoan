import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.Iterator;

// Data structure to store a Job
class Job implements Comparable<Job>{
    int start, finish, profit;

    Job(int start, int finish, int profit) {
        this.start = start;
        this.finish = finish;
        this.profit = profit;
    }
    //public int compareTo (Job x, Job y){
    //    return Double.compare(x.finish, y.finish);
    //}
    public int compareTo (Job y){
    return Double.compare(this.finish, y.finish);
    }
    public String toString(){
        return start+" - "+finish+" - "+profit;
    }
}
