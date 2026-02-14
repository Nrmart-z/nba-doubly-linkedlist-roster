package structure;
import model.Player;

public class RosterLinkedList {
	private Node head;
    private Node tail;
    private int size;

    private static final int MAX_ROSTER_SIZE = 15;

    public RosterLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    // Inserts player so roster stays sorted highest -> lowest rating
    public boolean addPlayerSorted(Player p) {
        if (p == null) return false;
        if (size >= MAX_ROSTER_SIZE) return false;

        Node newNode = new Node(p);

        // Case 1: empty list
        if (head == null) {
            head = tail = newNode;
            size++;
            return true;
        }

        int newRating = p.getOverallRating();

        // Case 2: insert before head 
        if (newRating >= head.getData().getOverallRating()) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
            size++;
            return true;
        }

        // Case 3: insert after tail 
        if (newRating <= tail.getData().getOverallRating()) {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            size++;
            return true;
        }

        // Case 4: insert in the middle
        Node curr = head;
        while (curr != null) {
            int currRating = curr.getData().getOverallRating();

            // Find first node with rating <= newRating, insert before it
            if (newRating >= currRating) {
                Node prev = curr.getPrev();

                prev.setNext(newNode);
                newNode.setPrev(prev);

                newNode.setNext(curr);
                curr.setPrev(newNode);

                size++;
                return true;
            }
            curr = curr.getNext();
        }

        return false; 
    }

    
    public void printRosterForward() {
        Node curr = head;
        while (curr != null) {
            System.out.println(curr.getData());
            curr = curr.getNext();
        }
    }

    public void printRosterBackward() {
        Node curr = tail;
        while (curr != null) {
            System.out.println(curr.getData());
            curr = curr.getPrev();
        }
    }
    
    public Player removeByName(String name) {
        if (head == null || name == null) return null;

        Node curr = head;

        while (curr != null) {
            Player p = curr.getData();

            if (p.getName().equalsIgnoreCase(name.trim())) {

                // Case 1: removing head
                if (curr == head) {
                    head = head.getNext();

                    if (head != null) {
                        head.setPrev(null);
                    } else {
                        // list became empty
                        tail = null;
                    }
                }
                // Case 2: removing tail
                else if (curr == tail) {
                    tail = tail.getPrev();
                    tail.setNext(null);
                }
                // Case 3: removing middle
                else {
                    Node prev = curr.getPrev();
                    Node next = curr.getNext();

                    prev.setNext(next);
                    next.setPrev(prev);
                }

                // fully detach
                curr.setNext(null);
                curr.setPrev(null);

                size--;
                return p;
            }

            curr = curr.getNext();
        }

        return null; // not found
    }
    public Player searchByName(String name) {
        if (head == null || name == null) return null;

        String target = name.trim();

        Node curr = head;
        while (curr != null) {
            Player p = curr.getData();
            if (p.getName().equalsIgnoreCase(target)) {
                return p;
            }
            curr = curr.getNext();
        }

        return null; // not found
        
    }
    
    public boolean contains(String name) {
        return searchByName(name) != null;
    }
    
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        Node curr = head;

        while (curr != null) {
            sb.append(curr.getData().toCSVLine()).append("\n");
            curr = curr.getNext();
        }

        return sb.toString();
    }

    public void loadFromCSV(String csvText) {
        clear();

        if (csvText == null || csvText.isEmpty()) return;

        String[] lines = csvText.split("\\R");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            Player p = Player.fromCSVLine(line);
            if (p != null) {
                addPlayerSorted(p);
            }
        }
    }


}
