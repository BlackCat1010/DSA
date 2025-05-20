
import java.util.Iterator;

    public class Deque<Item> implements Iterable<Item> {
        private Node frontdeck, backdeck;
        private int counter;

        private class Node {
            Node next;
            Node prev;
            Item item;

            public Node(Item item) {
                this.item = item;
            }
        }

        // construct an empty deque
        public Deque() {
            this.frontdeck = null;
            this.backdeck = null;
            counter = 0;

        }

        // is the deque empty?
        public boolean isEmpty() {
            return counter == 0;
        }

        // return the number of items on the deque
        public int size() {
            return counter;
        }

        // add the item to the front
        public void addFirst(Item item) {
            if (item == null) {
                throw new IllegalArgumentException("Null Arguments not allowed");
            } 

            // New Node Item created.
            Node newNode = new Node(item);

            if (isEmpty()) {
                // First Node added, frontdeck backdeck point to firstNode
                // System.out.println("addFirst.IsEmpty entered.");
                frontdeck = newNode;
                backdeck = newNode;
                counter++;
            } else {
                /* Second++ Node added. 

                Classical Deque: newNode is new frontdeck
                oldfrontdeck.prev is the newNode
                newNode.next is oldfrontdeck;
                Advance

                */ 
                // System.out.println("addFirst.nodecreating entered.");
                frontdeck.prev = newNode;
                newNode.next = frontdeck;
                frontdeck = newNode;
                // Increment size
                counter++;
            }
        }

        // add the item to the back
        public void addLast(Item item) {
            if (item == null) {
                throw new IllegalArgumentException("Null Arguments not allowed");
            } 

            // New Node Item created.
            Node newNode = new Node(item);

            if (isEmpty()) {
                // System.out.println("addLast.IsEmpty entered.");
                frontdeck = newNode;
                backdeck = newNode;
                counter++;
            } else {
                /* 
                Second++ Node added

                Link backdeck.next to newNode
                Link newNode.prev to backdeck
                Advance
                */ 

                // System.out.println("addLast.nodecreating entered.");
                backdeck.next = newNode;
                newNode.prev = backdeck;
                backdeck = newNode;
            
                counter++;
            }
        }

        // remove and return the item from the front
        public Item removeFirst() {
            if (isEmpty()) {
                throw new java.util.NoSuchElementException("Deque is empty");
            }

            // Set holder for Item
            Item item = frontdeck.item;

            // Advance frontdeck to PreviousNode and remove first
            if (frontdeck.next != null) {
                frontdeck = frontdeck.next;
                frontdeck.prev = null;
            } else {
                frontdeck = null;
                backdeck = null;
            }
            // Reduce counter
            counter--;
            return item;
        }

        // remove and return the item from the back
        public Item removeLast() {
            if (isEmpty()) {
                throw new java.util.NoSuchElementException("Deque is empty");
            }


            Item item = backdeck.item;

            if (backdeck.prev != null) {
                // Carry on backdeck remove operation
                backdeck = backdeck.prev;
                backdeck.next = null;
            } else {
                frontdeck = null;
                backdeck = null;
            }
            
            counter--;
            return item;
        }

         
        // return an iterator over items in order from front to back
        @Override
        public Iterator<Item> iterator() {
            return new frontdeckIterator();
        }

        private class frontdeckIterator implements Iterator<Item> {
            private Node frontptr = frontdeck;
            @Override
            public boolean hasNext() {return frontptr != null;}
            @Override
            public void remove() { throw new UnsupportedOperationException("remove() operation not supported"); }
            @Override
            public Item next() {
                if (!hasNext()) {
                throw new java.util.NoSuchElementException("Deque is empty");
            }

                Item currentItem = frontptr.item;
                frontptr = frontptr.next;
                // System.out.println("iterator().next activated: "+currentItem);
                return currentItem;
            }

        }

        // unit testing (required)
        public static void main(String[] args) {
            Deque<String> testDequeInst = new Deque<>();
            System.out.println("Testing isEmpty on empty Deque: "+testDequeInst.isEmpty());
            System.out.println("Testing size on empty Deque: "+testDequeInst.size());
            String item1 = "Item 1";
            String item2 = "Item 2";
            String item3 = "Item 3";

            // Test if addFirst addLast first working
            testDequeInst.addFirst(item1);
            testDequeInst.addLast(item2);
            testDequeInst.addFirst(item3);
            System.out.println("Testing isEmpty after addfirst 3 items: "+testDequeInst.isEmpty());
            System.out.println("Testing size after addfirst 3 items: "+testDequeInst.size());
            // Pass if shows 3 -- Current order: 3,1,2

            // Test if removefirst working -- Expecting item3, then item2
            System.out.println("RemovingFirst Test - Expecting Item3: "+testDequeInst.removeFirst());
            System.out.println("RemovingFirst Test - Expecting Item1: "+testDequeInst.removeFirst());
            System.out.println("Testing size after removing 2 items: "+testDequeInst.size());

            // Test for removelast if working -- Expecting item1 then item2
            testDequeInst.addFirst(item1);
            testDequeInst.addFirst(item2);
            testDequeInst.addFirst(item3);
            System.out.println("RemovingLast Test - Expecting Item2: "+testDequeInst.removeLast());
            System.out.println("RemovingLast Test - Expecting Item1: "+testDequeInst.removeLast());
            System.out.println("RemovingLast Test - Expecting Item2: "+testDequeInst.removeLast());
            System.out.println("Testing size after removing 3 items: "+testDequeInst.size());
            // Pass is 1 left

            // Test Remove All
            System.out.println("RemoveAll Test - Expecting Item3: "+testDequeInst.removeLast());
            System.out.println("Testing isEmpty on empty Deque: "+testDequeInst.isEmpty());
            System.out.println("Testing size on empty Deque: "+testDequeInst.size());

            // Iterator Testing
            testDequeInst.addFirst(item1);
            testDequeInst.addFirst(item2);
            testDequeInst.addLast(item3);
            Iterator<String> iterator = testDequeInst.iterator(); 
            System.out.println("Starting Iterator Test: ");
            while(iterator.hasNext()) {
                System.out.println(" "+iterator.next());
            }


        }

    }
