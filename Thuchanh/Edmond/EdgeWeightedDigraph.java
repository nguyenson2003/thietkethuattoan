
/******************************************************************************
 *  Compilation:  javac EdgeWeightedDigraph_T.java
 *  Execution:    java EdgeWeightedDigraph_T digraph.txt
 *  Dependencies: Bag.java DirectedEdge.java
 *  Data files:   https://algs4.cs.princeton.edu/44st/tinyEWD.txt
 *                https://algs4.cs.princeton.edu/44st/mediumEWD.txt
 *                https://algs4.cs.princeton.edu/44st/largeEWD.txt
 *
 *  An edge-weighted digraph, implemented using adjacency lists.
 *
 ******************************************************************************/

 

/**
 *  The {@code EdgeWeightedDigraph_T} class represents a edge-weighted
 *  digraph of vertices named 0 through <em>V</em> - 1, where each
 *  directed edge is of type {@link DirectedEdge} and has a real-valued weight.
 *  It supports the following two primary operations: add a directed edge
 *  to the digraph and iterate over all of edges incident from a given vertex.
 *  It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the edges incident from a given vertex, which takes
 *  time proportional to the number of such edges.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private int E;                      // number of edges in this digraph
    private ST<Vertex,Bag<DirectedEdge>> adj;       // adj[v] = adjacency list for vertex v
    private ST<Vertex,Bag<DirectedEdge>> adj_t;     // adj_t[w] = reverse adjacency list for vertex w
    /**
     * Initializes an empty edge-weighted digraph with {@code V} vertices and 0 edges.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public EdgeWeightedDigraph() {
        this.E = 0;
        adj = new ST<>();
        adj_t = new ST<>();
    }

    /**  
     * Initializes an edge-weighted digraph from the specified input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices and edge weights,
     * with each entry separated by whitespace.
     *
     * @param  in the input stream
     */
    public EdgeWeightedDigraph(In in) {
        this();
        while (!in.isEmpty()) {
            Vertex v = new Vertex(in.readString());
            Vertex w = new Vertex(in.readString());
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    /**
     * Initializes a new edge-weighted digraph that is a deep copy of {@code G}.
     *
     * @param  G the edge-weighted digraph to copy
     */
    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this();
        this.E = G.E();
        for (DirectedEdge e:G.edges()) {
            this.addEdge(e);
        }
    }

    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return adj.size();
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
        return E;
    }


    /**
     * Adds the directed edge {@code e} to this edge-weighted digraph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between {@code 0}
     *         and {@code V-1}
     */
    public void addEdge(DirectedEdge e) {
        Vertex v = e.from();
        Vertex w = e.to();
        if(!adj.contains(v))adj.put(v,new Bag<DirectedEdge>());
        if(!adj.contains(w))adj.put(w,new Bag<DirectedEdge>());
        if(!adj_t.contains(v))adj_t.put(v,new Bag<DirectedEdge>());
        if(!adj_t.contains(w))adj_t.put(w,new Bag<DirectedEdge>());
        adj.get(v).add(e);
        adj_t.get(w).add(e);
        E++;
    }

    // throw an IllegalArgumentException unless 
    private void validateVertex(Vertex v) {
        if (!adj.contains(v))
            throw new IllegalArgumentException("vertex " + v + " is not in graph");
    }
    
    
    public boolean contains(Vertex v) {
        return adj.contains(v);
    }
    
    
    /**
     * Returns the directed edges incident from vertex {@code v}.
     *
     * @param  v the vertex
     * @return the directed edges incident from vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless
     */
    public Iterable<DirectedEdge> adj(Vertex v) {
        validateVertex(v);
        return adj.get(v);
    }
    
    /**
     * Returns the directed edges incident to vertex {@code w}.
     *
     * @param  w the vertex
     * @return the directed edges incident to vertex {@code w} as an Iterable
     * @throws IllegalArgumentException unless
     */
    public Iterable<DirectedEdge> adj_t(Vertex w) {
        validateVertex(w);
        return adj_t.get(w);
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int indegree(Vertex w) {
        validateVertex(w);
        return adj_t.get(w).size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outdegree(Vertex v) {
        validateVertex(v);
        return adj.get(v).size();
    }

    /**
     * Returns all directed edges in this edge-weighted digraph.
     * To iterate over the edges in this edge-weighted digraph, use foreach notation:
     * {@code for (DirectedEdge e : G.edges())}.
     *
     * @return all edges in this edge-weighted digraph, as an iterable
     */
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> list = new Bag<DirectedEdge>();
        for (Vertex v:adj) {
            for (DirectedEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    }
    
    /**
     * Returns the vertexs
     *
     * @param  v the vertex
     * @return the vertexs as an Iterable
     */
    public Iterable<Vertex> vertices() {
        return adj.keys();
    }

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(adj.size() + " " + E + NEWLINE);
        for (Vertex v:adj) {
            s.append(v + ": ");
            for (DirectedEdge e : adj.get(v)) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Unit tests the {@code EdgeWeightedDigraph_T} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        StdOut.println("Number of vertex: "+G.V());
        StdOut.print("All of vertexs: ");
        for(Vertex v:G.vertices()){
            StdOut.print(v+" ");
        }
        StdOut.println();
        StdOut.println("Graph:");
        StdOut.println(G);
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
