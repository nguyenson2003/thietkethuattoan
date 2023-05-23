import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.Iterator;

class Job_clt implements Iterable<Job>
{
    private Queue<Job> collect = new Queue<>();
    private int maxProfit=0;
    // Function to perform binary search on the given jobs which are sorted by finish time.
    // The function returns index of the last job which doesn't conflict with the given job
    // i.e. whose finish time is less than or equal to the start time of the given job.
    public static int findLastNonConflictingJob(List<Job> jobs, int n)
    {
        // search space
        int low = 0;
        int high = n;
        // iterate till search space is not exhausted
        while (low <= high)
        {
            int mid = (low + high) / 2;
            if (jobs.get(mid).finish <= jobs.get(n).start) {
                if (jobs.get(mid + 1).finish <= jobs.get(n).start) {
                    low = mid + 1;
                } else {
                    return mid;
                }
            }
            else {
                high = mid - 1;
            }
        }

        // return negative index if no non-conflicting job is found
        return -1;
    }
    // Function to find the maximum profit of non-overlapping jobs using DP
    public Job_clt(List<Job> jobs){
        // sort jobs in increasing order of their finish times
        //Collections.sort(jobs, (x, y) -> x.finish - y.finish);
        Collections.sort(jobs);
        // get number of jobs
        int n = jobs.size();

        // construct an lookup table where the i'th index stores the maximum profit
        // for first i jobs
        int[] maxProfit = new int[n];

        // maximum profit gained by including the first job
        maxProfit[0] = jobs.get(0).profit;

        // fill maxProfit[] table in bottom-up manner from the second index
        for (int i = 1; i < n; i++)
        {
            // find the index of last non-conflicting job with current job

            // Tim index la cong viec sau cung khong xung dot voi cong viec hien thoi i .....  
            int index = findLastNonConflictingJob(jobs,i);

            // include the current job with its non-conflicting jobs
            int incl = jobs.get(i).profit;
            if (index != -1) {
                incl += maxProfit[index];
            }

            // store the maximum profit by including or excluding current job
            maxProfit[i] = Math.max(incl, maxProfit[i - 1]);
        }

        for(int i=n-1;i>=0;i--){
            if(i==0){
                collect.enqueue(jobs.get(i));break;
            }
            if(maxProfit[i]!=maxProfit[i-1]){
                collect.enqueue(jobs.get(i));
                i=findLastNonConflictingJob(jobs,i)+1;
            }
        }
        // return maximum profit
        this.maxProfit = maxProfit[n - 1];
    }
    public Iterator<Job> iterator() {
        return collect.iterator();
    }
    public int maxProfit(){return maxProfit;}
    public static void main(String[] args)
    {
        List<Job> jobs = Arrays.asList(new Job( 0, 6, 60 ),
                                            new Job( 1, 4, 30 ),
                                            new Job( 3, 5, 10 ),
                                            new Job( 5, 7, 30 ),
                                            new Job( 5, 9, 50 ),
                                            new Job( 7, 8, 10 ));
        Job_clt sol = new Job_clt(jobs);

        System.out.println("The maximum profit is " + sol.maxProfit());
        System.out.println("job should collect:");
        for(Job x:sol){
            StdOut.println(x);
        }
    }
}