package Services;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Buyer;
import Model.Seller;
import utilities.Constants;
import utilities.DbConnection;

public class LoginService {
	
	private String emailId;
	private String password;
	
	public LoginService(String password, String emailId) {
		this.emailId = emailId;
		this.password = password;
	}
	
	public Seller loginSeller() throws SQLException {
		Seller s = null;
		String retreivedPassword = "";
		String fName = "";
		String sellerId = "";
		String lName = "";
		String state = "";
		String city = "";
		String retreivedEmailId = "";
		
		String query = "select * from seller where emailId ='" + emailId + "'";
		ResultSet resultSet = DbConnection.selectQuery(query);
		
		while (resultSet.next()) {
			retreivedEmailId = resultSet.getString("emailId");
			retreivedPassword = resultSet.getString("password");
			fName = resultSet.getString("firstName");
			sellerId = resultSet.getString("sellerId");
			lName = resultSet.getString("lastName");
			state = resultSet.getString("state");
			city = resultSet.getString("city");
		}
		
		if (validatePassword(password, retreivedPassword)) {
			if(retreivedEmailId != null && !retreivedEmailId.trim().equals("")) {
				s = new Seller(fName, lName, emailId, password, state, city, Constants.SELLER);
				s.setSellerId(Integer.parseInt(sellerId));
			}
		}
		return s;
		
	}
	
	public Buyer loginBuyer() throws SQLException {
		Buyer b = null;
		String retreivedPassword = "";
		String fName = "";
		String buyerId = "";
		String lName = "";
		String state = "";
		String city = "";
		String retreivedEmailId = "";
		
		String query = "select * from buyer where emailId ='" + emailId + "'";
		ResultSet resultSet = DbConnection.selectQuery(query);
		while (resultSet.next()) {
			retreivedEmailId = resultSet.getString("emailId");
			retreivedPassword = resultSet.getString("password");
			fName = resultSet.getString("firstName");
			buyerId = resultSet.getString("buyerId");
			lName = resultSet.getString("lastName");
			state = resultSet.getString("state");
			city = resultSet.getString("city");
		}
		
		if (validatePassword(password, retreivedPassword)) {
			if(retreivedEmailId != null && !retreivedEmailId.trim().equals("")) {
				b = new Buyer(fName, lName, emailId, password, state, city, Constants.BUYER);
				b.setBuyerId(Integer.parseInt(buyerId));
			}
		}
		return b;
	}
	
	public Integer updateSellerPassword() throws SQLException {
		String squery ="update seller set password = '" + password+"'"+" where emailId ='" + emailId + "'";
	System.out.println(squery);
		Integer resultSet = DbConnection.updateQuery(squery);
		if(resultSet!=null) {
			return 1;
		}
		else {
		return 0;
		}
}
	
	public Integer updateBuyerPassword() throws SQLException {
		String squery ="update buyer set password = '" + password+"'"+" where emailId ='" + emailId + "'";
	System.out.println(squery);
		Integer resultSet = DbConnection.updateQuery(squery);
		if(resultSet!=null) {
			return 1;
		}
		else {
		return -1;
		}
		
		
	}
	
	public boolean validatePassword(String password, String rPassword) {
		if(password != null && rPassword !=null &&
				password.trim().equals(rPassword.trim())) {
			return true;
		}
		return false;
	}
}
