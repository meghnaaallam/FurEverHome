package Model;

public class PetBuyer {
	private int petId;
	private int buyerId;
	private String msg;
	private String status;
	
	public PetBuyer(int petId, int buyerId, String msg, String status) {
		
		this.petId = petId;
		this.buyerId = buyerId;
		this.msg = msg;
		this.status = status;
	}

	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
