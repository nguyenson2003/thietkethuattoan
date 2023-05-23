
/**
 * Write a description of class MonHoc here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MonHoc implements Comparable<MonHoc>
{
    // instance variables - replace the example below with your own
    private String tenMon;
    private int tinChi;

    public MonHoc(String tenMon,int tinChi)
    {
        this.tenMon=tenMon;
        this.tinChi=tinChi;
    }
    public int compareTo(MonHoc o){
        return this.tenMon.compareTo(o.tenMon);
    }
    public String tenMon(){
        return tenMon;
    }
    public int tinChi(){
        return tinChi;
    }
}
