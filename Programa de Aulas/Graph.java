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
        // feito
        //Esse método primeiro procura todos os vértices que tem o
        //vértice id como vizinho e tira de cada vizinhança; e depois
        //remove o próprio vértice
        for ( Vertex v1 : vertex_set.values()){
          v1.nbhood.remove( id );
        }
        vertex_set.remove(id);
    }

    public void compact() {
        // feito
        int n = vertex_set.size();
    		int [ ] present = new int[n+1];
    		Vertex [ ] stranges = new Vertex[n];
    		for( int i = 1; i <= n; i++) {
    			present[ i ] = 0;
    		}
    		int qst = 0;
            for( Vertex v1 : vertex_set.values() ) {
    			if( v1.id <= n )
    				present[ v1.id ] = 1;
    			else
    				stranges[ qst++ ] = v1;
    		}
    		int i = 1;
    		for( int pairs = 0; pairs < qst; i++ ) {
    			if( present[ i ] == 0)
    				present[ pairs++ ] = i;
    		}
    		for( i = 0; i < qst; i++) {
    			int old_id = stranges[ i ].id;
    			stranges[ i ].id = present[ i ];
    			for( Vertex v1 : vertex_set.values() ) {
    				if( v1.nbhood.get( old_id ) != null ) {
    					v1.nbhood.remove( old_id );
    					v1.nbhood.put( stranges[ i ].id, stranges[ i ] );
    				}
    			}
    		}
    }


    public int max_degree() {
        // feito
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
        // feito
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
        return true;
    }

    public Graph subjacent() {
        // feito
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

    public int count_components() {
		  // fazer
		  return -1;
	  }

    public void BFS( Integer id_raiz ) {
        Vertex raiz = vertex_set.get( id_raiz ); //uma raiz local ao método
        // feito
        raiz.dist = 0; //distancia inicial é zero pra qualquer outro vértices
                       //porque ainda não começou a contar nada;

        Queue<Vertex> lista = new LinkedList<Vertex>();//cria uma lista
        lista.add( raiz ) ; //o primeiro a ser posto na fila (não sei por que ele chamou de lista)

        Vertex atual; //vértice que está sendo visitado

        while ((atual = lista.poll()) != null) {
          for (Vertex viz : atual.nbhood.values() ){
            if( viz.dist == null ){
              viz.discover( atual );
              lista.add( viz );
            }
          }
        }
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
