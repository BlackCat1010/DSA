import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BruteCollinearPoints {
   private List<Point> listOfCollinearPoints;
   private List<LineSegment> listOfSegments = new ArrayList<>();

   public BruteCollinearPoints(Point[] points) {
 
    if (points == null) {
        throw new IllegalArgumentException("points[] cannot be null");
    }
    for (Point p: points) {
        if (p == null) {
            throw new IllegalArgumentException("points cannot be null");
        }
    }

    Point[] copy = points.clone();
    Arrays.sort(copy);
    for (int i = 1; i < copy.length; i++) {
    if (copy[i].compareTo(copy[i - 1]) == 0) {
        throw new IllegalArgumentException("Duplicate points found: " + copy[i]);
        }
    }

    // examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments

    for (int i = 0; i < points.length; i++) {
        for (int j = i+1; j < points.length; j++) {
            for (int k = j+1; k < points.length; k++) {
                for (int m = k+1; m < points.length; m++) {
                    Point p = points[i];
                    Comparator<Point> comp = p.slopeOrder();
                    
                    if (comp.compare(points[j], points[k]) == 0 && comp.compare(points[k], points[m]) == 0) {
                        // Line Segment 4 points found!
                        List<Point> listOfCollinearPoints = new ArrayList<Point>();
                        listOfCollinearPoints.add(p);
                        listOfCollinearPoints.add(points[j]);
                        listOfCollinearPoints.add(points[k]);
                        listOfCollinearPoints.add(points[m]);
                        Collections.sort(listOfCollinearPoints);

                        if (p.compareTo(listOfCollinearPoints.get(0))==0){
                        // Adding this to prevent dupe
                        listOfSegments.add(getLineSegment(listOfCollinearPoints));
                    }
                }
                    
                }
            }
        }
    }

   }    // finds all line segments containing 4 points
   public int numberOfSegments() { return listOfSegments.size(); }        // the number of line segments
   public LineSegment[] segments() {
    // the line segments
    return this.listOfSegments.toArray(new LineSegment[0]);
   }

   private LineSegment getLineSegment(List<Point> a) {
        Collections.sort(a); // Let it be in natural order;
        LineSegment ans = new LineSegment(a.get(0), a.get(a.size()-1));
        return ans;
    }
}
