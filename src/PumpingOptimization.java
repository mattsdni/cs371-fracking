
/**
 * @author dr. blaha
 *
 */
public interface PumpingOptimization {

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
    public abstract boolean getSiteDescription(String siteDescription);

    /**
     * Returns a pumping station that will be a collection point for all
     * pumping stations and minimize the overall loss of natural gas.
     * @return pumping station that is the best collection point
     */
    public abstract int collectionPoint();

    /**
     * Assuming that all sites pump the same amount of gas, compute the
     * minimal total "cost" (i.e., percentage of gas lost) to pump gas to
     * an optimal collection point.
     * Each time natural gas is sent throgh a pipeline 1% if the gas is lost.
     * @return the minimal total cost (percentage of gas lost) to send gas to an
     *         optimal collection point.
     */
    public abstract double minimalTotalCost();

    /**
     * Returns the the cost (percentage of gas lost) by pumping station v, when
     * sending gas to the collection point.
     * @param v is the pumping station
     * @return cost (percentage of gas lost) to pump gas from station v to the
     *         collection point.
     */
    public abstract double stationCost(int v);

    /**When shipping gas from v to the collection point pumping station v
     * will send gas to the pumping station returned by this method.
     * @param v is a pumping station number
     * @return the pumping station v sends gas to (in order to reach the
     *         collection point), or -1 if v is the collection point
     */
    public abstract int nextStation(int v);

    /** The route gas travels to get from pumping station v to the
     *  collection point.
     * @param v is a pumping station
     * @return a String that is the route from v to the collection point.
     *         If v is the collection point return the empty string.
     */
    public abstract String route(int v);

    /** Returns a matrix representation of the entire pumping site.
     *  If pumping well i is connected to pumping well j then m[i][j]=1,
     *  otherwise m[i][j]=0.
     * @return a string that is the matrix representation of the pumping site.
     */
    public abstract String toString();

}