package jpastart.reserve.application;

import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class GetHotelSummaryServiceTest extends JpaTestBase {

    private GetHotelSummaryService hotelSummaryService = new GetHotelSummaryService();

    @Test
    public void notFound() throws Exception {
        try {
            hotelSummaryService.getHotelSummary("NO-HOTEL-ID");
            fail();
        } catch (HotelNotFoundException e) {
        }
    }

    @Test
    public void get() throws Exception {
        HotelSummary hotelSummary = hotelSummaryService.getHotelSummary("H100-01");
        assertThat(hotelSummary.getHotel(), notNullValue());
        assertThat(hotelSummary.getLatestReviews().size(), equalTo(3));
        assertThat(hotelSummary.getLatestReviews().get(0).getId(), equalTo(6L));
    }
}
