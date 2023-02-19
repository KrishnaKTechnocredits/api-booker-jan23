package pojo.request.createbooking;

import java.util.Objects;

public class Bookingdates {
	public String checkin;
	public String checkout;

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	@Override
	public int hashCode() {
		return Objects.hash(checkin, checkout);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bookingdates other = (Bookingdates) obj;
		return Objects.equals(checkin, other.checkin) && Objects.equals(checkout, other.checkout);
	}
}