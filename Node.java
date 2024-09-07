
public class Node {
	private String word;
	private int occurrences;
	private Node next;
	
	public Node(String word) {
		this.word = word;
		this.occurrences = 1;
		this.next = null;
	}
	
	public Node(String word, int occurrences) {
		this.word = word;
		if(occurrences > 0) {
			this.occurrences = occurrences;
		}
		else {
			this.occurrences = 1;
		}
		this.next = null;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public void setNext(Node next) {
		this.next = next;
	}
	
	public Node getNext() {
		return this.next;
	}
	
	public void incrementOccurrences() {
		this.occurrences++;
	}
	
	public void decrementOccurrences() {
		this.occurrences--;
	}
	
	public int getOccurrences() {
		return occurrences;
	}
	//Figured it can't hurt to include these for node and linked list
	public String toString() {
		return "Word: " + word 
				+ " Occurences: " + occurrences;
	}
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(!(obj instanceof Node)) {
			return false;
		}
		Node n = (Node) obj;
		return n.getWord().equals(this.word) && n.getOccurrences() == this.occurrences;
	}
}
