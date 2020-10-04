import java.io.FileReader;
import java.io.BufferedReader;

public class FileGraph {
	public Graph open_text( ) {
		String thisLine = null;
		Graph dg1 = new Graph( );
		String pieces[ ];

		try {
		    FileReader file_in = new FileReader("myfiles/entrada.txt");
		    BufferedReader br1 = new BufferedReader( file_in );
		    while ( (thisLine = br1.readLine( )) != null) {
			    // retira excessos de espaços em branco
			    thisLine = thisLine.replaceAll("\\s+", " ");
			    pieces = thisLine.split(" ");
			    int v1 = Integer.parseInt( pieces[0] );
			    dg1.add_vertex( v1 );
			    for( int i = 2; i < pieces.length; i++ ) {
   					int v2 = Integer.parseInt( pieces[ i ] );
					dg1.add_edge( v1, v2 );
				}				    
		    }       
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.print("Arquivo lido com sucesso.");
		return dg1;
	}
}

