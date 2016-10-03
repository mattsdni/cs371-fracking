
import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;

public class SiteView extends PApplet
{

    public static void main(String args[])
    {
        PApplet.main(new String[]{"SiteView"});
    }

    private PFont f;
    private int bg;
    private PumpingSite p;
    Pump[] pumps;
    LinkedList<Pipe> pipes;
    boolean pump_selected;

    public void setup()
    {
        size(800, 600);
        bg = color(240, 230, 220);
        ellipseMode(CENTER);
        f = createFont("Arial", 24, true);
        textFont(f);
        frame.setTitle("Cabot Oil and Gas - Natural Gas Simulator");
        p = new PumpingSite();
        p.getSiteDescription("site.txt");
        p.collectionPoint();
        pumps = new Pump[p.pumpCount()];
        pipes = new LinkedList<>();
        int radius = width/4;
        for (int i = 0; i < pumps.length; i++)
        {
            int x = (int)(radius*cos(i*1f) + width/2 + 80);
            int y = (int)(radius*sin(i*1f) + height/2);
            pumps[i] = new Pump(x, y, 30, i, p.getPumpConnections(i));
//            for (Pipe p : pipes)
//            {
//                if (!(p.x == pumps[i].x && p.y == pumps[i].y))
//                {
//                    pipes.add(new Pipe(pumps[i].x))
//                }
//            }
        }
        pumps[p.getBest_point()].color = color(0,255,0);

    }

    public void draw()
    {
        background(bg);
        fill(0);
        text(p.toString(), 20, 40);
        for (Pump pump : pumps)
        {
            pump.update();
            pump.draw();
        }
    }

    public void keyPressed()
    {
        if (key == '+')
        {
            System.out.println("plus");
        }
    }

    public void mousePressed()
    {
        if (mouseButton == LEFT)
        {
            System.out.println("left click at: " + mouseX + ", " + mouseY);
        }
    }

    /**
     * Represents a Natural Gas Pump
     */
    private class Pump
    {
        int x, y, r = 0;
        int color;
        ArrayList<Integer> connections;
        int id;
        boolean selected;

        Pump(int x, int y, int r, int id, ArrayList<Integer> connections)
        {
            this.x = x;
            this.y = y;
            this.r = r;
            this.color = color(0);
            this.id = id;
            this.connections = connections;
        }

        void draw()
        {
            fill(this.color);
            ellipse(x,y,r,r);
            fill(0);
            text(id, x+12, y-12);
            stroke(0);
            for (int c : connections)
            {
                line(x,y,pumps[c].x,pumps[c].y);
            }
        }

        void update()
        {
            if (pointBall(mouseX, mouseY, this.x, this.y, this.r))
            {
                if (mousePressed && !pump_selected)
                {
                    this.selected = true;
                    pump_selected = true;
                }
            }
            if (selected)
            {
                if (mousePressed)
                {
                    this.x = mouseX;
                    this.y = mouseY;
                }
            }
        }

        boolean pointBall(int px, int py, int bx, int by, int bSize)
        {
            float xDist = px - bx;
            float yDist = py - by;
            float distance = sqrt((xDist * xDist) + (yDist * yDist));
            return (bSize / 2 > distance);
        }

    }

    /**
     * Represents a Natural Gas Pump
     */
    private class Pipe
    {
        int x, y = 0;
        int color;
        ArrayList<Integer> connections;
        int id;
        boolean selected;

        Pipe(int x, int y, ArrayList<Integer> connections)
        {
            this.x = x;
            this.y = y;
            this.color = color(0);
            this.connections = connections;
        }

        void draw()
        {
            for (int i = 0; i < connections.size()-1; i++)
            {
                line(pumps[connections.get(i)].x, pumps[connections.get(i)].y, pumps[connections.get(i+1)].x, pumps[connections.get(i+1)].y);
            }
        }

        void update()
        {

        }

    }

    public void mouseReleased()
    {
        for (Pump pump : pumps)
        {
            pump.selected = false;
            pump_selected = false;
        }
    }
}