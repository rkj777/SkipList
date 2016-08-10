import java.util.Random;

public class Skip_List<Key extends Comparable<? super Key>, Value> {

	private Node head, tail;
	private int n, h;
	private Random r = new Random();

	public Skip_List() {
		Node n1 = new Node(null, null, Node.negInf);
		Node n2 = new Node(null, null, Node.posInf);

		n1.right = n2;
		n2.left = n1;

		head = n1;
		tail = n2;

		n = 0;
		h = 0;
	}

	public Node findEntry(Key k) {
		Node n = head;

		while (true) {
			while ((n.right.inf != Node.posInf)
					&& (n.right.key.compareTo(k) <= 0)) {
				n = n.right;
			}

			if (n.down != null) {
				n = n.down;
			} else {
				break;
			}
		}
		return n;
	}

	public Value find(Key k) {
		Node n = findEntry(k);

		if (k.equals(n.key)) {
			return (n.value);
		}
		return null;
	}

	public void addLayer() {
		Node n1 = new Node(null, null, Node.negInf);
		Node n2 = new Node(null, null, Node.posInf);

		n1.right = n2;
		n1.down = head;

		n2.left = n1;
		n2.down = tail;

		head.up = n1;
		tail.up = n2;

		head = n1;
		tail = n2;

		h++;
	}

	public void add(Key k, Value v) {
		Node n1, n2;
		int i = 0;

		n1 = findEntry(k);

		if (k == n1.key) {
			n1.value = v;
			return;
		}

		n2 = new Node(k, v, null);

		n2.left = n1;
		n2.right = n1.right;
		n1.right.left = n2;
		n1.right = n2;

		while (r.nextDouble() < 0.5) {
			if (i >= h) {
				addLayer();
			}

			while (n1.up == null) {
				n1 = n1.left;
			}

			n1 = n1.up;

			Node n3 = new Node(k, null, null);
			n3.left = n1;
			n3.right = n1.right;
			n3.down = n2;

			n1.right.left = n3;
			n1.right = n3;
			n2.up = n3;

			n2 = n3;

			i = i + 1;
		}

		n = n + 1;
		return;
	}

	public void remove(Key k) {
		Node n1 = findEntry(k);
		Node n2;
		if (n1.key != k) {
			System.out.println("Item not found");
			return;
		}
		
		n = n-1;

		n1.left.right = n1.right;
		n1.right.left = n1.left;
		n1 = n1.up;

		while (n1 != null) {
			n1.left.right = n1.right;
			n1.right.left = n1.left;
			n1.down.up = null;
			n1.down.down = null;
			n1.down = null;
			n1 = n1.up;
		}

	}

	public void clear() {
		Node n1 = head;
		Node n2;

		while (n1.down != null) {
			n1 = n1.down;
		}
		
		
		n1 = n1.right;
		while (n1.inf != Node.posInf) {
			n2 = n1.right;
			n1.left.right = n1.right;
			n1.right.left = n1.left;
			n1 = n1.up;
			
			

			while (n1 != null) {
				n1.left.right = n1.right;
				n1.right.left = n1.left;
				n1.down.up = null;
				n1.down.down = null;
				n1.down = null;
				n1 = n1.up;
			}
			
			n1 = n2;
		}
		
		n = 0;
		h= 0;
	}
	
	public int size(){
		return n;
	}
	
	public String toString(){
		Node n1 = head;
		
		String list = "";
		
		while (n1.down != null) {
			n1 = n1.down;
		}
		n1 = n1.right;
		while(n1.inf != Node.posInf){
			list += n1.key + " : " + n1.value + ", ";
			n1 = n1.right;
		}
		
		return list;
	}

	private class Node {
		private Key key;
		private Value value;
		private String inf;

		public static final String negInf = "-inf";
		public static final String posInf = "+inf";

		private Node up = null;
		private Node down = null;
		private Node left = null;
		private Node right = null;

		public Node(Key k, Value v, String infinty) {
			key = k;
			value = v;
			inf = infinty;

		}

	}
	
	public static void main(String[] args) {
		Skip_List<Integer,String> list = new Skip_List<Integer,String>();
		list.add(2, "YES");
		list.add(1,"YEAH");
		list.add(3, "HELLO");
		
		System.out.println(list);
	}
}
