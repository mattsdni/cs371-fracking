import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Matt on 9/28/2016.
 */
public class PumpingSiteTest {

    PumpingSite p = new PumpingSite();

    @org.junit.Test
    public void getSiteDescription() throws Exception {
        assertTrue(p.getSiteDescription("site.txt"));
        System.out.println(p);


    }

    @org.junit.Test
    public void testCollectionPoint()
    {
        p.getSiteDescription("site.txt");
        System.out.println(p.collectionPoint());
    }

}