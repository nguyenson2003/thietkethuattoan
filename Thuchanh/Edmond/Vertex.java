import java.util.TreeSet;

public class Vertex implements Comparable<Vertex>
{
    private String name;
    private TreeSet<String> child = new TreeSet<>();

    /**
     * Constructor for Node of class Vertex
     */
    public Vertex(String name)
    {
        this.name = name;
        child.add(""+this);
    }
    
    /**
     * Constructor for SuperNode of class Vertex
     */
    public Vertex(Bag<Vertex> vertexs){
        name="";
        for(Vertex v:vertexs){
            name+=v+" ";
        }
        name = name.trim().replace(' ',',');
        for(String str : name.split("\\,"))
            child.add(str);
        
    }
    
    public boolean contains(String v){
        for(String x : v.split("\\,")){
            if(!child.contains(x))return false;
        }
        return true;
    }
    public boolean contains(Vertex v){
        for(String x:v.child){
            if(!child.contains(x))return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    @Override
    public int compareTo(Vertex that){
        return this.name.compareTo(that.name);
    }
    
    @Override
    public boolean equals(Object o){
        return this.compareTo((Vertex)o)==0;
    }
}
