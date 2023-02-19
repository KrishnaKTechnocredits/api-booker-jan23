package pojo.request.createbooking;

import java.util.Objects;

public class CreateBookingRequest {
	public String firstname;
	public String lastname;
	public int totalprice;
	public boolean depositpaid;
	public Bookingdates bookingdates;
	public String additionalneeds;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	public boolean isDepositpaid() {
		return depositpaid;
	}
	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}
	public Bookingdates getBookingdates() {
		return bookingdates;
	}
	public void setBookingdates(Bookingdates bookingdates) {
		this.bookingdates = bookingdates;
	}
	public String getAdditionalneeds() {
		return additionalneeds;
	}
	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}
	
	@Override
	public int hashCode() {
		return firstname.length();
	}
	
	@Override
	public boolean equals(Object obj) {
		CreateBookingRequest other = (CreateBookingRequest) obj;
		return additionalneeds.equals(other.additionalneeds)
				&& Objects.equals(bookingdates, other.bookingdates) && depositpaid == other.depositpaid
				&& Objects.equals(firstname, other.firstname) && Objects.equals(lastname, other.lastname)
				&& totalprice == other.totalprice;
	}
	
	
}