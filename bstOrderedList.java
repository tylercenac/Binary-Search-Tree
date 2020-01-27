import java.util.ArrayList;
import java.util.function.Function;

public class bstOrderedList {
	
    //Used to count size
    private int tmpCounter;

    class Node {
        public Node leftChild;
        public Comparable data;
        public Node rightChild;
    }

    // The root of the tree
    private Node root;

    //The default constructor for our BST
    public bstOrderedList() {
        root = null;
    }

    //Wrapper method for adding.  Adds the root if BST is empty.
    public void add(Comparable newObject) {
        if (isEmpty()) {
            root = new Node();
            root.data = newObject;
        }
        else
        {
            recAdd(root, newObject);
        }
    }

    //Recursively traverses the tree until an empty spot is found where we can add our new data
    private Node recAdd(Node root, Comparable data)
    {
        if (root == null) {
            root = new Node();
            root.data = data;
        }
        else
        {
            int dataComparison = data.compareTo(root.data);
            if (dataComparison < 0) {
                root.leftChild = recAdd(root.leftChild, data);
            }
            else if (dataComparison > 0) {
                root.rightChild = recAdd(root.rightChild, data);
            }
        }
        return root;
    }

    //Wrapper method for the remove method
    public void remove(Comparable object) {
        root = recRemove(root, object);
    }

    //Recursively traverses the tree and removes the node with the correct data
    private Node recRemove(Node root, Comparable data) {
        if (root == null) return root;

        int dataComparison = data.compareTo(root.data);
        if (dataComparison < 0) {
            root.leftChild = recRemove(root.leftChild, data);
        } else if (dataComparison > 0) {
            root.rightChild = recRemove(root.rightChild, data);
        } else {
            if (root.leftChild == null) {
                return root.rightChild;
            } else if (root.rightChild == null) {
                return root.leftChild;
            }

            root.data = minValue(root.rightChild);
            root.rightChild = recRemove(root.rightChild, root.data);
        }

        return root;
    }

    //Finds the leftmost value in the right subtree of the root
    private Comparable minValue(Node root)
    {
        Comparable minV = root.data;

        while(root.leftChild != null) {
            minV = root.leftChild.data;
            root = root.leftChild;
        }
        return minV;
    }

    //Gives a function to increment a counter to inorder traversal, then returns the result
    public int size() {
        tmpCounter = 0;
        inOrderRec(root, x -> tmpCounter++);
        return tmpCounter;
    }

    //Returns true if the root is null
    public boolean isEmpty() {
        return root == null;
    }

    //Return a string representation of the contents in the BST
    public String toString() {
        StringBuilder builder = new StringBuilder();
        inOrderRec(root, x -> builder.append(String.format("%s", x.toString())));
        return builder.toString();
    }

    //Returns an array of the values in the BST using the provided sorting order
    public Comparable[] toArray(String sorting) {
        ArrayList<Comparable> list = new ArrayList<>();
        if (sorting.equals("Inorder")) {
            inOrderRec(root, x -> list.add(x));
        } else if (sorting.equals("Postorder")) {
            postOrderRec(root, x -> list.add(x));
        } else if (sorting.equals("Preorder")) {
            preOrderRec(root, x -> list.add(x));
        }
        Comparable[] out = new Comparable[list.size()];
        return list.toArray(out);
    }

    //Traverses the BST in order and applies the provided function to the data when visited
    private void inOrderRec(Node root, Function<Comparable, Object> function) {
        if (root != null) {
            inOrderRec(root.leftChild, function);
            function.apply(root.data);
            inOrderRec(root.rightChild, function);
        }
    }

    //Traverses the BST in preorder and applies the provided function to the data when visited
    private void preOrderRec(Node root, Function<Comparable, Object> function) {
        if (root != null) {
            function.apply(root.data);
            preOrderRec(root.leftChild, function);
            preOrderRec(root.rightChild, function);
        }
    }

    //Traverses the BST in postorder and applies the provided function to the data when visited
    private void postOrderRec(Node root, Function<Comparable, Object> function) {
        if (root != null) {
            postOrderRec(root.leftChild, function);
            postOrderRec(root.rightChild, function);
            function.apply(root.data);
        }
    }


}
