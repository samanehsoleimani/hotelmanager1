package Commons;

public class Room1 {
	private int RoomNumber;
	private String Status;
	private String GuestsName;
	private String Bed;

	
	public String getBed() {
	    return Bed;  // تبدیل عدد به رشته و افزودن " Bed"
	}

	public void setBed(String bed) {
	    this.Bed = bed;
	}

	public int getRoomNumber() {
		return RoomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		RoomNumber = roomNumber;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getGuestsName() {
		return GuestsName;
	}
	public void setGuestsName(String guestsName) {
		GuestsName = guestsName;
	}
	
	
	
	

}
