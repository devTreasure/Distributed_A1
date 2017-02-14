package cs455.overlay.node;
import java.util.ArrayList;

import cs455.overlay.node.Graph.Vertex;


public class Dijkstra {
	   private static final Graph.Edge[] GRAPH = {
			   
	/*		   
	      new Graph.Edge("a", "b", 7),
	      new Graph.Edge("a", "c", 9),
	      new Graph.Edge("a", "f", 14),
	      new Graph.Edge("b", "c", 10),
	      new Graph.Edge("b", "d", 15),
	      new Graph.Edge("c", "d", 11),
	      new Graph.Edge("c", "f", 2),
	      new Graph.Edge("d", "e", 6),
	      new Graph.Edge("e", "f", 9),
	      */
			   
			      new Graph.Edge("a", "b", 3),
			      new Graph.Edge("a", "c", 15),
			      new Graph.Edge("a", "d", 7),
			      new Graph.Edge("a", "e", 11),
			      
			      new Graph.Edge("b", "c", 8),
			      new Graph.Edge("b", "d", 3),
			      new Graph.Edge("b", "e", 5),
			      new Graph.Edge("b", "a", 3),
			      
			      new Graph.Edge("c", "b", 8),
			      new Graph.Edge("c", "d", 6),
			      new Graph.Edge("c", "e", 4),   
			      new Graph.Edge("c", "a", 15), 
			      
			      
			      new Graph.Edge("d", "a", 7),
			      new Graph.Edge("d", "b", 3),
			      new Graph.Edge("d", "c", 6),   
			      new Graph.Edge("d", "e", 2),
			      
			      new Graph.Edge("e", "a", 11),
			      new Graph.Edge("e", "b", 5),
			      new Graph.Edge("e", "d", 2),
			      new Graph.Edge("e", "c", 4)  
	      
	      /*
			A.adjacencies = new Edge[] { new Edge(B, 3) };
			A.adjacencies = new Edge[] { new Edge(C, 5) };
			A.adjacencies = new Edge[] { new Edge(D, 7) };
			A.adjacencies = new Edge[] { new Edge(E, 1) };			
			
			B.adjacencies = new Edge[] { new Edge(A, 3) };		
			B.adjacencies = new Edge[] { new Edge(C, 8) };		
			B.adjacencies = new Edge[] { new Edge(D, 3) };
			B.adjacencies = new Edge[] { new Edge(E, 9) };		
			
			C.adjacencies = new Edge[] { new Edge(A, 5) };	
			C.adjacencies = new Edge[] { new Edge(B, 8) };
			C.adjacencies = new Edge[] { new Edge(D, 6) };		
			C.adjacencies = new Edge[] { new Edge(E, 4) };			
			
			D.adjacencies = new Edge[] { new Edge(A, 7) };
			D.adjacencies = new Edge[] { new Edge(B, 3) };
			D.adjacencies = new Edge[] { new Edge(C, 6) };
			D.adjacencies = new Edge[] { new Edge(E, 2) };		
			
			E.adjacencies = new Edge[] { new Edge(A, 1) };
			E.adjacencies = new Edge[] { new Edge(B, 9) };
			E.adjacencies = new Edge[] { new Edge(C, 4) };
			E.adjacencies = new Edge[] { new Edge(D, 2) };
			*/
			
	   };
	   private static final String START = "a";
	   private static final String END = "e";
	 
	   public static void main(String[] args) {
		   
	      Graph g = new Graph(GRAPH);
	      g.dijkstra(START);
	       ArrayList<Vertex>  str=  g.printPath(END);
	      //g.printAllPaths();
	    
		//System.out.println(START);
	    for (Vertex v : str) {
	    	
	    	//System.out.println("Printing from collection");
	    	System.out.println(v.name + " -- "  + v.dist );
			
		}
	   }
	}