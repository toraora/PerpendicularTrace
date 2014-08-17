package perp;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.media.jai.JAI;

class Pixel {
    int r;
    int g;
    int b;

    Pixel() {
        r = 0;
        b = 0;
        b = 0;
    }

    static int INC = 50;
    void increment() {
        if (r > 255 - INC) return;
        r += INC;
        g += INC;
        b += INC;
    }

    void setRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    int getRGB() {
        return new Color(r, g, b).getRGB();
    }
}

class LineImage {
    int size;
    double zoom;
    Pixel[][] pixels;

    LineImage(int size, double zoom) {
        this.size = size;
        this.zoom = zoom;
        pixels = new Pixel[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pixels[i][j] = new Pixel();
            }
        }
    }

    boolean inBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    void drawPoint(Point p, int r, int g, int b) {
        int x = (int) p.x + size / 2;
        int y = (int) p.y + size / 2;
        if (inBounds(x, y)) {
            pixels[y][x].setRGB(r, g, b);
        }
    }

    void drawLine(Line l) {
        double diffx = l.p2.x - l.p1.x;
        double diffy = l.p2.y - l.p1.y;
        double mag = 2 * Math.sqrt(diffx * diffx + diffy * diffy);
        double dx = diffx / mag;
        double dy = diffy / mag;
        if (Double.isNaN(dx) || Double.isNaN(dy)) return;

        double curx = l.p1.x + size / 2, cury = l.p1.y + size / 2;
        int prevx = -1, prevy = -1;
        while (inBounds((int) curx, (int) cury)) {
            if ((int) curx == prevx && (int) cury == prevy) {
                curx += dx;
                cury += dy;
                continue;
            }
            prevx = (int) curx;
            prevy = (int) cury;
//            System.out.println("incrementing pixel at " + curx + " " + cury);
            pixels[prevy][prevx].increment();
            curx += dx;
            cury += dy;
        }
        curx = l.p1.x - dx + (size - 1) / 2;
        cury = l.p1.y - dy + (size - 1) / 2;
        while (inBounds((int) curx, (int) cury)) {
            if ((int) curx == prevx && (int)cury == prevy) {
                curx -= dx;
                cury -= dy;
                continue;
            }
            prevx = (int) curx;
            prevy = (int) cury;
//            System.out.println("incrementing pixel at " + curx + " " + cury);
            pixels[prevy][prevx].increment();
            curx -= dx;
            cury -= dy;
        }
    }

    void saveTo(String filename) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                image.setRGB(i, j, pixels[j][i].getRGB());
            }
        }
        System.out.println("printing to file");
        JAI.create("filestore", image, filename, "tiff");
    }
}
            

