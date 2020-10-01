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
    //private ArrayList<ArrayList<Vertex>> conjSubgrafos; //lista de conjuntos de partes de subgrafos

    public Graph() {
    	st1 = new Stack<Vertex>();
    	//conjSubgrafos = new ArrayList<ArrayList<Vertex>>();
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

  // Método do trabalho 1:
  // Um grafo g é de distância hereditária se é conexo e, para qualquer subgrafo induzido dele,
  // a distância entre dois vértices desse subgrafo é a mesma, no subgrafo e no grafo original.
  //
  // A ideia do algoritmo:
  // Conseguir encontrar todos os subgrafos induzidos desse grafo e comparar todas as distâncias.
  // Inicialmente fazer uma BFS/DFS no grafo original para poder guardar as distâncias de cada vértice
  //
	/*

        1   2   3   7
    1[  0   1   1   0  ]
    2[  1   0   0   0  ]
    3[  1   0   0   0  ]
    7[  0   0   0   0  ]

    1 -> 2, 3
    2 -> 1
    3 -> 1
    7 ->

    Matriz[2][1]

    */
  public void isDistanceHereditary(){
  	ArrayList<Graph> subgrafos;

    distGrafo(); //fazer bfs ou dfs do grafo original para descobrir as distâncias
    enumerate(, 0);
    subgrafos = link();
    removeSubgrafo(subgrafos);//se o subgrafo for desconexo, sai do conjunto
    //fazer bfs ou dfs de cada subgrafo          <1,2,1> <1,3,2>...<7,1,1>
    comparaDistancia(); // comparar distâncias

  }

  private void distGrafo(){
  	ArrayList<HashMap<Integer, Integer>> distancia; //talvez não possa ser um arraylist
  	for(i = 0; i <= vertex_set.size; i++){
  		distancia[i] = BFS(i);
	}
  	return distancia;
  }

  private void enumerate(ArrayList<Vertex> vSet, int ind){
    if ind == vertex_set.size() {
      System.out.print();
      return;
    }

    enumerate(vSet, ind+1);
    ArrayList<Vertex> newVset = vSet + vertex_set[ind];
    enumerate(newVset, ind+1);
  }

	ArrayList<Pair<vertice,vertice>> listaAdjacencias;
  private void link(){
    //juntar as arestas e gerar cada subgrafo
	  ArrayList<Graph> subgrafos;
	 for (passar por cada lista de vertices ){ //linha incompleta
	 	Graph newSubgraph = new Graph();
	 	for(para cada vertice ){ //linha incompleta
	 		newSubgraph.add_vertex(vertice.i);
		}
		for (tamanho da lista) {
			  if (vertice do grafo esta no subgrafo){  //linha incompleta
				  aresta do vertice do subgrafo = newSubgraph.add_edge(); //linha incompleta
			  }
			  //outra ideia
			  if (na lista de adjacencias checar se o par está no subgrafo){ //linha incompleta
				aresta do vertice do subgrafo = newSubgraph.add_edge(); //linha incompleta
			}
		}
	 }
  }
  private void removeSubgrafo(ArrayList<Graph> subgrafos){
  	for(i = 0; i <= subgrafos.size; i++){
  		if(!subgrafos[i].is_Connected){
  			remove do conjunto //linha incompleta
		}
	}
  }
  comparaDistancia(){
  	//
	  for(i = 0; i >= subgrafos.size; i++){
		  if(g1.d == subgrafo[i].d){

		  }
	  }
  }


/*  // Para um grafo ser de distância hereditária:
  // * todo caminho é ou o menor deles, ou ele precisa ter pelo menos uma aresta conectando dois
  // vértices cujo caminho é não consecutivo;
  // * todo ciclo de tamanho 5 ou maior tem pelo menos duas diagonais que se cruzam;
  // * para cada grafo com 4 vértices u, v, w, and x, pelo menos duas das três somas das distâncias
  // d(u,v)+d(w,x), d(u,w)+d(v,x), and d(u,x)+d(v,w) são iguais uma a outra;
  // * não tem, como um subgrafo isométrico, nenhum ciclo de tamanho 5 ou maior, ou nenhum dos 3 grafos:
  //          um grafo de ciclo de 5 vértices com uma corda, um grafo de ciclo de 5 vértices com duas
  //          cordas que não se cruzam, e um grafo de ciclo de 6 vértices com uma corda conectando vértices
  //          opostos;
  //  * pode ser construído de um único vértice com uma sequência das três operações:
  //          1 - Adicionar um novo vértice conectado por uma aresta a um vértice existente do grafo,
  //          2 - Substituir qualquer vértice do grafo por um par de vértices, onde cada um deles tem os mesmos
  //          vizinhos que o vértice substituído. Esses novos vértices são chamados de "false twins",
  //          3 - Substituir qualquer vértice do grafo por um par de vértices, onde cada um deles tem os mesmos
  //          vizinhos não apenas que o vértice substituído, mas também que o outro vértice do par. Esses vértices
  //          são chamados de "true twins";
  // * pode ser completamente decomposto em cliques e estrelas por uma decomposição "split";
  // * tem a mínima partição hierárquica (1);
  // * tem uma caracterização proibida. Nenhum subgrafo induzido pode ser uma "house" (grafo complementar de um grafo
  // com caminho de 5 vértices), um "hole" (grafo de ciclo de 5 ou mais vértices), um domino" (grafo com ciclo de 6
  // vértices mais uma aresta diagonal entre dois vértices opostos), ou uma "gem" (um ciclo de 5 vértices mais duas
  // arestas diagonais que incidem no mesmo vértice).
  //
  // Fontes usadas para pesquisa:
  //    https://www.graphclasses.org/classes/gc_80.html
  //    https://en.wikipedia.org/wiki/Distance-hereditary_graph
  //    https://core.ac.uk/download/pdf/82240981.pdf
  public boolean isDistanceHereditary(){
    //precisa cumprir os 8 requisitos acima
    if (is_connected()){

      if(!MenorCaminhoOuAresta() ?????????? ){
        return false;
      }

      if(!acyclic() && ??????????){
        return false;
      }

      if(){
        return false;
      }

      if(){
        return false;
      }

      if(){
        return false;
      }

      //clique star
      if(){
        return false;
      }

      if(){
        return false;
      }

      if (HHDG()){
        return false;
      }

      return true;
    }
    return false;
  }

  //Checa se o grafo tem alguma dessas caracterizações proibidas.
  //Ter uma delas já é o suficiente para o problema em questão.
  private boolean HHDG(){
    //HOUSE = grafo complementar de um grafo com caminho de 5 vértices
    if(){
      return true;
    }

    //HOLE = grafo cíclico de 5 ou mais vértices ?????????
    if (!acyclic() && vertex_set.size >= 5){
      return true;
    }

    //DOMINO = grafo com ciclo de 6 vértices + aresta diagonal entre dois vértices opostos
    if(!acyclic() && ??????????){
      return true;
    }

    //GEM = grafo com ciclo de 5 vértices +  2 arestas diagonais incidentes no mesmo vértice
    if(!acyclic() && ??????????){
      return true;
    }

    return false;
  }*/
}
