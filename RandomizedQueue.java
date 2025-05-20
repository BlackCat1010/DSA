import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] itemArray;
    private int filledsize;
    private int capacity;

    // construct an empty randomized queue
    public RandomizedQueue() {
        filledsize = 0;
        capacity = 1;
        itemArray = (Item[]) new Object[capacity];
    }

    
    // is the randomized queue empty?
    public boolean isEmpty() {
        return filledsize == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return filledsize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        itemArray[filledsize++]=item;
        // System.out.println("enqueue: "+item+" currentcap: "+capacity);
        ArrayResize();
    }

    private void ArrayResize() {
        while (filledsize > 0 && filledsize >= capacity/2 ) {
            // Create new Array and copy items over
            capacity = capacity*2;
            // System.out.println("Doubling capacity to "+capacity);

            Item[] newItemArray = (Item[]) new Object[capacity];

            for (int i = 0; i<filledsize;i++){
                newItemArray[i]=itemArray[i];
            }
            itemArray=newItemArray;
        }

        if (filledsize > 0 && filledsize < capacity/4){
            capacity = capacity/2;

            Item[] newItemArray = (Item[]) new Object[capacity];

            for (int i = 0; i<filledsize;i++) {
                newItemArray[i]=itemArray[i];
            }
            itemArray=newItemArray;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("RandomizedQueue is empty.");
        }

        int chosenInt = StdRandom.uniformInt(filledsize);
        // System.out.println("ChosenInt: "+chosenInt);
        Item chosenItem = itemArray[chosenInt];
        itemArray[chosenInt] = itemArray[filledsize-1];
        itemArray[filledsize-1] = null;
        filledsize--;
        ArrayResize();

        return chosenItem;


    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("RandomizedQueue is empty.");
        }

        int chosenInt = StdRandom.uniformInt(this.filledsize);
        Item chosenItem = itemArray[chosenInt];
        return chosenItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        Item[] independentItemArray;
        int currentIndex;

        public RandomQueueIterator() {
            currentIndex = 0;
            independentItemArray = (Item[]) new Object[capacity];
            for (int i = 0; i<filledsize; i++ ) {
                independentItemArray[i]=itemArray[i];
            }

        }

        @Override
        public boolean hasNext() {
            return currentIndex < filledsize;
        }

        @Override
        public Item next() {
            if (currentIndex > filledsize) {
                throw new java.util.NoSuchElementException("Array out of bounds");
            }
            return itemArray[currentIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove unsupported.");
        }

    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<String> testInstance = new RandomizedQueue<String>();
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";
        String test4 = "test4";

        System.out.println("Test Start:");

        System.out.println("Empty Inst: testInstance.isEmpty() ->"+testInstance.isEmpty());
        System.out.println("Empty Inst: testInstance.size() ->"+testInstance.size());

        testInstance.enqueue(test1);
        testInstance.enqueue(test2);
        testInstance.enqueue(test3);
        testInstance.enqueue(test4);

        System.out.println("Inst w 4 Items: testInstance.isEmpty() ->"+testInstance.isEmpty());
        System.out.println("Inst w 4 Items: testInstance.size() ->"+testInstance.size());

        Iterator<String> itr = testInstance.iterator();

        System.out.println("Testing Iterator:");

        while(itr.hasNext()) {
            System.out.print(itr.next()+" ");
        }

        System.out.println("\n Sampling Start:");
        System.out.println(testInstance.sample());
        System.out.println(testInstance.sample());
        System.out.println(testInstance.sample());
        System.out.println("3 Times Sampling End isEmpty: "+testInstance.isEmpty()+ " End Size: "+testInstance.size());

        System.out.println("Dequeue Start:");
        System.out.println(testInstance.dequeue());
        System.out.println(testInstance.dequeue());
        System.out.println(testInstance.dequeue());
        System.out.println(testInstance.dequeue());
        System.out.println("4 Times Dequeue End isEmpty: "+testInstance.isEmpty()+ " End Size: "+testInstance.size());

        


    }

}
