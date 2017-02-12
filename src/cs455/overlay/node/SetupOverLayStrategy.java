package cs455.overlay.node;
import java.util.ArrayList;
import java.util.HashMap;

import cs455.overlay.commands.Node;

public class SetupOverLayStrategy {

	public HashMap<Node, ArrayList<Node>> SelectNodeForPeerConection(ArrayList<Node> nodeCol, int numberOfNeighbours) {
		HashMap<Node, ArrayList<Node>> easyhset = new HashMap<Node, ArrayList<Node>>();
		for(int i=0; i<nodeCol.size();i++) {
			Node node = nodeCol.get(i);
			ArrayList<Node> neighbours = new ArrayList<Node>();
			int neighbourIndex = i + 1;
			for(int j=0; j < numberOfNeighbours; j++) {
				neighbourIndex = (neighbourIndex >= nodeCol.size()) ? 0 : neighbourIndex;
				Node neighbourNode = nodeCol.get(neighbourIndex);
				if(neighbourIndex != i && !neighbours.contains(neighbourNode)) {
					neighbours.add(neighbourNode);
				}
				neighbourIndex++;
			}
			easyhset.put(node, neighbours);
		}
		return easyhset;
	}
	
//	public static void main(String[] args) {
//		Node m1 = new Node("127.0.0.1", "A", 1);
//		Node m2 = new Node("127.0.0.1", "B", 2);
//		Node m3 = new Node("127.0.0.1", "C", 3);
//		Node m4 = new Node("127.0.0.1", "D", 4);
//		Node m5 = new Node("127.0.0.1", "E", 5);
//		Node m6 = new Node("127.0.0.1", "F", 6);
//		Node m7 = new Node("127.0.0.1", "G", 7);
//		Node m8 = new Node("127.0.0.1", "H", 8);
//		Node m9 = new Node("127.0.0.1", "I", 9);
//		Node m10 = new Node("127.0.0.1", "J", 10);
//		Node m11 = new Node("127.0.0.1", "K", 11);
//		
//		List<Node> asList = Arrays.asList(m1, m2, m3, m4, m5, m6,m7, m8, m9, m10, m11);
//		HashMap<Node, ArrayList<Node>> a = new SetupOverLayStrategy().SelectNodeForPeerConection(new ArrayList<>(asList), 3);
//		Set<Node> keySet = a.keySet();
//		for (Node node : keySet) {
//			System.out.println(node);
//			System.out.println(a.get(node));
//			System.out.println();
//			System.out.println();
//		}
//		
//	}
	
	
	
//	public HashMap<String, ArrayList<Node>> SelectNodeForPeerConection(ArrayList<Node> nodeCol) {
//		int peer = 0;
//		String strPeerNode = "";
//		int increment = 1;
//
//		
//		
//		for (int m = 0; m < 2; m++) {
//			for (int x = 0; x < nodeCol.size(); x++) {
//				ArrayList<Node> neighbours = new ArrayList<Node>();
//				peer = x;
//				strPeerNode = nodeCol.get(peer).Node_name;
//
//				if (m > 0) {
//					// neighbours = easyhset.get(peer);
//					// System.out.println(neighbours);
//					neighbours = easyhset.get(strPeerNode);
//				}
//
//				int neighbour = peer + increment;
//
//				if (neighbour < nodeCol.size())
//					// neighbours.add();
//					neighbours.add(nodeCol.get(neighbour));
//				else {
//					neighbour = increment;
//					if (m > 0 && neighbour < nodeCol.size())
//						neighbours.add(nodeCol.get(neighbour));
//					else
//						neighbours.add(nodeCol.get(0));
//				}
//				easyhset.put(strPeerNode, neighbours);
//			}
//			increment += 1;
//		}
//		return easyhset;
//
//	}

	/*
	 * FOR TESTING public static void main(String[] args) { // TODO
	 * Auto-generated method stub
	 * 
	 * nodeCollection = new ArrayList<Node>();
	 * 
	 * Node nodeA = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 2210;
	 * 
	 * Node nodeB = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 3340;
	 * 
	 * Node nodeC = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 7020;
	 * 
	 * Node nodeD = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 4060;
	 * 
	 * Node nodeE = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 2090;
	 * 
	 * Node nodeF = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 2056;
	 * 
	 * Node nodeG = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 2058;
	 * 
	 * Node nodeH = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 2067;
	 * 
	 * Node nodeI = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 2095;
	 * 
	 * Node nodeJ = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 2097;
	 * 
	 * Node nodeK = new Node(); nodeA.IP_address = "127.0.0.1"; nodeA.Port =
	 * 2099;
	 * 
	 * nodeCollection.add(nodeA); nodeCollection.add(nodeB);
	 * nodeCollection.add(nodeC); nodeCollection.add(nodeD);
	 * nodeCollection.add(nodeE);
	 * 
	 * nodeCollection.add(nodeF); nodeCollection.add(nodeG);
	 * nodeCollection.add(nodeH); nodeCollection.add(nodeJ);
	 * nodeCollection.add(nodeK);
	 * 
	 * SelectNodeForPeerConection(nodeCollection);
	 * 
	 * for (Integer key : easyhset.keySet()) { System.out.println("peer = " +
	 * key);
	 * 
	 * } for (ArrayList<Integer> value : easyhset.values()) {
	 * System.out.println("Neighbours = " + value); } // Iterating over values
	 * only
	 * 
	 * ArrayList<Integer> xx = easyhset.get(0); //System.out.println(xx); }
	 */

}
