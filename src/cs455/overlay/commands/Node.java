package cs455.overlay.commands;

public class Node {
	
	public String ipAddress ;
	public String node_name;
	public int port;
	
	public Node() {
	}

	public Node(String ipAddress, String node_name, int port) {
		this.ipAddress = ipAddress;
		this.node_name = node_name;
		this.port = port;
	}

	@Override
	public String toString() {
		return "Node [IP_address=" + ipAddress + ", Node_name=" + node_name + ", Port=" + port + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

}
