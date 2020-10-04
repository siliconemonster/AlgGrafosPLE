import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.Collections;

public class Digraph implements Serializable {
    protected HashMap<Integer,Vertex> vertex_set;
    protected int time;
    private Boolean acyclic;

    public Digraph() {

        vertex_set = new HashMap<Integer,Vertex>();
    }

    public void add_vertex( ) {
		// fazer: utilizar um id disponível
	  }

    public void add_vertex( int id ) {
        if ( this.vertex_set.get( id ) == null ) {
            Vertex v = new Vertex( id );
            vertex_set.put( v.id, v );
        }
    }

    public void add_arc( Integer id1, Integer id2) {
  		try {
  			Vertex v1 = vertex_set.get(id1);
  			Vertex v2 = vertex_set.get(id2);
  			v1.add_neighbor( v2 );
  		} catch(Exception e) {
  			this.add_vertex( id1 );
  			this.add_vertex( id2 );
  			Vertex v1 = vertex_set.get(id1);
  			Vertex v2 = vertex_set.get(id2);
  			v1.add_neighbor( v2 );
  		}
    }

    // implementação do add_arc sem try
    public void add_arc1( Integer id1, Integer id2) {
  		this.add_vertex( id1 );
          Vertex v1 = vertex_set.get(id1);
  		this.add_vertex( id2 );
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
        for( Vertex v1 : vertex_set.values() ) {
            if( v1.degree() > max )
                max = v1.degree();
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
        // fazer: se direcionado ? encontrar o subjacente
        this.BFS(2);
        for(Vertex vertice : vertex_set.values()){
            vertice.print();
            if(vertice.d == null){
                return false;
            }
        }
        return true;
    }

    public int count_components() {
		  // fazer
		  return -1;
	  }

    public HashMap<Integer, Integer> BFS( Integer id_raiz ) {
        //Hashmap que salva a distância de cada vértice à raiz
        HashMap<Integer, Integer> distancias = new HashMap<Integer, Integer>();
        Vertex raiz = vertex_set.get(id_raiz); //uma raiz local ao método
        // feito

        for (Vertex v1 : vertex_set.values()) { //resetar todas as distâncias a cada vez que rodar
            v1.d = null;
        }
        raiz.d = 0; //distancia inicial é zero pra qualquer outro vértices
        //porque ainda não começou a contar nada;

        Queue<Vertex> lista = new LinkedList<Vertex>();//cria uma lista
        lista.add(raiz); //o primeiro a ser posto na fila (não sei por que ele chamou de lista)

        Vertex atual; //vértice que está sendo visitado

        while ((atual = lista.poll()) != null) {
          for (Vertex viz : atual.nbhood.values() ){
            if( viz.d == null ){
              viz.discover( atual );
              lista.add( viz );
              distancias.put(viz.id, viz.d); //ID do vizinho da raiz, distância
            }
          }
        }
        return distancias; // retorna o hashmap que contém a distância de cada vértice à raiz
    }

    public void DFS( List<Vertex> ordering ) {
  		if( ordering == null ) {
  			ordering = new ArrayList<Vertex>( );
  			ordering.addAll( vertex_set.values( ) );
  		}
  		acyclic = true;
        time = 0;
  		for( Vertex v1 : vertex_set.values() )
  			v1.parent = null;
  		time = 0;
  		for( Vertex v1 : ordering )
  			if( v1.d == null )
  				DFS_visit( v1 );
  	}

    private void DFS_visit( Vertex v1 ) {
      v1.d = ++time;
      for( Vertex neig : v1.nbhood.values( ) ) {
        if( neig.d == null ) {
          neig.parent = v1;
          DFS_visit( neig );
        }
        else if (neig.d < v1.d)
          acyclic = false;
          // encontrar os vértices que formam esse ciclo
      }
      v1.f = ++time;
    }
    public List<Vertex> get_list_roots( ) {
      List<Vertex> list_roots = new ArrayList<Vertex>( );
      for( Vertex v1 : vertex_set.values( ) ) {
        if( v1.parent == null )
          list_roots.add( v1 );
      }
      return list_roots;
    }

    public boolean is_acyclic( ) {
      if( acyclic != null )
        return acyclic;
      DFS( null );
      return acyclic;
    }

    public List<Vertex> topological_sorting( ) {
      if( ! is_acyclic( ) ) {
        System.out.printf("\n\n O grafo contém ciclo!!");
        return null;
      }
      List<Vertex> ts_vertex_set = new ArrayList<Vertex>();
      for ( Vertex v1 : vertex_set.values( ) )
        ts_vertex_set.add( v1 );
      Collections.sort( ts_vertex_set );
      return ts_vertex_set;
    }

    public Digraph reverse( ) {
  		Digraph d2 = new Digraph( );
  		for( Vertex v11 : this.vertex_set.values( ) ) {
              d2.add_vertex( v11.id );
  		}
          for( Vertex v11 : this.vertex_set.values()) {
              for( Vertex v12 : v11.nbhood.values()) {
                  Vertex v21 = d2.vertex_set.get( v11.id );
                  Vertex v22 = d2.vertex_set.get( v12.id );
                  v22.add_neighbor( v21 );
              }
          }
  		return d2;
  	}

  	public void CFC( ) {
  		DFS( null );

  		List<Vertex> vertex_set1 = new ArrayList<Vertex>();
  		for ( Vertex v1 : vertex_set.values( ) )
  			vertex_set1.add( v1 );
  		Collections.sort( vertex_set1 );

  		Digraph d2 = this.reverse( );

  		List<Vertex> vertex_set2 = new ArrayList<Vertex>();
  		for ( Vertex v1 : vertex_set1 ) {
  			Vertex v2 = d2.vertex_set.get( v1.id );
  			vertex_set2.add( v2 );
  		}

  		d2.DFS( vertex_set2 );

  		List<Vertex> list_roots = d2.get_list_roots( );

  		for( Vertex v1 : d2.vertex_set.values( ) ) {
  			v1.root = v1.get_root( );
  			System.out.print( "\n v1 " + v1.id + " " + v1.root.id);
  		}

  		for( Vertex v1 : list_roots ) {
  			System.out.print( "\n Outra CFC: " );
  			for( Vertex v2 : d2.vertex_set.values( ) ) {
  				if( v2.root == v1 )
  					System.out.print(" " + v2.id );
  			}
  		}
  	}

    public boolean is_bipartite( ) {
  		Graph g1 = this.subjacent( );
  		if( ! g1.is_bipartite( ) )
  			return false;
  		return true;
  	}

    public void print() {
      System.out.print("\n\n -------------------------------");
      if( this.vertex_set.size() == 0 ) {
  			System.out.printf("\n\n Conjunto de vértices vazio");
  			System.out.print("\n\n -------------------------------");
  			return;
  		}

        System.out.printf("\n\n Grafo, grau máximo %d", this.max_degree());

        if( this.is_undirected() )
            System.out.println("\nNão direcionado");
        else
            System.out.println("\nDirecionado");

        for( Vertex v : vertex_set.values())
            v.print();
        System.out.print("\n\n -------------------------------");
    }
}