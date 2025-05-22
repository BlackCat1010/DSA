import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
   private List<LineSegment> listOfSegments = new ArrayList<>();

   public FastCollinearPoints(Point[] points) {
    if (points == null) {
        throw new IllegalArgumentException("points is null");
    }
    for (Point p:points) {
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
    // End of handling

    // finds all line segments containing 4 or more points
    for (int i = 0; i < points.length; i++) {
        Point anchor = points[i];
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints, anchor.slopeOrder());
        int counter = 1;

        for (int j = 1; j < sortedPoints.length; j++) {
            // If different slope from previous, check if old counter logs>3 consecutive slopes.
            // Using previous is OK because if 0th element is anchor and counter starts with 1 everytime.
            // If counter>=3, log counted points into collinearpoints.
            if (Double.compare(anchor.slopeTo(sortedPoints[j]), anchor.slopeTo(sortedPoints[j-1])) != 0) {
                // Double.compare to avoid rounding issues with doubles

                // If counter >=3 and next one is different, break off and add
                if (counter >= 3) {
                    List<Point> collinearpoints = new ArrayList<>();
                    
                    // Lock points into collinearpoints
                    collinearpoints.add(anchor);
                    for (int k = j -1; k >= j-counter; k--) {
                        collinearpoints.add(sortedPoints[k]);
                    }
                    // Process linesegment from collinearpoints and add the points listOfSegments
                    if (anchor.compareTo(Collections.min(collinearpoints))==0){
                        // Added in so anchor must be smallest before can add into listOfSegments < Prevent Dupe
                        listOfSegments.add(getLineSegment(collinearpoints));
                    }
                    
                }

                // Reset counter & advance
                counter = 1;
            }
            // If same as previous, inc counter.;
            else {
                // Double.compare(anchor.slopeTo(sortedPoints[j]),anchor.slopeTo(sortedPoints[j-1]))==0
                counter++;
            }
        }

        // Chatgpt's suggestion to eliminate last group issue below
        if (counter >= 3) {
            List<Point> collinearpoints = new ArrayList<>();
            collinearpoints.add(anchor);
            for (int k = sortedPoints.length - 1; k >= sortedPoints.length - counter; k--) {
                collinearpoints.add(sortedPoints[k]);
            }
            if (anchor.compareTo(Collections.min(collinearpoints)) == 0) {
                listOfSegments.add(getLineSegment(collinearpoints));
            }
        }
        // End of Chatgpt's suggestion. basically just to run again.
    }
    }    
    public int numberOfSegments() { return listOfSegments.size(); } // the number of line segments
    public LineSegment[] segments() {
        return this.listOfSegments.toArray(new LineSegment[0]);
    } // the line segments

    private LineSegment getLineSegment(List<Point> a) {
        Collections.sort(a); // Let it be in natural order;
        LineSegment ans = new LineSegment(a.get(0), a.get(a.size()-1));
        return ans;
    }
}
