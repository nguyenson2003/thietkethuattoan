import java.util.Comparator;
import java.util.StringTokenizer;
import java.io.*;

public class SinhVien implements Comparable<SinhVien>
{
    private String Ten;public String Ten(){return Ten;}
    private long MaSV;public long MaSV(){return MaSV;}
    private double DiemTBC;public double DiemTBC(){return DiemTBC;}
    private String NgaySinh;public String NgaySinh(){return NgaySinh;}
    private String QueQuan;public String QueQuan(){return QueQuan;}
    private String Sdt;public String Sdt(){return Sdt;}


    public SinhVien(String Ten, int MaSV,double DiemTBC, String NgaySinh, String QueQuan, String Sdt){
        this.Ten=Ten;
        this.MaSV=MaSV;
        this.DiemTBC=DiemTBC;
        this.NgaySinh=NgaySinh;
        this.QueQuan=QueQuan;
        this.Sdt=Sdt;
    }
    public SinhVien(String svStr){
        StringTokenizer temp = new StringTokenizer(svStr,",");
        Ten = temp.nextToken();
        MaSV = Integer.parseInt(temp.nextToken());
        DiemTBC = Double.parseDouble(temp.nextToken());
        NgaySinh = temp.nextToken();
        QueQuan = temp.nextToken();
        Sdt = temp.nextToken();
    }

    public static class tenOrder implements Comparator<SinhVien>{
        @Override
        public int compare(SinhVien o1, SinhVien o2) {
            return o1.Ten.compareTo(o2.Ten);
        }
    }
    
    @Override
    public int compareTo(SinhVien b){
        return this.MaSV>b.MaSV?1:-1;
    }
    
    public static  Comparator<SinhVien> diemtbcOrder(){
        return (o1,o2)-> o1.DiemTBC>o2.DiemTBC?1:-1;
    }

    public static Comparator<SinhVien> ngaysinhOrder()  {
        return (o1,o2)-> o1.NgaySinh.compareTo(o2.NgaySinh);
    }

    public static  Comparator<SinhVien> quequanOrder(){
        return (o1, o2) -> o1.QueQuan.compareTo(o2.QueQuan);
    }

    public static Comparator<SinhVien> stdOrder() {
        return (o1, o2) -> o1.Sdt.compareTo(o2.Sdt);
    }

    @Override
    public String toString() {
        return String.format("%d - %10s - %.2f - %12s - %5s - %12s", MaSV, Ten, DiemTBC, NgaySinh, QueQuan, Sdt);
    }
    
    
    public static void main(String []args) throws IOException {
        System.setIn(new FileInputStream(new File("sv.txt")));
        //theo masv
        BST<Long,SinhVien> st = new BST<>();
        for(int i=0;!StdIn.isEmpty();i++){
            SinhVien val = new SinhVien(StdIn.readLine());
            st.put(val.MaSV(),val);
        }
        for(long ma:st.keys()){
            StdOut.println(st.get(ma));
        }
        StdOut.println();
        
        //theo sdt
        BST<String,SinhVien> st2 = new BST<>();
        for(long ma:st.keys()){
            SinhVien val = st.get(ma);
            st2.put(val.Sdt(),val);
        }
        for(String sdt:st2.keys()){
            StdOut.println(st2.get(sdt));
        }
        StdOut.println();

        //tìm dong huong
        BST<String,Queue<SinhVien>> st3 = new BST<>();
        for(long ma:st.keys()){
            SinhVien val = st.get(ma);
            if(!st3.contains(val.QueQuan())) st3.put(val.QueQuan(),new Queue<>());
            st3.get(val.QueQuan()).enqueue(val);
        }
        for(String que:st3.keys()){
            StdOut.println("Que "+que+":");
            for(SinhVien val:st3.get(que)){
                StdOut.println("\t"+val);
            }
        }
        StdOut.println();
        
    }
}
