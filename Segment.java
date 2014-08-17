package perp;
    
class Segment extends Line {

    Segment(Point p1, Point p2) {
        super(p1, p2);
    }

    Line toPerpLine() {
        Point mid = midpoint();
        double slope = slope();
        double perpSlope;
        if (Double.isNaN(slope)) perpSlope = 0;
        else perpSlope = -1. / slope;
        Point other = new Point(mid.x + 1, mid.y + perpSlope);
        return new Line(mid, other);
    }

    Point midpoint() {
        double x = (p2.x - p1.x) / 2;
        double y = (p2.y - p1.y) / 2;
        return new Point(x, y);
    }
}


    
