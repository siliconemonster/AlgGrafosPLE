import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class AlgGrafos {
    public static void main(String args[]) {

        Scanner scan1 = new Scanner(System.in);

        String line1 = "\n\n 0 Sair \n 1 Print \n 2 Ler de arquivo \n 3 Escrever em arquivo \n 4 Adicionar vértice";
        String line2 = "\n 5 Adicionar aresta \n 6 Excluir vértice \n 7 BFS \n 8 Subjacente \n 9 Compactar \n Escolha a opção: ";
        String menu = line1 + line2;

        Graph g1 = new Graph();

        while( true ) {
            System.out.printf( menu );
            int choice = scan1.nextInt();
            switch( choice ) {
                case 0:
                    return;
                case 1:
                    g1.print();
                    break;
                case 2:
                    g1 = read( );
                    break;
                case 3:
                    write( g1 );
                    break;
                case 4:
					// fazer
                    g1.add_vertex( 1 );
                    g1.add_vertex( 2 );
                    g1.add_vertex( 3 );
                    g1.add_vertex( 4 );
                    break;
                case 5:
					// fazer
                    g1.add_edge(1,2);
                    g1.add_edge(1,3);

                    // fazer: criar uma opção
                    g1.add_arc(1,4);
                    break;
                case 6:
					System.out.print("Vértice a excluir: ");
					int id = scan1.nextInt();
					g1.del_vertex( id );
					break;
                case 7:
					// fazer
                    g1.BFS( 4 );
                    break;
                case 8:
                    Graph g2 = g1.subjacent();
                    g2.print();
                    break;
                case 9:
					g1.compact();
            }
        }
    }

    private static void write( Graph g1 ) {
        try
        {
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
