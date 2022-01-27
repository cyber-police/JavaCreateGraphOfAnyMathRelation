package src.graph;

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GraphDraw extends JFrame {
    int width;
    int height;

    ArrayList<Node> nodes;
    ArrayList<edge> edges;

    public GraphDraw() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nodes = new ArrayList<Node>();
        edges = new ArrayList<edge>();
        width = 60;
        height = 60;
    }

    public GraphDraw(String name) {
        this.setTitle(name);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nodes = new ArrayList<Node>();
        edges = new ArrayList<edge>();
        width = 60;
        height = 60;
    }

    class Node {
        int x, y;
        String name;

        public Node(String myName, int myX, int myY) {
            x = myX;
            y = myY;
            name = myName;
        }
    }

    class edge {
        int i, j;

        public edge(int ii, int jj) {
            i = ii;
            j = jj;
        }
    }

    public void addNode(String name, int x, int y) {
        nodes.add(new Node(name, x, y));
        this.repaint();
    }

    public void addEdge(int i, int j) {
        edges.add(new edge(i, j));
        this.repaint();
    }

    public void paint(Graphics g) {
        FontMetrics f = g.getFontMetrics();
        int nodeHeight = Math.max(height, f.getHeight());

        for (Node n : nodes) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            int nodeWidth = Math.max(width, f.stringWidth(n.name) + width / 2);
            g.setColor(Color.black);
            g.drawOval(n.x - width / 2, n.y - height / 2, width, height);
            g.setColor(Color.white);
            g.fillOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);
            g.setColor(Color.black);
            g.drawOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);
            g.drawString(n.name, n.x - f.stringWidth(n.name), n.y + f.getHeight() / 2);
        }

        g.setColor(Color.black);
        for (edge e : edges) {
            int dx = nodes.get(e.j).x - nodes.get(e.i).x, dy = nodes.get(e.j).y - nodes.get(e.i).y;
            if (e.j == e.i) {
                g.setColor(Color.red);
                g.drawOval(nodes.get(e.i).x - width / 2, nodes.get(e.i).y - nodeHeight / 2, width, height);
            } else {
                double D = Math.sqrt(dx * dx + dy * dy);
                double k = (double) ((D - 30) / D);
                double dy2 = (int) dy;
                double dx2 = (int) dx;
                dy2 = dy2 * k;
                dx2 = dx2 * k;
                dy = (int) dy2;
                dx = (int) dx2;
                int x2 = dx + nodes.get(e.i).x;
                int y2 = dy + nodes.get(e.i).y;
                // B-side
                dx = nodes.get(e.i).x - nodes.get(e.j).x;
                dy = nodes.get(e.i).y - nodes.get(e.j).y;

                dy2 = (int) dy;
                dx2 = (int) dx;
                dy2 = dy2 * k;
                dx2 = dx2 * k;
                dy = (int) dy2;
                dx = (int) dx2;
                int x1 = dx + nodes.get(e.j).x;
                int y1 = dy + nodes.get(e.j).y;

                dx = x2 - x1;
                dy = y2 - y1;
                D = Math.sqrt(dx * dx + dy * dy);

                double xm = D - 10, xn = xm, ym = 10, yn = -10, x;
                double sin = dy / D, cos = dx / D;

                x = xm * cos - ym * sin + x1;
                ym = xm * sin + ym * cos + y1;
                xm = x;
                x = xn * cos - yn * sin + x1;
                yn = xn * sin + yn * cos + y1;
                xn = x;
                int[] xpoints = { x2, (int) xm, (int) xn };
                int[] ypoints = { y2, (int) ym, (int) yn };
                g.setColor(Color.black);
                g.drawLine(x1, y1, x2, y2);
                g.fillPolygon(xpoints, ypoints, 3);
            }
        }
    }
}