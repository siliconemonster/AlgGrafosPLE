import java.util.HashMap;

import java.util.ArrayList;

public class Graph {
    private HashMap<Integer,Vertex> vertex_set;

    public Graph() {
        vertex_set = new HashMap<Integer,Vertex>();
    }

    public void add_vertex() {
        Vertex v = new Vertex( vertex_set.size()+1 );
        vertex_set.put( v.id, v );
    }

    public void add_arc( Integer id1, Integer id2) {
        Vertex v1 = vertex_set.get(id1);
        Vertex v2 = vertex_set.get(id2);
        v1.add_neighbor( v2 );
    }

    public void add_edge( Integer id1, Integer id2) {
        Vertex v1 = vertex_set.get(id1);
        Vertex v2 = vertex_set.get(id2);
        v1.add_neighbor( v2 );
        v2.add_neighbor( v1 );

// ou
//        add_arc( id1, id2 );
//        add_arc( id2, id1 );
    }

    public int degree(){
        int[] degree;

        for(int i = 0; i <= vertex_set.size; i++){
            degree[i] = 0;
        }
        for(int i = 0; i <= vertex_set.size; i++){
            degree[i] = nbhood.values();
            print("Grau do vértice " + i + " é " + degree[i]);
        }
    }

    public int max_degree() {
        // fazer
        return 0;
    }

    public boolean is_undirected() {
        // fazer
        return false;
    }

    public Graph subjacent() {
        // fazer
        return this;
    }

    public boolean is_connected() {
        // fazer
        return false;
    }

    public void BFS( Integer id_raiz ) {
        Vertex raiz = vertex_set.get( id_raiz );
        // fazer
    }

    public void print() {
        System.out.println("Grafo: ");

        for(int i = 1; i <= vertex_set.size(); i++)
            vertex_set.get(i).print();
    }
}
