package jpastart.reserve.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MonChargeId implements Serializable {
    @Column(name = "hotel_id")
    private String hotelId;
    @Column(name = "year_mon")
    private String yearMon;

    public MonChargeId() {}

    public MonChargeId(String hotelId, String yearMon) {
        if (hotelId == null)
            throw new IllegalArgumentException("illegal hotelId");
        if (yearMon == null)
            throw new IllegalArgumentException("illegal yearMon");
        this.hotelId = hotelId;
        this.yearMon = yearMon;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getYearMon() {
        return yearMon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MonChargeId that = (MonChargeId) o;

        if (!hotelId.equals(that.hotelId)) return false;
        return yearMon.equals(that.yearMon);
    }

    @Override
    public int hashCode() {
        int result = hotelId.hashCode();
        result = 31 * result + yearMon.hashCode();
        return result;
    }
}
