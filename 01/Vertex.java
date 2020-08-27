import java.util.HashMap;

public class Vertex {
    protected Integer id;
    protected HashMap<Integer,Vertex> nbhood;

    public Vertex ( int id ) {
        this.id = id;
        nbhood = new HashMap<Integer,Vertex>();
    }

    public void add_neighbor( Vertex viz ) {
        nbhood.put(viz.id, viz);
    }

    public int degree() {
        // grau de saída se direcionado
        return nbhood.size();
    }

    public void print() {
        System.out.print("\nId do vertice = " + id + ", Vizinhança: " );
        for( Vertex v : nbhood.values())
            System.out.print(" " + v.id );
    }
}
