
/******************************************************************************
 *  Compilation:  javac Edmond.java
 *  Execution:    java  Edmond filename.txt
 *  Dependencies: EdgeWeightedGraph.java Edge.java Queue.java
 *                UF.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Compute a minimum spanning forest using Kruskal's algorithm.
 *
 *  %  java Edmond tinyEWG.txt 
 *  0-7 0.16000
 *  2-3 0.17000
 *  1-7 0.19000
 *  0-2 0.26000
 *  5-7 0.28000
 *  4-5 0.35000
 *  6-2 0.40000
 *  1.81000
 *
 *  % java Edmond mediumEWG.txt
 *  168-231 0.00268
 *  151-208 0.00391
 *  7-157   0.00516
 *  122-205 0.00647
 *  8-152   0.00702
 *  156-219 0.00745
 *  28-198  0.00775
 *  38-126  0.00845
 *  10-123  0.00886
 *  ...
 *  10.46351
 *
 ******************************************************************************/

 

/**
 *  The {@code Edmond} class represents a data type for computing a
 *  <em>minimum spanning tree</em> in an edge-weighted graph.
 *  The edge weights can be positive, zero, or negative and need not
 *  be distinct. If the graph is not connected, it computes a <em>minimum
 *  spanning forest</em>, which is the union of minimum spanning trees
 *  in each connected component. The {@code weight()} method returns the 
 *  weight of a minimum spanning tree and the {@code edges()} method
 *  returns its edges.
 *  <p>
 *  This implementation uses <em>Krusal's algorithm</em> and the
 *  union-find data type.
 *  The constructor takes time proportional to <em>E</em> log <em>E</em>
 *  and extra space (not including the graph) proportional to <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the {@code weight()} method takes constant time
 *  and the {@code edges()} method takes time proportional to <em>V</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For alternate implementations, see {@link LazyPrimMST}, {@link PrimMST},
 *  and {@link BoruvkaMST}.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class EdmondMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;
    private double weight;                                          // weight of MST
    private Queue<DirectedEdge> mst = new Queue<DirectedEdge>();    // edges in MST
    private ST<Vertex,DirectedEdge> edgeTo;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public EdmondMST(EdgeWeightedDigraph G,String root) {
        Vertex r = new Vertex(root);
        edgeTo   = new ST<>();
        if(!G.contains(r)) 
            throw new IllegalArgumentException("Vertex " + root + " is not in graph");
        EdgeWeightedDigraph temp_mst = new EdgeWeightedDigraph();
        
        //chon cac canh di den dinh w be nhat tru node goc
        for(Vertex w:G.vertices()){
            if(w.equals(r))continue;
            DirectedEdge min_edge = new DirectedEdge(w,w,Double.MAX_VALUE);
            for(DirectedEdge e:G.adj_t(w)){
                if(e.weight()<min_edge.weight())min_edge=e;
            }
            //khong the tao mst
            if(min_edge.weight() == Double.MAX_VALUE) 
                throw new IllegalArgumentException("Cannot create minium spainning tree");
            edgeTo.put(w,min_edge);
            temp_mst.addEdge(min_edge);
        }
        
        //check cycle
        EdgeWeightedDirectedCycle cycle = new EdgeWeightedDirectedCycle(temp_mst);
        //co cycle
        if(cycle.hasCycle()){
            //create super node
            Bag<Vertex> bag = new Bag<>();
            for(Vertex v:cycle.v_cycle()){
                bag.add(v);
            }
            Vertex s_node = new Vertex(bag);
            //create G'
            EdgeWeightedDigraph temp_G = new EdgeWeightedDigraph();
            for(DirectedEdge e : G.edges()){
                Vertex v = e.from();
                Vertex w = e.to();
                if(s_node.contains(v)) v = s_node;
                if(s_node.contains(w)) w = s_node;
                if(v==w) continue;
                if(w.equals(r))continue;
                //create E'
                DirectedEdge temp_e = new DirectedEdge(v,w,e.weight()-edgeTo.get(e.to()).weight());
                temp_e.setParent(e);
                temp_G.addEdge(temp_e);
            }
            // create mst
            edgeTo = new ST<>();
            EdmondMST mini = new EdmondMST(temp_G,root);
            for(DirectedEdge e: mini.edges()){
                edgeTo.put(e.getParent().to(),e.getParent());
                mst.enqueue(e.getParent());
                weight+=e.getParent().weight();
            }
            for(DirectedEdge e:cycle.cycle()){
                //khong lay cac canh da chon khi co lai
                if(!edgeTo.contains(e.to())) {
                    edgeTo.put(e.to(),e);
                    mst.enqueue(e);
                    weight+=e.weight();
                }
            }
        }else{
            for(DirectedEdge e: temp_mst.edges()){
                mst.enqueue(e);
                weight+=e.weight();
            }
        }
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<DirectedEdge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        return weight;
    }

    /**
     * Unit tests the {@code Edmond} data type.
     *
     * @param args the command-line arguments
     */
    // "tinyEWD.txt","r"
    // "tinyEWD2.txt","r"
    // "tinyEWD3.txt","o"
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        EdmondMST mst = new EdmondMST(G,args[1]);
        for (DirectedEdge e : G.edges()) {
            StdOut.println(e);
        }
        StdOut.println();
        for (DirectedEdge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }

}


/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
