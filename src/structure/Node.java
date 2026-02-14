package structure;

import model.Player;

public class Node {
	// doubly linked list node that will store as Player
	
	private Player data;
	private Node next;
	private Node prev;
	
	public Node(Player data) {
		this.data = data;
		this.next = null;
		this.prev = null;
	}
	// Getters
	public Player getData() { return data;}
	public Node getNext() { return next;}
	public Node getPrev() { return prev;}
	
	// Setters
	public void setData(Player data) { this.data = data;}
	public void setNext(Node next) { this.next = next;}
	public void setPrev(Node prev) { this.prev = prev;}
}