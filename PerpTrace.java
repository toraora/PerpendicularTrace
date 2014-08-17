package perp;

public class PerpTrace {
    
    static double fx(double param) {
        return Math.sin(param) + Math.sin( 2 * param);
    }

    static double fy(double param) {
        return Math.cos(3 * param + 2);
    }

    static double RANGE_MIN = 0;
    static double RANGE_MAX = 2 * Math.PI;
    static double RANGE = RANGE_MAX - RANGE_MIN;
    static int STEPS = 500;
    static double STEPSIZE =  RANGE / STEPS;
    static int SMALLSTEPS = 50000;
    static double SMALLSTEPSIZE =  RANGE / SMALLSTEPS;
    static int SIZE = 5001;
    static double ZOOM = 1000;

    static Point pivot = new Point(500, -100);
    static LineImage image = new LineImage(SIZE, ZOOM);

    public static void main(String[] args) {
        Point p;
        for (double theta = RANGE_MIN; theta < RANGE_MAX; theta += STEPSIZE) {
            p = new Point(ZOOM * fx(theta), ZOOM * fy(theta));
            image.drawLine(new Segment(pivot, p).toPerpLine());
        }
        for (double theta = RANGE_MIN; theta < RANGE_MAX; theta += SMALLSTEPSIZE) {
            p = new Point(ZOOM * fx(theta), ZOOM * fy(theta));
            image.drawPoint(p, 255, 0, 0);
        }
        for (int i = -5; i <= 5; i++)
            for (int j = -5; j <= 5; j++) {
                p = new Point(pivot.x + i, pivot.y + j);
                image.drawPoint(p, 0, 255, 0);
            }
        image.saveTo("out.tiff");
    }
}    
