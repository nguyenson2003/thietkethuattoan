/******************************************************************************
 *  Compilation:  javac FileIndex.java
 *  Execution:    java FileIndex file1.txt file2.txt file3.txt ...
 *  Dependencies: ST.java SET.java In.java StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/35applications/ex1.txt
 *                https://algs4.cs.princeton.edu/35applications/ex2.txt
 *                https://algs4.cs.princeton.edu/35applications/ex3.txt
 *                https://algs4.cs.princeton.edu/35applications/ex4.txt
 *
 *  % java FileIndex ex*.txt
 *  age
 *   ex3.txt
 *   ex4.txt 
 * best
 *   ex1.txt 
 * was
 *   ex1.txt
 *   ex2.txt
 *   ex3.txt
 *   ex4.txt 
 *
 *  % java FileIndex *.txt
 *
 *  % java FileIndex *.java
 *
 ******************************************************************************/

 
import java.util.HashMap;
import java.io.File;

/**
 *  The {@code FileIndex} class provides a client for indexing a set of files,
 *  specified as command-line arguments. It takes queries from standard input
 *  and prints each file that contains the given query.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/35applications">Section 3.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class FrequencyIndex { 

    // Do not instantiate.
    private FrequencyIndex() { }
    // {"ex1.txt","ex2.txt","ex3.txt","ex4.txt"}
    public static void main(String[] args) { 
        // System.setIn(new FileInputStream(new File("ex1.txt")));
        // key = word, value = file + so lan xuat hien
        HashMap<String,HashMap<File,Integer>> map = new HashMap<>();
        
        // create inverted index of all files
        StdOut.println("Indexing files");
        for (String filename : args) {
            StdOut.println("  " + filename);
            File file = new File(filename);
            In in = new In(file);
            
            while (!in.isEmpty()) {
                String word = in.readString();
                if(!map.containsKey(word)){
                    map.put(word,new HashMap<>());
                }
                HashMap<File,Integer> temp = map.get(word);
                temp.put(file,temp.getOrDefault(file,0)+1);
            }
        }
        
        for(var temp:map.entrySet()){
            StdOut.println(temp.getKey());
            for(var temp2:temp.getValue().entrySet()){
                StdOut.printf("\t %s - %d lan\n",temp2.getKey(),temp2.getValue());
            }
        }
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

