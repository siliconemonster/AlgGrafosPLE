public class AlgGrafos {
    public static void main(String args[]) {

        Graph g1 = new Graph();
        g1.add_vertex( 1 );
        g1.add_vertex( 2 );
        g1.add_vertex( 3 );
        g1.add_vertex( 4 );

        g1.add_edge(1,2);
        g1.add_edge(1,3);
        g1.add_arc(1,4);

        g1.print();

        Graph g2 = g1.subjacent();
        g2.print();
    }
}
