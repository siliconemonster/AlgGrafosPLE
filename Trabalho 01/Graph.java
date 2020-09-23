import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.Stack;

public class Graph extends Digraph {

    private Stack<Vertex> st1;

    public Graph() {
    st1 = new Stack<Vertex>( );
    }

    @Override public void add_arc( Integer id1, Integer id2) {
		System.out.println("Operação não permitida: Adição de arco.");
     }

	@Override public boolean is_acyclic( ) {
		// fazer
		return true;
	}
  @Override public boolean is_bipartite( ) {
		for( Vertex v1 : vertex_set.values( ) ) {
			if( v1.ind_set == 0 ) {
				v1.ind_set = 1;
				if( ! bipartite_visit( v1 ) )
					return false;
			}
		}
		System.out.print( "\n É bipartido " );
		return true;
	}
	private boolean bipartite_visit( Vertex v1 ) {
		for( Vertex v2 : v1.nbhood.values( ) ) {
			if( v2.ind_set == 0 ) {
				v2.ind_set = - v1.ind_set;
				if( ! bipartite_visit( v2 ) )
					return false;
			}
			if( v2.ind_set == v1.ind_set ) {
				// encontrar um ciclo ímpar
				System.out.printf( "\n Não é bipartido " + v1.id + " " + v2.id );
				return false;
			}
		}
		return true;
	}

    GRAFO CORDAL

  // Método do trabalho 1:
  // Um grafo g é de distância hereditária se para qualquer subgrafo induzido dele,
  // a distância entre dois vértices desse subgrafo é a mesma, no subgrafo e no grafo original.
  //
  // Para um grafo ser de distância hereditária:
  // * todo caminho é ou o menor deles, ou ele precisa ter pelo menos uma aresta conectando dois
  // vértices cujo caminho é não consecutivo;
  // * todo ciclo de tamanho 5 ou maior tem pelo menos duas diagonais que se cruzam;
  // * para cada grafo com 4 vértices u, v, w, and x, pelo menos duas das três somas das distâncias
  // d(u,v)+d(w,x), d(u,w)+d(v,x), and d(u,x)+d(v,w) são iguais uma a outra;
  // * não tem, como um subgrafo isométrico, nenhum ciclo de tamanho 5 ou maior, ou nenhum dos 3 grafos:
  //          um grafo de ciclo de 5 vértices com uma corda, um grafo de ciclo de 5 vértices com duas
  //          cordas que não se cruzam, e um grafo de ciclo de 6 vértices com uma corda conectando vértices
  //          opostos;
  //  * pode ser construído
//  They are the graphs that can be built up from a single vertex by a sequence of the following three operations, as shown in the illustration:
//Add a new pendant vertex connected by a single edge to an existing vertex of the graph.
//Replace any vertex of the graph by a pair of vertices, each of which has the same set of neighbors as the replaced vertex. The new pair of vertices are called false twins of each other.
//Replace any vertex of the graph by a pair of vertices, each of which has as its neighbors the neighbors of the replaced vertex together with the other vertex of the pair. The new pair of vertices are called true twins of each other.
  // * pode ser completamente decomposto em cliques e estrelas por uma decomposição "split";
  //
//They are the graphs that have rank-width one, where the rank-width of a graph is defined as the minimum, over all hierarchical partitions of the vertices of the graph, of the maximum rank among certain submatrices of the graph's adjacency matrix determined by the partition.[6]
//They are the HHDG-free graphs, meaning that they have a forbidden graph characterization according to which no induced subgraph can be a house (the complement graph of a five-vertex path graph), hole (a cycle graph of five or more vertices), domino (six-vertex cycle plus a diagonal edge between two opposite vertices), or gem (five-vertex cycle plus two diagonals incident to the same vertex).
  public isDistanceHereditary(){

  }
}
