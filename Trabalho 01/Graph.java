import java.io.Serializable;
import java.util.*;

public class Graph extends Digraph {

    private Stack<Vertex> st1;
    //private ArrayList<ArrayList<Vertex>> conjSubgrafos; //lista de conjuntos de partes de subgrafos

    public Graph() {
        st1 = new Stack<Vertex>();
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
    // Um grafo g é de distância hereditária se é conexo e, para qualquer subgrafo induzido conexo
    // dele, a distância entre dois vértices desse subgrafo é a mesma, no subgrafo e no grafo original.
    //
    // A ideia do algoritmo:
    // Conseguir encontrar todos os subgrafos induzidos desse grafo e comparar todas as distâncias.
    // Inicialmente fazer uma BFS no grafo original para poder guardar as distâncias de cada vértice
    //
    // Fontes usadas para pesquisa:
    //    https://www.graphclasses.org/classes/gc_80.html
    //    https://en.wikipedia.org/wiki/Distance-hereditary_graph
    //    https://core.ac.uk/download/pdf/82240981.pdf
    //    https://slideplayer.com/slide/4734042/

    public boolean isDistanceHereditary(){
        ArrayList<Graph> subgrafos;
        ArrayList<ArrayList<Vertex>> conjPartes = new ArrayList<ArrayList<Vertex>>();
        boolean distHereditaria;

        //lista de distâncias: contém as distâncias de cada vértice de cada grafo (tal grafo, tal raiz, tal vértice, tal distância)
        ArrayList<HashMap<Integer, HashMap<Integer, Integer>>> distancias = new ArrayList<>();
        verticeList.addAll(vertex_set.values()); // lista para facilitar a medição da quantidade de vértices no método enumerate

        if(!this.is_undirected()){
            System.out.print("\n\nO grafo inserido é direcionado.\nUm grafo de distância hereditária precisa ser não direcionado.\n");
            return false;
        }
        if(!this.is_connected()){
            System.out.print("\n\nO grafo inserido não é conexo.\nUm grafo de distância hereditária precisa ser conexo.\n");
            return false;
        }
        System.out.print("\nO grafo é conexo.\n");

        distancias.add(distGrafo(this)); //fazer bfs do grafo original para descobrir as distâncias. A posição 0 é do grafo original
        /*for(HashMap.Entry<Integer, HashMap<Integer, Integer>> d : distancias.get(0).entrySet()){
            System.out.print(d.getKey() + " - ");
            for(HashMap.Entry<Integer ,Integer> v : d.getValue().entrySet()){
                System.out.print("(" + v.getKey() + ", " + v.getValue() + ") ");
            }
            System.out.println();
        }*/

        enumerate(conjPartes, new ArrayList<Vertex>(), 0); // encontra todos os conjuntos de partes possíveis
        /*for(ArrayList<Vertex> v : conjPartes){
            for(Vertex v1: v){
                System.out.print(v1.id + " ");
            }
            System.out.println();
        }*/
        subgrafos = link(conjPartes); //liga os conjuntos de partes e as arestas
        removeSubgrafo(subgrafos);//se o subgrafo for desconexo, sai do conjunto

        //fazer bfs de cada subgrafo
        for (int i = 0; i < subgrafos.size(); i++){ //laço para cada subgrafo do conjunto
            distancias.add(distGrafo(subgrafos.get(i)));
            /*for(HashMap.Entry<Integer, HashMap<Integer, Integer>> d : distancias.get(i+1).entrySet()){
                System.out.print(d.getKey() + " - ");
                for(HashMap.Entry<Integer ,Integer> v : d.getValue().entrySet()){
                    System.out.print("(" + v.getKey() + ", " + v.getValue() + ") ");
                }
                System.out.println();
            }
            System.out.println("--------------------");*/
        }

        distHereditaria = comparaDistancia(subgrafos, distancias); // comparar distâncias entre os subgrafos e o grafo original


        return distHereditaria;
    }

    private HashMap<Integer, HashMap<Integer, Integer>> distGrafo(Graph grafo){
        //para cada raiz, a distância de cada vértice
        HashMap<Integer, HashMap<Integer, Integer>> distancia = new HashMap<>();
        for(int i : grafo.vertex_set.keySet()){
            distancia.put(i, grafo.BFS(i)); //calcula a distância de cada raiz utilizando a BFS
        }
        return distancia;
    }

    //método recursivo para encontrar todos os conjuntos possíveis de vertíces dos futuros subgrafos
    private void enumerate(ArrayList<ArrayList<Vertex>> conjPartes, ArrayList<Vertex> vSet, int ind){
        if (ind == this.verticeList.size()){ //se o índice for o último
            /*System.out.print("[ ");
            for(Vertex v : vSet){
                System.out.print(v.id + " ");
            }
            System.out.println("]");*/
            conjPartes.add(vSet); //adiciona ao conjunto de partes do futuro subgrafo
            return;
        }

        enumerate(conjPartes, vSet, ind+1); //chama novamente o método
        ArrayList<Vertex> newVset = new ArrayList<>(vSet); // quando o método rodar pela primeira vez em cada índice (até, na recursão, conseguir entrar no if a primeira vez), vSet estará vazio o tempo todo
        newVset.add(this.verticeList.get(ind)); //adicionando o vértice de índice ind no novo conjunto (o novo conjunto pegará o vSet original e adicionará novos elementos (?)
        enumerate(conjPartes, newVset, ind+1); //chama novamente o método, mas dessa vez com o novo conjunto
    }

    private ArrayList<Graph> link(ArrayList<ArrayList<Vertex>> conjPartes){
        //juntar as arestas e gerar cada subgrafo
        ArrayList<Graph> subgrafos = new ArrayList<Graph>();
        for (ArrayList<Vertex> combVertices : conjPartes){ //passar por cada conjunto de partes
            Graph newSubgraph = new Graph();
            for(Vertex vertice : combVertices){ //passar por cada vértice da combinação de vértices
                newSubgraph.add_vertex(vertice.id); //adicionar ao subgrafo novo
            }
            for (Vertex vertice : combVertices) { //passar por cada vértice da combinação de vértices novamente pois preciso que todos os vértices já tenham sido adicionados
                HashMap<Integer,Vertex> vizinhancaOrig = this.vertex_set.get(vertice.id).nbhood; //peguei a vizinhança do vértice em questão (no for)
                for(HashMap.Entry<Integer, Vertex> viz : vizinhancaOrig.entrySet()){
                    if (newSubgraph.vertex_set.containsKey(viz.getKey())){  //checa se viz está no subgrafo
                        newSubgraph.add_arc1(vertice.id, viz.getKey()); //adiciona arco ao subgrafo
                    }
                }
            }
            newSubgraph.verticeList.addAll(newSubgraph.vertex_set.values()); //para checar se é conexo
            //newSubgraph.print();
            subgrafos.add(newSubgraph);
        }
        return subgrafos;
    }
    private void removeSubgrafo(ArrayList<Graph> subgrafos) {
        //remove de subgrafos o grafo atual caso ele não seja conexo
        subgrafos.removeIf(grafoAtual -> grafoAtual.verticeList.isEmpty()); //remove o conjunto vazio
        subgrafos.removeIf(grafoAtual -> !grafoAtual.is_connected());
        /*for(Graph g : subgrafos){
            g.print();
        }*/
    }
    private boolean comparaDistancia(ArrayList<Graph> subgrafos, ArrayList<HashMap<Integer, HashMap<Integer, Integer>>> distanciaGeral){
        //
        HashMap<Integer, HashMap<Integer, Integer>> distanciasOrig = distanciaGeral.get(0); //uma variável para salvar a distância de cada vértice a cada raiz do grafo original

        for(int i = 1; i < distanciaGeral.size(); i++){ //passar por cada subgrafo induzido
            HashMap<Integer, HashMap<Integer, Integer>> distanciasSubgrafo = distanciaGeral.get(i); //uma variável para salvar a distância de cada vértice a cada raiz dos subgrafosgrafos

            for(HashMap.Entry<Integer, HashMap<Integer,Integer>> distVertices : distanciasOrig.entrySet()){ //passar por cada vértice do grafo original
                if(!distanciasSubgrafo.containsKey(distVertices.getKey())){ //se a raiz não estiver no subgrafo induzido, passa para a próxima iteração do laço acima
                    continue;
                }
                for(HashMap.Entry<Integer, Integer> distRaiz : distVertices.getValue().entrySet()){
                    if(!distanciasSubgrafo.get(distVertices.getKey()).containsKey(distRaiz.getKey())){ //se o vértice da listagem de distâncias comparadas não estiver no subgrafo induzido, passa para a próxima iteração do laço acima
                        continue;
                    }
                    if(!distRaiz.getValue().equals(distanciasSubgrafo.get(distVertices.getKey()).get(distRaiz.getKey()))){ //compara se o valor da distância é igual
                        //NÃO É DE DISTANCIA HEREDITÁRIA
                        System.out.print("\nO grafo não é de Distância Hereditária:\nPerceba que a distância do subgrafo é diferente da do grafo original:\n");
                        System.out.println("Subgrafo induzido:");
                        imprimeDistancia(distanciasSubgrafo);
                        System.out.println("\nGrafo original:");
                        imprimeDistancia(distanciasOrig);
                        return false;
                    }
                }
            }
        }
        System.out.println("\n O grafo é de Distância Hereditária.");
        return true;
    }

    private void imprimeDistancia(HashMap<Integer, HashMap<Integer, Integer>> distancias) {
        for(HashMap.Entry<Integer, HashMap<Integer, Integer>> d : distancias.entrySet()){
            System.out.print(d.getKey() + " - ");
            for(HashMap.Entry<Integer ,Integer> v : d.getValue().entrySet()){
                System.out.print("(" + v.getKey() + ", " + v.getValue() + ") ");
            }
            System.out.println();
        }
    }
}
