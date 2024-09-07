
public class LinkedList {
	private Node head;
	private int size;
	
	public LinkedList() {
		this.head = null;
		this.size = 0;
	}
	
	public LinkedList(Node node) {
		this.head = node;
		this.size = 1;
	}
	
	public int getSize() {
		return size;
	}
	
	public void add(String word) {
		//Only keeps alphabetical and numerical and whitespace, makes more sense for processing real text from a web page/book/file 
		word = word.replaceAll("[^\\sa-zA-Z0-9]", "");
		Node newNode = new Node(word);
		//Adds to the front if head is null, or if its the "least valuable" word (first element if sorted)
		if(head == null || word.compareTo(head.getWord()) < 0) {
			newNode.setNext(head);
			head = newNode;
			size++;
		}
		//If it's the head word then increment the occurrence
		else if(word.equals(head.getWord())) {
			head.incrementOccurrences();
		}
		//If it's not the first word in the sorted list
		else {
			//Check if the word is already in the list 
			Node current = head;
			while(current != null) {
				if(current.getWord().equals(word)){
					current.incrementOccurrences();
					return;
				}
				current = current.getNext();
			}
			//Reset to head node
			current = head;
			//Search and find the correct space
			while((current.getNext() != null && word.compareTo(current.getNext().getWord()) > 0)) {
				current = current.getNext();
			}
			//Insert the node into the list once the correct spot is found
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			size++;
		}
	}
	//Overriding add to also be able to add by passing a node
	public void add(Node node) {
		//Check if that specific node is already in the list (causes problems when it's the same 
		Node current = head;
		while(current != null) {
			//SPECIFICALLY checking if it's the same address, if it has the same contents I don't care. the logic below handles that case without adding the same node twice.
			if(current == node) {
				return;
			}
			current = current.getNext();
		}
		String word = node.getWord();
		word = word.replaceAll("[^\\sa-zA-Z0-9]", "");
		if(head == null || word.compareTo(head.getWord()) < 0) {
			node.setNext(head);
			head = node;
			size++;
		}
		else if(word.equals(head.getWord())) {
			//Increments by the amount of time the word has occurred according to the node passed as a parameter
			for(int i = 0; i < node.getOccurrences(); i++) {
				head.incrementOccurrences();
			}
		}
		else {
			current = head;
			while(current != null) {
				if(current.getWord().equals(word)){
					node.getOccurrences();
					for(int i = 0; i < node.getOccurrences(); i++) {
						current.incrementOccurrences();
					}
					return;
				}
				current = current.getNext();
			}
			//Reset to head node
			current = head;
			//Search and find the correct space
			while((current.getNext() != null && word.compareTo(current.getNext().getWord()) > 0)) {
				current = current.getNext();
			}
			//Insert the node into the list once the correct spot is found
			node.setNext(current.getNext());
			current.setNext(node);
			size++;
		}
	}
	public int search(String word) {
		word = word.replaceAll("[^\\sa-zA-Z0-9]", "");
		//Linear search 
		Node current = head;
		while(current != null) {
			if(current.getWord().equals(word)) {
				return current.getOccurrences();
			}
			current = current.getNext();
		}
		return 0;
	}
	
	public boolean delete(String word, boolean deleteOneOccurence) {
		word = word.replaceAll("[^\\sa-zA-Z0-9]", "");
		//Check if head node is null
		if(head == null) {
			return false;
		}
		//Handle if the word is the first word
		if(head.getWord().equals(word)) {
			if(deleteOneOccurence == false || head.getOccurrences() == 1) {
				head = head.getNext();
				size--;
				return true;
			}
			else {
				head.decrementOccurrences();
				return true;
			}
		}
		//Search for the word if not the first word
		Node current = head;
		while(current.getNext() != null && !(current.getNext().getWord().equals(word))) {
			//Until it finds the word before the word you're looking for, it will update current
			current = current.getNext();
		}
		//Once the word before the word is found or you get to the end of the linked list
		if(current.getNext() != null) {
			//If occurrence drops to 0 or full word is to be dropped, delete the element
			if(deleteOneOccurence == false || current.getNext().getOccurrences() == 1) {
				current.setNext(current.getNext().getNext());
				size--;
				return true;
			}
			//If not just decrement the occurrence
			else {
				current.getNext().decrementOccurrences();
				return true;
			}
		}
		//If it's not found false is returned
		return false;
	}
	
	public String[] list(boolean isAscending) {
		String[] arr = new String[size];
		Node current = head;
		int index = isAscending ? 0 : size - 1;
		while(current != null) {
			arr[index] = current.getWord();
			current = current.getNext();
			index = isAscending ? index + 1 : index - 1;
		}
		return arr;
	}
	
	public String getHighestOccurrence() {
		if(head == null) {
			return "";
		}
		String maxWord = head.getWord();
		int maxWordOccurrences = head.getOccurrences();
		Node current = head;
		while(current != null) {
			if(current.getOccurrences() > maxWordOccurrences) {
				maxWord = current.getWord();
				maxWordOccurrences = current.getOccurrences();
			}
			current = current.getNext();
		}
		return maxWord;
	}
	
	public String getLowestOccurrence() {
		if(head == null) {
			return "";
		}
		String minWord = head.getWord();
		int minWordOccurrences = head.getOccurrences();
		Node current = head;
		while(current != null) {
			if(current.getOccurrences() < minWordOccurrences) {
				minWord = current.getWord();
				minWordOccurrences = current.getOccurrences();
			}
			current = current.getNext();
		}
		return minWord;
	}
	
	public String[] getTopFive() {
		//If the list is less than size 5, it will just return the words in order of largest to smallest
		Node[] top5NodeArr = new Node[Math.min(5, size)];
		int index = 0;
		Node current = head;
		while(index < top5NodeArr.length && current != null) {
			top5NodeArr[index] = current;
			index++;
			current = current.getNext();
		}
		while(current != null) {
			//Check if it's greater than any of the other occurences in the list
			for(int i = 0; i < top5NodeArr.length; i++) {
				if(current.getOccurrences() > top5NodeArr[i].getOccurrences()) {
					top5NodeArr[i] = current;
					break;
				}
			}
			current = current.getNext();
		}
		//Selection sort the nodes
		for(int i = 0; i < top5NodeArr.length; i++) {
			int maxIndex = i;
			for(int j = i + 1; j < top5NodeArr.length; j++) {
				if(top5NodeArr[j].getOccurrences() > top5NodeArr[maxIndex].getOccurrences()) {
					maxIndex = j;
				}
			}
			if(maxIndex != i) {
				Node temp = top5NodeArr[maxIndex];
				top5NodeArr[maxIndex] = top5NodeArr[i];
				top5NodeArr[i] = temp;
			}
		}
		String[] strArr = new String[top5NodeArr.length];
		for(int i = 0; i < strArr.length; i++) {
			strArr[i] = top5NodeArr[i].getWord();
		}
		return strArr;
	}
	
	public String[] getBottomFive() {
		//If the list is less than size 5, it will just return the 
		Node[] bottom5NodeArr = new Node[Math.min(5, size)];
		int index = 0;
		Node current = head;
		while(index < bottom5NodeArr.length && current != null) {
			bottom5NodeArr[index] = current;
			index++;
			current = current.getNext();
		}
		while(current != null) {
			//Check if it's greater than any of the other occurences in the list
			for(int i = 0; i < bottom5NodeArr.length; i++) {
				if(current.getOccurrences() < bottom5NodeArr[i].getOccurrences()) {
					bottom5NodeArr[i] = current;
					break;
				}
			}
			current = current.getNext();
		}
		//Selection sort the nodes
		for(int i = 0; i < bottom5NodeArr.length; i++) {
			int minIndex = i;
			for(int j = i + 1; j < bottom5NodeArr.length; j++) {
				if(bottom5NodeArr[j].getOccurrences() > bottom5NodeArr[minIndex].getOccurrences()) {
					minIndex = j;
				}
			}
			if(minIndex != i) {
				Node temp = bottom5NodeArr[minIndex];
				bottom5NodeArr[minIndex] = bottom5NodeArr[i];
				bottom5NodeArr[i] = temp;
			}
		}
		String[] strArr = new String[bottom5NodeArr.length];
		for(int i = 0; i < strArr.length; i++) {
			strArr[i] = bottom5NodeArr[i].getWord();
		}
		return strArr;
	}
	
	public int getSumOfWords() {
		int sum = 0;
		Node current = head;
		while(current != null) {
			sum += current.getOccurrences();
			current = current.getNext();
		}
		return sum;
	}
	
	public int getDifferentWordCount() {
		int count = 0;
		Node current = head;
		while(current != null) {
			count++;
			current = current.getNext();
		}
		return count;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Node current = head;
		while(current != null) {
			sb.append("Word: " + current.getWord() + " Occurences: " + current.getOccurrences() + "\n");
			current = current.getNext();
		}
		return sb.toString();
	}
	
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(!(obj instanceof LinkedList)){
			return false;
		}
		LinkedList ll = (LinkedList) obj;
		Node current1 = this.head;
		Node current2 = ll.head;
		while(current1 != null && current2 != null) {
			if(!(current1.getWord().equals(current2.getWord())) && current1.getOccurrences() != current2.getOccurrences()){
				return false;
			}
			current1 = current1.getNext();
			current2 = current2.getNext();
		}
		if(current1 == null && current2 != null || current1 != null && current2 == null) {
			return false;
		}
		return true;
	}
}
