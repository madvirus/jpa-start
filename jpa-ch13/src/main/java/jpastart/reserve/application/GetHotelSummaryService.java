package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.Hotel;
import jpastart.reserve.model.Review;
import jpastart.reserve.repository.HotelRepository;
import jpastart.reserve.repository.ReviewRepository;

import java.util.List;

public class GetHotelSummaryService {

    private HotelRepository hotelRepository = new HotelRepository();
    private ReviewRepository reviewRepository = new ReviewRepository();

    public HotelSummary getHotelSummary(String hotelId) {
        try {
            Hotel hotel = hotelRepository.find(hotelId);
            if (hotel == null) throw new HotelNotFoundException();
            List<Review> latestReviews =
                    reviewRepository.findByHotel(hotel, 0, 3);
            return new HotelSummary(hotel, latestReviews);
        } finally {
            EMF.closeCurrentEntityManager();
        }
    }
}
