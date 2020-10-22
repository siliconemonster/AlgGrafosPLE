import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.List;

public class AlgGrafos {

    static Digraph dg1;

    public static void main(String args[ ]) {
        dg1 = new Digraph();
        main_menu( );
    }

    private static void main_menu( ) {

        Scanner scan1 = new Scanner(System.in);

        String line1 = "\n\n 0 Sair \n 1 Print \n 2 Ler de arquivo \n 3 Escrever em arquivo \n 4 Grafo padrão";
        String line2 = "\n 6 Excluir vértice \n 7 Entrada arquivo texto \n 8 Algoritmos \n Escolha a opção: ";
        String menu = line1 + line2;

        boolean goon = true;

        while( goon ) {
            System.out.printf( menu );
            int choice = scan1.nextInt();
            switch( choice ) {
                case 0:
                    goon = false;
                    break;
                case 1:
                    dg1.print();
                    break;
                case 2:
                    dg1 = read( );
                    break;
                case 3:
                    write( dg1 );
                    break;
                case 4:
                    // fazer
                    dg1.add_vertex( 1 );
                    dg1.add_vertex( 2 );
                    dg1.add_vertex( 3 );
                    dg1.add_vertex( 4 );
                    // fazer
//                    g1.add_edge(1,2);
//                    g1.add_edge(1,3);
                    // fazer: criar uma opção
                    dg1.add_arc(1,2,-1);
                    dg1.add_arc(1,3,3);
                    dg1.add_arc(2,3,3);
                    dg1.add_arc(1,4,1);
                    dg1.add_arc(3,1,2);
                    break;
                case 6:
                    System.out.print("Vértice a excluir: ");
                    int id = scan1.nextInt();
                    dg1.del_vertex( id );
                    break;
                case 7:
                    FileGraph fg1 = new FileGraph( );
                    dg1 = fg1.open_text( );
                    break;

                case 8:
                    algorithms( );
            }
        }
    }

    private static void algorithms( ) {
        Scanner scan1 = new Scanner(System.in);

        String line1 = "\n\n 0 Sair \n 1 Print \n 7 BFS \n 8 Subjacente \n 9 Compactar";
        String line2 =  "\n 10 DFS \n 11 Ordenação topológica \n 12 Reverter arcos \n 13 CFC";
        String line3 = "\n 14 Bipartido \n 15 Comp. biconexas \n 16 Bellman Ford \n Escolha a opção: ";
        String menu = line1 + line2 + line3;

        boolean goon = true;

        while( goon ) {
            System.out.printf( menu );
            int choice = scan1.nextInt();
            switch( choice ) {
                case 0:
                    goon = false;
                    break;
                case 1:
                    dg1.print();
                    break;
                case 7:
                    // fazer
                    dg1.BFS( 4 );
                    break;
                case 8:
                    Graph g2 = dg1.subjacent( );
                    g2.print( );
                    break;
                case 9:
                    dg1.compact( );
                    break;
                case 10:
                    dg1.DFS( null );
                    break;
                case 11:
                    List<Vertex> ts_vertex_set = dg1.topological_sorting( );
                    System.out.printf("\n\n Ordenação topológica \n");
                    for ( Vertex v1 : ts_vertex_set )
                        System.out.printf("\n id: " + v1.id + " f: " + v1.f );
                    break;
                case 12:
                    Digraph d2 = dg1.reverse( );
                    d2.print( );
                    break;
                case 13:
                    dg1.CFC( );
                    break;
                case 14:
                    dg1.is_bipartite( );
                    break;
                case 15:
                    g2 = dg1.subjacent( );
                    g2.bicon_comp( );
                    break;
                case 16:
                    dg1.Bellmann_Ford( 1 );
            }
        }
    }

    private static void write( Digraph g1 ) {
        try {
            FileOutputStream arquivoGrav = new FileOutputStream("myfiles/saida.dat");
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
            objGravar.writeObject( g1 );
            objGravar.flush();
            objGravar.close();
            arquivoGrav.flush();
            arquivoGrav.close();
            System.out.println("Objeto gravado com sucesso!");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static Graph read( ) {
        Graph g1 = null;
        try
        {
            FileInputStream arquivoLeitura = new FileInputStream("myfiles/saida.dat");
            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
            g1 = (Graph) objLeitura.readObject();
            objLeitura.close();
            arquivoLeitura.close();
            System.out.println("Objeto recuperado: ");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return g1;
    }
}
