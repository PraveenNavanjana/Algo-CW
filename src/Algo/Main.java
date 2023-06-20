// student id-w1870590
// student name-Praveen navanjana
//......//

package Algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

class DrawGraph {
    int V; // The number of vertices
    Set<Integer>[] adj; // Adjacency sets array

    public DrawGraph(int V) {
        this.V = V;
        adj = new HashSet[V];
        int i = 0;
        while (i < V) {
            adj[i] = new HashSet<>();
            i++;
        }
    }


    public void AddEdge(int v, int w) {
        adj[v].add(w); // Add w to v's set
    }

    public void DeleteSinkVertices(boolean[] visited) {
        System.out.println();

        boolean sinkVertexFound;
        do {
            sinkVertexFound = false;

            // Find and eliminate sink vertices

            for (int i = 0; i < V; i++) {
                if (!visited[i] && adj[i].isEmpty()) {
                    visited[i] = true;

                    System.out.println("Found and eliminated sink vertex: " + i);

                    // Remove the sink vertex from all adjacency sets

                    for (Set<Integer> set : adj) {
                        set.remove(i);
                    }
                    sinkVertexFound = true;
                    break;
                }
            }
        } while (sinkVertexFound);

    }

    public boolean isCyclicUtil(int v, boolean[] visited) {
        visited[v] = true;

        // Recur for all vertices next to this one.
        for (int i : adj[v]) {
            if (!visited[i] || isCyclicUtil(i, visited)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCyclic() {
        boolean[] visited = new boolean[V];
        DeleteSinkVertices(visited);

        // Check for cycles in the remaining graph

        int i = 0;
        while (i < V) {
            if (!visited[i] && isCyclicUtil(i, visited)) {
                return true;
            }
            i++;
        }

        return false;
    }

    public int getV() {
        return V;
    }

    public Set<Integer>[] getAdj() {
        return adj;
    }

    public static DrawGraph parseFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        // Determine the number of vertices by reading the first sentence
        line = reader.readLine();
        int V = Integer.parseInt(line);
        DrawGraph graph = new DrawGraph(V);

        // Continue reading to add edges to the graph.
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");
            int v = Integer.parseInt(tokens[0]) - 1;
            int w = Integer.parseInt(tokens[1]) - 1;
            graph.AddEdge(v, w);
        }

        reader.close();
        return graph;
    }
}


class test {
    public static void main(String[] args) {
        try {
            DrawGraph g = DrawGraph.parseFile("input.txt");

            // Print all of the vertices
            System.out.println("Vertices:");
            int i = 1;
            while (i <= g.getV()) {
                System.out.print(i + " ");
                i++;
            }
            System.out.println();


            // Print all of the edges
            System.out.println("Edges:");
            int k = 0;
            while (k < g.getV()) {
                for (Integer j : g.getAdj()[k]) {
                    System.out.println(k + " -> " + j);
                }
                k++;
            }


            boolean isCyclic = g.isCyclic();
            String result = isCyclic ? "Graph is cyclic" : "Graph is acyclic";

            System.out.println();
            System.out.println(result);
            System.out.println();

        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
    }
}
