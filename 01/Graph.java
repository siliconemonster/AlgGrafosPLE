import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private HashMap<Integer,Vertex> vertex_set;

    public Graph() {
        vertex_set = new HashMap<Integer,Vertex>();
    }

    public void add_vertex( int id ) {
        if ( this.vertex_set.get( id ) == null ) {
            Vertex v = new Vertex( id );
            vertex_set.put( v.id, v );
        }
        else
            System.out.printf("Já existe vértice com esse número");
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

    public void del_vertex( int id ) {
        // fazer
        //Esse método primeiro procura todos os vértices que tem o
        //vértice id como vizinho e tira de cada vizinhança; e depois
        //remove o próprio vértice
        for ( Vertex v1 : vertex_set.values()){
          v1.nbhood.remove( id );
        }
        vertex_set.remove(id);
    }

    public void compact() {
        // fazer
    }


    public int max_degree() {
        // fazer
        //---------- tentei fazer da cabeça ----------
/*        int temp = 0;

        for(int i = 1; i <= /*quantidade de vértices*//*; i++){
          if (degree[i] > degree[i-1]){
            temp = i;
          }
        }
        System.out.print("O grau máximo é " + grau[temp]);


        return 0;
    }*/

      //---------- parte do professor ----------
      int max = -1;

      //vertex_set.size() = quantidade de vértices
      for(int i = 1; i <= vertex_set.size(); i++){
        if (vertex_set.get(i).degree() > max){
          max = vertex_set.get(i).degree();
        }
      }
      return max;
    }

    public boolean is_undirected() {
        // fazer
        //-----parte do professor------
        /*explicação:
        Para cada vértice v1 do grafo, para cada vizinho v2 desse vértice,
        se v1 for vizinho de v2 mas v2 não for vizinho de v1, não é não
        direcionado.*/

        for( Vertex v1 : vertex_set.values()) {
            for( Vertex v2 : v1.nbhood.values()) {
                if (v2.nbhood.get(v1.id) == null)
                    return false;
            }
        }
        return false;
    }

    public Graph subjacent() {
        // fazer
        //-----parte do professor------
        /*explicação:
        Cria-se um novo objeto tipo Graph g2 para não sobrescrever
        o grafo que já temos, e adicionamos todos os vértices de g1
        em g2.
        Para cada vértice v11 pertencente ao grafo g1, e para cada
        vértice v12 vizinho de v11
        ?????? pega-se o id de cada vértice em g1 para botar em g2 ??????
        adiciona-se o v22 de vizinho do v21 e o v21 de vizinho do v22*/

        Graph g2 = new Graph();
        for( Vertex v11 : this.vertex_set.values()) {
            g2.add_vertex( v11.id );
        }
        for( Vertex v11 : this.vertex_set.values()) {
            for( Vertex v12 : v11.nbhood.values()) {
                Vertex v21 = g2.vertex_set.get( v11.id );
                Vertex v22 = g2.vertex_set.get( v12.id );
                v21.add_neighbor( v22 );
                v22.add_neighbor( v21 );
            }
        }
        return g2;
    }

    public boolean is_connected() {
        // fazer
        return false;
    }

    public void BFS( Integer id_raiz ) {/*
        Vertex raiz = vertex_set.get( id_raiz );
        // fazer
        //---------- Utilizei ajuda do livro do Jayme ----------
        Set<String> visitado = new LinkedHashSet<String>();
        Queue<String> fila = new LinkedList<String>();
        visitado.add(raiz);
        fila.add(raiz);
        while (!fila.isEmpty()) {
            String vertex = fila.poll();
            for (Vertex v : graph.getAdjVertices(vertex)) {
                if (!visitado.contains(v.label)) {
                    visitado.add(v.label);
                    fila.add(v.label);
                }
            }
        }
        return visited;*/
    }

    public void print() {
        System.out.printf("\n\n Grafo, grau máximo %d", this.max_degree());

        if( this.is_undirected() )
            System.out.println("\nNão direcionado");
        else
            System.out.println("\nDirecionado");

        for( Vertex v : vertex_set.values())
            v.print();
    }
}
