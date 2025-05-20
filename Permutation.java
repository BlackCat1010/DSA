import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randoqueue = new RandomizedQueue<String>();

        int toprint = StdIn.readInt();
        while (!StdIn.isEmpty()) {
            randoqueue.enqueue(StdIn.readString());
            // System.out.println("enqueued");
        }

        Iterator<String> it = randoqueue.iterator();

        for (int i = 0 ; i < toprint;i++) {
            randoqueue.dequeue();
        }

        

    }

    
}
