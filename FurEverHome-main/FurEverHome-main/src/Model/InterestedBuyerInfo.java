package Model;

public class InterestedBuyerInfo {
	
	private int buyerId;
	private String buyerFName;
	private String buyerLName;
	private String status;
	private String msg;
	
	public InterestedBuyerInfo(int buyerId, String buyerFName, String buyerLName, String status, String msg) {
		this.buyerId = buyerId;
		this.buyerFName = buyerFName;
		this.buyerLName = buyerLName;
		this.status = status;
		this.msg = msg;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerFName() {
		return buyerFName;
	}

	public void setBuyerFName(String buyerFName) {
		this.buyerFName = buyerFName;
	}

	public String getBuyerLName() {
		return buyerLName;
	}

	public void setBuyerLName(String buyerLName) {
		this.buyerLName = buyerLName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	

}
