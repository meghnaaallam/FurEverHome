package Services;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Buyer;
import Model.Seller;
import utilities.Constants;
import utilities.DbConnection;

public class SellerService {
	private Seller sellerService;
	
	public SellerService() {
		
	}
	
	public SellerService(Seller sellerService) {
		this.sellerService = sellerService;
	}
	
	public void addSellerToDb()throws SQLException {
		String sql = "INSERT INTO `seller`(`firstName`, `lastName`, `emailId`, `password`, `city`, `state`)"
				+ "VALUES ('" + sellerService.getfName() + "','" + sellerService.getlName() + "','" + sellerService.getEmailId() + "','" + sellerService.getPassword() + "','" + sellerService.getCity() + "','" + sellerService.getState() + "')";
		DbConnection.query(sql);
	}
	
	public Seller sellerExist(String emailId) throws SQLException {
		Seller s = null;
		String retreivedPassword = "";
		String fName = "";
		String sellerId = "";
		String lName = "";
		String retreivedEmailId = "";
		String state = "";
		String city = "";
		
		String sql = "select * from seller where emailid = '" + emailId + "';";
		ResultSet resultSet = DbConnection.selectQuery(sql);
		while (resultSet.next()) {
			retreivedPassword = resultSet.getString("password");
			retreivedEmailId = resultSet.getString("emailId");
			fName = resultSet.getString("firstName");
			sellerId = resultSet.getString("sellerId");
			lName = resultSet.getString("lastName");
			state = resultSet.getString("state");
			city = resultSet.getString("city");
		}
		if(retreivedEmailId != null && !retreivedEmailId.trim().equals("")) {
			s = new Seller(fName, lName, emailId, retreivedPassword, state, city, Constants.SELLER);
		}
		return s;
	}
	
}