import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Matt on 9/26/16.
 *
 */
public class PumpingSite implements PumpingOptimization
{
    private int[][] graph_matrix;
    private ArrayList<Integer>[] graph_list;
    private int best_point = -1;
    private int best_distance = Integer.MAX_VALUE;
    private int[] best_distances;
    private int[] parents;

    /**
     * Read the text file siteDescription that contains a description of the
     * pumping site. The format of the file is specified in the Program 2
     * assignment. Save the data in a data structure of your choice. This
     * information is needed to implement the methods
     * in the interface PumpingOptimization.
     *
     * @param siteDescription name of the text file that contains a
     *        description of the pumping site.
     *        The format for the text file is in the Program 1 assignment.
     * @return return true if the file was found and false otherwise
     */
    @Override
    public boolean getSiteDescription(String siteDescription)
    {
        try {
            FileReader fileReader = new FileReader(siteDescription);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int num_lines = Integer.parseInt(bufferedReader.readLine().trim());
            graph_matrix = new int[num_lines][num_lines];
            graph_list = (ArrayList<Integer>[])new ArrayList[num_lines];
            best_distances = new int[graph_list.length];
            parents = new int[graph_list.length];
            for (int i = 0; i < num_lines; i++) {
                String[] edges = bufferedReader.readLine().split("\\s+");
                for (int j = 0; j < edges.length; j++) {
                    Integer edge = Integer.parseInt(edges[j]);
                    graph_matrix[i][edge] = 1;
                    if (graph_list[i] == null) {
                        graph_list[i] = new ArrayList<>();
                    }
                    graph_list[i].add(edge);
                }
            }
            bufferedReader.close();
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Returns a pumping station that will be a collection point for all
     * pumping stations and minimize the overall loss of natural gas.
     * @return pumping station that is the best collection point
     */
    @Override
    public int collectionPoint()
    {
        int best_point = -1; // the best point to collect the gas
        int best_distance = Integer.MAX_VALUE; // the shortest distance the gas will travel
        int[] distances = new int[graph_list.length]; // the distance from each pump to the collection point
        int[] parents  = new int[graph_list.length]; // storage to remember who is connected to who
        boolean[] seen = new boolean[graph_list.length]; // storage to remember which stations have been looked at
        Queue<Integer> q = new LinkedList<>();

        // perform breadth first search from each node
        for (int node = 0; node < graph_list.length; node++)
        {
            int distance = 0; // start total path traveled to 0
            q.add(node); // queue up the first node
            seen[0] = true; // look at the first node
            while (!q.isEmpty()) // explore the whole graph
            {
                int i = q.remove(); // get the current node
                for (Integer j : graph_list[i]) // for each node connected to this node
                {
                    if (!seen[j]) // if have not yet seen this node
                    {
                        q.add(j); // add this node to the queue
                        seen[j] = true; // remember that this node has been seen
                        distances[j] = distances[i] + 1; // keep track of total distance to get here
                        distance += distances[j];  // compute total path length for each starting position
                        parents[j] = i; // remember who the parent of this node is
                    }
                }
            }
            distances = new int[graph_list.length]; // reset value for next pass
            seen = new boolean[graph_list.length]; // reset value for next pass
            parents  = new int[graph_list.length]; // reset value for next pass

            if (distance < best_distance) // if it's better to start at this pump, update values
            {
                best_distance = distance;
                best_point = node;
                this.best_distances = distances; // set global for other functions to use
                this.parents = parents;
            }
        }
        this.best_point = best_point; // set global for other functions to use
        this.best_distance = best_distance; // set global for other functions to use
        return best_point;
    }

    /**
     * Assuming that all sites pump the same amount of gas, compute the
     * minimal total "cost" (i.e., percentage of gas lost) to pump gas to
     * an optimal collection point.
     * Each time natural gas is sent throgh a pipeline 1% if the gas is lost.
     * @return the minimal total cost (percentage of gas lost) to send gas to an
     *         optimal collection point.
     */
    @Override
    public double minimalTotalCost()
    {
        return 1 - Math.pow(.99, this.best_distance);
    }

    /**
     * Returns the the cost (percentage of gas lost) by pumping station v, when
     * sending gas to the collection point.
     * @param v is the pumping station
     * @return cost (percentage of gas lost) to pump gas from station v to the
     *         collection point.
     */
    @Override
    public double stationCost(int v)
    {
        return 1 - Math.pow(.99, best_distances[v]);
    }

    /**When shipping gas from v to the collection point, pumping station v
     * will send gas to the pumping station returned by this method.
     * @param v is a pumping station number
     * @return the pumping station v sends gas to (in order to reach the
     *         collection point), or -1 if v is the collection point
     */
    @Override
    public int nextStation(int v)
    {
        if (v == this.best_point)
            return -1;
        return parents[v];
    }

    /** The route gas travels to get from pumping station v to the
     *  collection point.
     * @param v is a pumping station
     * @return a String that is the route from v to the collection point.
     *         If v is the collection point return the empty string.
     */
    @Override
    public String route(int v)
    {
        String route = "";
        while (v != best_point)
        {
            route += parents[v] + " ";
            v = parents[v];
        }
        return route;
    }

    /** Returns a matrix representation of the entire pumping site.
     *  If pumping well i is connected to pumping well j then m[i][j]=1,
     *  otherwise m[i][j]=0.
     * @return a string that is the matrix representation of the pumping site.
     */
    public String toString()
    {
        String s = "";
        s += "Matrix\n";
        for (int i = 0; i < graph_matrix.length; i++)
        {
            for (int j = 0; j < graph_matrix.length; j++)
            {
                s += graph_matrix[i][j] + " ";
            }
            s += "\n";
        }
        s += "Adjacency List\n";
        for (int i = 0; i < graph_list.length; i++)
        {
            for (int j = 0; j < graph_list[i].size(); j++)
            {
                s += graph_list[i].get(j) + " ";
            }
            s += "\n";
        }
        return s;
    }

    int pumpCount()
    {
        return graph_list.length;
    }

    /**
     * @param id of the pump requested
     * @return a list of pumps that this is connected it
     */
    ArrayList<Integer> getPumpConnections(int id)
    {
        return graph_list[id];
    }

    public int getBest_point()
    {
        return this.best_point;
    }
}
