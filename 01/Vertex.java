import java.io.Serializable;
import java.util.HashMap;

public class Vertex implements Serializable {
    protected Integer id;
    protected HashMap<Integer,Vertex> nbhood;
    protected Vertex parent;
    protected Integer dist;

    public Vertex ( int id ) {
		// id >= 1
        this.id = id;
        nbhood = new HashMap<Integer,Vertex>();
        parent = null;
        dist = null;
    }

    public void add_neighbor( Vertex viz ) {
        nbhood.put(viz.id, viz);
    }

    public int degree() {
        // grau de saída se direcionado
        return nbhood.size();
    }

    public void discover( Vertex parent ) {
        this.parent = parent;
        this.dist = parent.dist + 1;
    }

    public void print() {
        System.out.print("\nId do vértice " + id + ", Vizinhança: " );
        for( Vertex v : nbhood.values())
            System.out.print(" " + v.id );
        if( parent != null)
            System.out.print(", pai " + parent.id + " distância " + dist );
        else if ( dist == null )
            System.out.print(", não alcançável pela raiz");
        else
            System.out.print(", raiz, distância" + dist);
    }
}
