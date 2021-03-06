package ShuttleSystemBackend.services;

import ShuttleSystemBackend.domain.TripDetails;
import ShuttleSystemBackend.factory.TripFactory;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by User on 2017/09/07.
 */
public class TripDetailServTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private TripService service;

    @Test
    public void testCreate() throws Exception {
        //repository.deleteAll();

        TripDetails tripDetails = TripFactory.createTrip("admin","test","test");
        TripDetails savedTripDetails =service.create(tripDetails);

        assertNotNull("CREATE TEST", savedTripDetails);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testReadAll() throws Exception {
        Iterable<TripDetails> trips =  service.readAll();

        assertNotNull("READ TEST",trips);
    }

    @Test(dependsOnMethods = "testReadAll")
    public void testUpdate() throws Exception {
        TripDetails tripDetailsFound = service.readById(1L);
        TripDetails updateTripDetails = new TripDetails.TripBuilder()
                .copy(tripDetailsFound)
                .destination("service testupdate")
                .build();
        TripDetails updatedTripDetails =service.create(updateTripDetails);
        Assert.assertSame("UPDATE TEST", updateTripDetails.getDestination(), updatedTripDetails.getDestination());
    }

    @Test(dependsOnMethods = "testUpdate")
    public void testDelete() throws Exception {
        TripDetails foundTripDetails = service.readById(3L);
        if(foundTripDetails !=null) {
            assertNotNull("BEFORE DELETE TEST", foundTripDetails);
            service.delete(foundTripDetails);
            TripDetails deletedTripDetails = service.readById(3L);

            assertNull("DELETE TEST", deletedTripDetails);
        }

    }
}
