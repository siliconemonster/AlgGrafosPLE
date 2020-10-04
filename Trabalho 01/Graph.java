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

  public void bicon_comp( ) {
    // falta testar a raiz
    time = 0;
    for( Vertex v1 : vertex_set.values( ) )
      if( v1.d == null )
        bicon_comp_visit( v1 );
  }

  private void bicon_comp_visit( Vertex v1 ) {
    v1.d = ++time;
    v1.low = v1.d;
    for( Vertex neig : v1.nbhood.values( ) ) {
      if( neig.d == null ) {
        st1.push( v1 );
        st1.push( neig );
        neig.parent = v1;
        bicon_comp_visit( neig );
        if( neig.low >= v1.d )
          this.desempilha( v1, neig );
        if( neig.low < v1.low )
          v1.low = neig.low;
      }
      else if( neig != v1.parent ) {
        if( neig.d < v1.d ) {
          st1.push( v1 );
          st1.push( neig );
        }
      }
    }
  }

  private void desempilha( Vertex cut_vertex, Vertex aux ) {
    if( st1.empty( ) )
      return;
    System.out.println( "\n Bloco: " );
    Vertex v1 = this.st1.pop( );
    Vertex v2 = this.st1.pop( );
    System.out.println( v1.id );
    System.out.print( v2.id );
    while( v1 != cut_vertex || v2 != aux ) {
      if( st1.empty( ) )
        return;
      v1 = this.st1.pop( );
      v2 = this.st1.pop( );
      System.out.println( v1.id );
      System.out.print( v2.id );
    }
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



  public boolean isDistanceHereditary(){  //TEORICAMENTE tudo certo
  	ArrayList<Graph> subgrafos;
  	ArrayList<ArrayList<Vertex>> conjPartes = new ArrayList<ArrayList<Vertex>>();

  	//lista de distâncias: contém as distâncias de cada vértice de cada grafo (tal grafo, tal raiz, tal vértice, tal distância)
  	ArrayList<ArrayList<HashMap<Integer, Integer>>> distâncias;

    distancias[0] = distGrafo(); //fazer bfs do grafo original para descobrir as distâncias. A posição 0 é do grafo original
    conjPartes = enumerate(conjPartes, , 0); // encontra todos os conjuntos de partes possíveis
    subgrafos = link(conjPartes); //liga os conjuntos de partes e as arestas
    subgrafos = removeSubgrafo(subgrafos);//se o subgrafo for desconexo, sai do conjunto

    //fazer bfs de cada subgrafo
      for (int i = 0; i <= subgrafos.size(); i++){ //laço para cada subgrafo do conjunto
          for (int j = 0; i<= subgrafos.vertex_set.size(); i++){ //laço para cada vértice do conjunto ser a raiz da busca
              distancias[j+1] = BFS(j); //a primeira distância é a do grafo original
          }
      }
    comparaDistancia(distancias); // comparar distâncias entre os subgrafos e o grafo original


      return disHereditaria;
  }

  private void distGrafo(){  //TEORICAMENTE tudo certo
      //para cada raiz, a distância de cada vértice
  	ArrayList<HashMap<Integer, Integer>> distancia; //talvez não possa ser um arraylist
  	for(i = 0; i <= vertex_set.size; i++){
  		distancia[i] = BFS(i);
	}
  	return distancia;
  }

  private void enumerate(ArrayList<ArrayList<Vertex>> conjPartes, ArrayList<Vertex> vSet, int ind){
    if (ind == vertex_set.size()){
      System.out.print(vSet);
      conjPartes.put(vSet); //paramos aqui
      return;
    }

    enumerate(vSet, ind+1);
    ArrayList<Vertex> newVset = vSet + vertex_set[ind];
    enumerate(newVset, ind+1);
  }

  ArrayList<Pair<vertice,vertice>> listaAdjacencias; //um vétice é ligado no outro vértice por exemplo [(1, 2), (1, 3), (2, 3), (3, 7)]
  private void link(){
    //juntar as arestas e gerar cada subgrafo
	  ArrayList<Graph> subgrafos;
	 for (passar por cada lista de vertices ){ //passar por cada conjunto de partes
	 	Graph newSubgraph = new Graph();
	 	for(para cada vertice ){ //linha incompleta
	 		newSubgraph.add_vertex(vertice.i);
		}
		for (tamanho da lista) {
			  if (vertice do grafo esta no subgrafo){  //linha incompleta
				  aresta do vertice do subgrafo = newSubgraph.add_arc1(); //linha incompleta
			  }
			  //outra ideia
			  if (na lista de adjacencias checar se o par está no subgrafo){ //linha incompleta
				aresta do vertice do subgrafo = newSubgraph.add_arc1(); //linha incompleta
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
