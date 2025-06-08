import java.util.LinkedList;
import java.util.Queue;

public class BST {
    Node root;
    private class Node {
        Node left,right;
        int Key;
        String Value;
        int Depth;

        public Node(int Key, String Value,Node left, Node right) {
            this.Key = Key;
            this.Value = Value;
            this.left = left;
            this.right = right;
        }
    }

    BST(int Key, String Value) {
        this.root = new Node(Key,Value,null,null);
        this.root.Depth = 0;

    }
    /*
     * Start of Operations for BST below
     */

    public void insert(int Key,String Value){
        insert(this.root,Key,Value,0);
    }

    private Node insert(Node root,int Key,String Value, int Depth) {
        if (root == null) {
            root = new Node(Key,Value,null,null);
            root.Depth = Depth;
            System.out.println("Node added");
            return root;
        }

        if (Key > root.Key){
            root.right = insert(root.right,Key,Value,Depth+1);
            return root;
        }
        if (Key < root.Key){
            root.left = insert(root.left,Key,Value,Depth+1);
            return root;
        }
        if (Key == root.Key){
            System.out.println("Duplicate Key Found, Value Updated from "+root.Value+" to "+ Value);
            root.Value = Value;
            return root;
        }
        return null;
        
    }

    public void delete(int Key){
        delete(root,Key,0);

    }
    private Node delete(Node root, int Key, int Depth) {
        if (root==null){
            System.out.println("Deletion Node not found.");
            return null;
        }

        if (Key > root.Key){
            root.right = delete(root.right,Key,Depth+1);
        } else if (Key < root.Key){
            root.left = delete(root.left,Key,Depth+1);
        } else {
        // Delete Logic here
        // 3 Scenarios: 0 child, 1 child, 2 child
        if (root.left == null && root.right == null){
            return null;
        }
        if (root.left == null){
            return root.right;
        }
        if (root.right == null){
            return root.left;
        }

        // 2 Children scenario - Look right Node and find leftmost. Set it as deleted node, and delete leftmost.
        Node successor = root.right;
        while (successor.left!=null){
            successor = successor.left;
        }
        root.Key = successor.Key;
        root.Value = successor.Value;
        root.Depth = Depth;
        root.right = delete(root.right,successor.Key,Depth+1); 
        return root;

    }
    updateDepths(root,Depth);
    return root;
    }
    private void updateDepths(Node root, int Depth){
        if (root==null){return;}
        root.Depth = Depth;
        updateDepths(root.left,Depth+1);
        updateDepths(root.right,Depth+1);
    }
    public int countNodes(){
        return countNodes(root);
    }

    private int countNodes(Node root){
        if (root == null){return 0;}
        return 1+countNodes(root.left)+countNodes(root.right);
    }

    public int maxDepth(){
        return maxDepth(root);
    }

    private int maxDepth(Node root){
        if (root == null){return 0;}
        return 1+ Math.max(maxDepth(root.left),maxDepth(root.right));
    }
    /*
     * End of Operations for BST below
     */


    /*
     * Start of Search/Traversal Functionalities for BST
     */

    

    public Node search(int Key) {
        Node searchtarget = search(root,Key);
        System.out.println("Search target found. Value: "+searchtarget.Value+"\nAt depth: "+searchtarget.Depth);
        return searchtarget;
    }

    private Node search(Node root,int Key) {
        if (root == null){return null;}
        if (Key == root.Key){
            return root;
        }
        if (Key > root.Key){
            return search(root.right,Key);
        }
        if (Key < root.Key){
            return search(root.left,Key);
        }
        return null;
    }

    public void inOrderSearch() {
        System.out.println("inOrderSearch Starts");
        inOrderSearch(root);
    }

    private void inOrderSearch(Node root) {
        if (root==null){return;}
        inOrderSearch(root.left);
        System.out.println(root.Key+" at Depth "+root.Depth);
        inOrderSearch(root.right);
    }

    public void preOrderSearch() {
        System.out.println("preOrderSearch Starts");
        preOrderSearch(root);
    }

    private void preOrderSearch(Node root) {
        if (root==null){return;}
        System.out.print(root.Key+" ");
        preOrderSearch(root.left);
        preOrderSearch(root.right);

    }

    public void postOrderSearch() {
        System.out.println("\npostOrderSearch Starts");
        postOrderSearch(root);
    }

    private void postOrderSearch(Node root) {
        if (root==null){return;}
        postOrderSearch(root.right);
        postOrderSearch(root.left);
        System.out.print(root.Key+" ");
    }

    public void breadthFirstSearch() {
        System.out.println("\nbreadthFirstSearch Starts");
        if (root==null){
            System.out.println("tree is empty");
            return ;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            Node current = queue.poll();
            System.out.print(current.Key + " ");
            if(current.left!=null){queue.add(current.left);};
            if(current.right!=null){queue.add(current.right);};
        }
    }


    /*
     * End of Search/Traversal Functionalities for BST
     */

    public static void main(String[] args) {
        // main Test cases

        BST newbest = new BST(5,"Five");
        newbest.insert(10,"Ten");
        newbest.insert(8,"Eight");
        newbest.insert(1,"One");
        newbest.insert(2,"Two");
        newbest.insert(3,"Three");
        newbest.insert(4,"Four");
        newbest.insert(7,"Seven");
        newbest.insert(9,"Nine");
        newbest.inOrderSearch();
        newbest.search(3);
        newbest.delete(10);  // leaf
        newbest.delete(1);   // one child
        newbest.delete(5);   // two children (root)
        newbest.inOrderSearch();
        newbest.preOrderSearch();
        newbest.postOrderSearch();
        newbest.breadthFirstSearch();
        System.out.println("\nTotal Nodes:"+newbest.countNodes());
        System.out.println("\nMax Height:"+newbest.maxDepth());


    }


}
