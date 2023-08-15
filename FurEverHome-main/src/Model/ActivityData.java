package Model;

public class ActivityData {

	private Integer petBuyerId;
private Integer buyerId;
private String message;
private String activityStatus;
	
	
	public ActivityData(Integer petBuyerId, Integer buyerId, String message, String activityStatus) {
      
		this.petBuyerId = petBuyerId;
		this.buyerId = buyerId;
		this.message = message;
		this.activityStatus = activityStatus;
	}

	public Integer getPetBuyerId() {
		return petBuyerId;
	}


	public void setPetBuyerId(Integer petBuyerId) {
		this.petBuyerId = petBuyerId;
	}


	public Integer getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getActivityStatus() {
		return activityStatus;
	}


	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	
}
