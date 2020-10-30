import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

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
                System.out.print( "\n Não é bipartido " + v1.id + " " + v2.id );
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
    // Método do trabalho 2:
    // Em um grafo não direcionado, um emparelhamento (ou acomplamento) é um conjunto de arestas tal que cada
    // duas arestas não são incidentes, ou seja, não existem duas arestas nesse conjunto compartilhando vértices.
    // Dado um grafo G, um acoplamento M é subconjunto de arestas de G tal qual todo vértice em G é extremo de,
    //  no máximo, uma aresta em M. Um acoplamento M em G é um conjunto arestas não-adjacentes par-a-par; ou
    //  seja, duas arestas de M não compartilham um mesmo vértice.
    //
    // Um emparelhamento máximo é o conjunto de maior tamanho de emparelhamento do grafo.
    // Como é um conjunto par a par, posso criar um Pair<aresta1, aresta2> ?????????
    //
    // A ideia do algoritmo:
    // imprimir o emparelhamento máximo do grafo dado.
    //
    //
    // Fontes usadas para pesquisa:
    //    https://pt.wikipedia.org/wiki/Acoplamento_(teoria_dos_grafos)
    //
    //
    //

    public void emparelhamentoMax(){ //pode ser void mesmo, só desejo imprimir o emparelhamento máximo no final
        ArrayList<Set<Vertex>> arestas = new ArrayList<>(); //conjunto de pares de vértices para representar as arestas

        if(!this.is_undirected()){
            System.out.print("\n\nO grafo inserido é direcionado. Por favor insira um grafo não-direcionado.\n");
            return;
        }

        salvaArestas(arestas);

        checaIncidencia(arestas);

    }

    private void salvaArestas(ArrayList<Set<Vertex>> arestas){
        for(Vertex v: vertex_set.values()){ //para cada vértice v
            for(HashMap.Entry<Integer, Vertex> viz: v.nbhood.entrySet()){ //para cada vizinho do vértice v
                if(arestas.contains(Set.of(v,viz.getValue()))){ //se a aresta já tiver sido adicionada ( [2,1] = [1,2]), pula
                    continue;
                }
                arestas.add(Set.of(v,viz.getValue())); // adiciona na lista de arestas
                //System.out.println(Set.of(v,viz.getValue()).equals(Set.of(viz.getValue(),v)));
            }
        }
        //imprimeArestas(arestas);
    }

    private void checaIncidencia(ArrayList<Set<Vertex>> arestas) {
        ArrayList<ArrayList<Set<Vertex>>> emparelhamentos = new ArrayList<>(); //Lista com o conjunto de emparelhamentos
        ArrayList<Set<Vertex>> rot = new ArrayList<>(arestas); //lista de arestas rotacionadas

        for (int i = 0; i < arestas.size(); i++) {
            ArrayList<Vertex> marcados = new ArrayList<>(); //vértices que já foram usados

            emparelhamentos.add(new ArrayList<Set<Vertex>>());
            Collections.rotate(rot, 1);

            for (Set<Vertex> aresta : rot) { //para cada aresta de arestas
                Set<Vertex> intersecao = new HashSet<Vertex>(aresta); //o método de interseção modificaria arestas, então criei uma cópia
                intersecao.retainAll(marcados); // interseção de vértices de arestas com vértices marcados
                if (!intersecao.isEmpty()) { // já existe pelo menos um vértice da aresta no marcados
                    continue;
                }
                marcados.addAll(aresta); // marca vértices da aresta
                emparelhamentos.get(i).add(aresta); //adiciona aresta no emparelhamento
            }
            //imprimeArestas(emparelhamentos.get(i));
            //imprimeArestas(rot);

        }
        comparaTamanho(emparelhamentos);

        /*for (ArrayList<Set<Vertex>> emp : conjEmparelhamentos){
            imprimeArestas(emp);
        }*/
    }

    private void comparaTamanho(ArrayList<ArrayList<Set<Vertex>>> emparelhamentos){
        int i = 0, tamanho = 0;

        for(int j = 0; j < emparelhamentos.size(); j++){
            if(emparelhamentos.get(j).size() > tamanho){
                i = j;
                tamanho = emparelhamentos.get(j).size();
            }
        }
        imprimeArestas(emparelhamentos.get(i), tamanho);
    }

    private void imprimeArestas(ArrayList<Set<Vertex>> arestas, int tamanho) {
        System.out.println("\nO emparelhamento máximo possui "+ tamanho + " arestas:");
        for(Set<Vertex> aresta: arestas){
            System.out.print("( ");
            for(Vertex v : aresta){
                System.out.print(v.id + " ");
            }
            System.out.println(")");
        }
    }
}

