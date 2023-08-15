package Services;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.PetBuyer;
import Model.PetData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.Constants;
import utilities.DbConnection;

public class PetService {
	
	public PetService() {
		
	}
	
	public ObservableList<PetData> fetchBuyerPetDashboardInfo(int buyerId) throws SQLException {
		ObservableList<PetData> petDataList = FXCollections.observableArrayList();

		String query = "select * from petinfo WHERE id NOT IN (\r\n"
				+ "    SELECT id\r\n"
				+ "    FROM petBuyer\r\n"
				+ "    WHERE buyerId = \r\n"
				+ buyerId +");";

		System.out.println("query " + query);
		
		
		ResultSet resultSet = DbConnection.selectQuery(query);
		
		
		if(resultSet != null) {
			while(resultSet.next()) {
				int petId = Integer.parseInt(resultSet.getString("id"));
				Integer age = Integer.parseInt(resultSet.getString("age"));
				String petCategory = resultSet.getString("petCategory");
				String petName = resultSet.getString("petName");
				String sex = resultSet.getString("sex");
				Integer price = (resultSet.getString("price") != null)?( Integer.parseInt(resultSet.getString("price"))): 0;
				String choiceOfSelection = resultSet.getString("sellerChoice");
				String breed = resultSet.getString("breed");
				int sellerId = Integer.parseInt(resultSet.getString("sellerId"));	

				PetData pd = new PetData(petName, age, breed, price, choiceOfSelection, petCategory, sex, null);
				pd.setPetId(petId);
				pd.setSellerId(sellerId);
				petDataList.add(pd);
			}
		}
		return petDataList;
	}

	public void addBuyerInterest(String msg, int petId, int buyerId) {
		String sql = "INSERT INTO `petbuyer`(`id`, `buyerId`, `status`, `buyerMessage`)"
				+ "VALUES ('" + petId + "','" + buyerId + "','" + Constants.PENDING + "','" + msg + "')";
		System.out.println("query " + sql);
		DbConnection.query(sql);
	}
	

	public ObservableList<PetData> fetchBuyerActivityData(int buyerId) throws SQLException{
		ObservableList<PetData> petDataList = FXCollections.observableArrayList();
		String query = "SELECT pi.*\r\n"
				+ "FROM petinfo pi\r\n"
				+ "JOIN petbuyer pb ON pi.id = pb.id\r\n"
				+ "WHERE pb.buyerId =" + buyerId+ ";";
		System.out.println("query " + query);
		ResultSet resultSet = DbConnection.selectQuery(query);
		
		if(resultSet != null) {
			while(resultSet.next()) {
				int petId = Integer.parseInt(resultSet.getString("id"));
				Integer age = Integer.parseInt(resultSet.getString("age"));
				String petCategory = resultSet.getString("petCategory");
				String petName = resultSet.getString("petName");
				String sex = resultSet.getString("sex");
				Integer price = (resultSet.getString("price") != null)?( Integer.parseInt(resultSet.getString("price"))): 0;
				String choiceOfSelection = resultSet.getString("sellerChoice");
				String breed = resultSet.getString("breed");
				int sellerId = Integer.parseInt(resultSet.getString("sellerId"));
				 
				
				PetData pd = new PetData(petName, age, breed, price, choiceOfSelection, petCategory, sex, null);
				pd.setPetId(petId);
				pd.setSellerId(sellerId);
				petDataList.add(pd);
			}
			
			
		}
		return petDataList;
	}
	
	public PetBuyer retrievePetBuyerData(int petId, int buyerId) throws NumberFormatException, SQLException {
		PetBuyer pb = null;		
		String sql = "select * from petbuyer where id = "+petId+" and buyerId = "+buyerId+";";
		System.out.println(sql);
		ResultSet resultSet = DbConnection.selectQuery(sql);
		if(resultSet != null) {
			while(resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString("id"));
				int bId = Integer.parseInt(resultSet.getString("buyerId"));
				String msg = resultSet.getString("buyerMessage");
				String status = resultSet.getString("status");
				pb = new PetBuyer(id, bId, msg, status);
				return pb;
			}
		}
		return pb;
		
	}
	
	public ResultSet getPetAndBuyerInfo(int sellerId) {
		ResultSet resultSet = null;
		String query = "SELECT pb.id,\r\n"
				+ "       pb.buyerId,\r\n"
				+ "       pb.status,\r\n"
				+ "       pb.buyerMessage,\r\n"
				+ "       pi.petCategory,\r\n"
				+ "       pi.petName,\r\n"
				+ "       pi.age,\r\n"
				+ "       pi.breed,\r\n"
				+ "       pi.sellerChoice,\r\n"
				+ "       pi.sellerId,\r\n"
				+ "       pi.sex,\r\n"
				+ "       pi.price,\r\n"
				+ "       b.firstName,\r\n"
				+ "       b.lastName\r\n"
				+ "FROM petBuyer pb\r\n"
				+ "JOIN petinfo pi ON pb.id = pi.id\r\n"
				+ "JOIN buyer b ON pb.buyerId = b.buyerId\r\n"
				+ "WHERE pb.status IN ('Pending', 'Rejected', 'Approved') AND pi.sellerId = "+sellerId+";";
		resultSet = DbConnection.selectQuery(query);
		return resultSet;
	}
	
	public void updatePetDetails(PetData pd)throws NumberFormatException, SQLException {
		String query = "update petinfo set petName = '" + pd.getPetName() + "', age = " + pd.getAge() +
				", breed = '" + pd.getBreed() + "', sex = '" + pd.getSex() + "'";
		if(pd.getPrice() > 0) {
			query += ", price = " + pd.getPrice();
		}
		query = query + " where id = " + pd.getPetId() + ";";
		System.out.println(query);
		
		DbConnection.query(query);
	}
	
	public void deletePetDetails(PetData pd) throws SQLException {
		String query = "Delete from petinfo where id = " + pd.getPetId() + ";";
		DbConnection.query(query);
	}

}
