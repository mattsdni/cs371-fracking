/**
 * Created by Matt on 9/26/16.
 *
 */
public class PumpingSite implements PumpingOptimization
{
    @Override
    public boolean getSiteDescription(String siteDescription)
    {
        return false;
    }

    @Override
    public int collectionPoint()
    {
        return 0;
    }

    @Override
    public double minimalTotalCost()
    {
        return 0;
    }

    @Override
    public double stationCost(int v)
    {
        return 0;
    }

    @Override
    public int nextStation(int v)
    {
        return 0;
    }

    @Override
    public String route(int v)
    {
        return null;
    }

    @Override
    public String toString(int v)
    {
        return null;
    }
}
