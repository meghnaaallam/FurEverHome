package Services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Buyer;
import utilities.Constants;
import utilities.DbConnection;

public class BuyerService {
	private Buyer buyerService;
	
	public BuyerService(Buyer buyerService) {
		this.buyerService = buyerService;
	}
	
	public BuyerService() {
		// TODO Auto-generated constructor stub
	}

	public void addBuyerToDb()throws SQLException {
		String sql = "INSERT INTO `buyer`(`firstName`, `lastName`, `emailId`, `password`, `city`, `state`)"
				+ "VALUES ('" + buyerService.getfName() + "','" + buyerService.getlName() + "','" + buyerService.getEmailId() + "','" + buyerService.getPassword() + "','" + buyerService.getCity() + "','" +buyerService.getState() + "')";
		DbConnection.query(sql);
	}
	
	public void setStatus(ArrayList<Integer> arrList, String status, int petId) {
		if (arrList != null && arrList.size() > 0) {
			String sql = "update petbuyer set status = '" + status + "' where id = "+ petId + " and buyerId in (";
			for(int i=0; i<arrList.size(); i++) {
				sql += arrList.get(i);
				if(i != arrList.size()-1) {
					sql += ",";
				}else {
					sql += ");";
				}
			}
			System.out.println(sql);
			DbConnection.query(sql);
			
		}
	}
	
	public Buyer buyerExist(String emailId) throws SQLException {
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
			retreivedPassword = resultSet.getString("password");
			fName = resultSet.getString("firstName");
			buyerId = resultSet.getString("buyerId");
			lName = resultSet.getString("lastName");
			state = resultSet.getString("state");
			retreivedEmailId = resultSet.getString("emailId");
			city = resultSet.getString("city");
		}
		if(retreivedEmailId != null && !retreivedEmailId.trim().equals("")) {
			b = new Buyer(fName, lName, emailId, retreivedPassword, state, city, Constants.BUYER);
		}
		return b;
	}
	
}
