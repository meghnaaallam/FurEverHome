package Controllers;

import Model.Seller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.Buyer;
import Services.BuyerService;
import Services.SellerService;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import utilities.Constants;
import utilities.Validations;

public class RegisterController implements Initializable {
	
	@FXML
	private PasswordField password;
	
	@FXML
	private PasswordField cPassword;
	
	@FXML
	private TextField fName;
	
	@FXML
	private TextField lName;
	
	@FXML
	private TextField emailId;
	
	@FXML
	private TextField city;
	
	@FXML
	private ImageView rightImageView;
	
	@FXML
	private ImageView leftImageView;
	
	@FXML
	private TextField state;
	
	@FXML
	private RadioButton sellerType;
	
	@FXML
	private RadioButton buyerType;
	
	public void onRegister() {
		
		String firstName = fName.getText();
		String lastName = lName.getText();
		String emailID = emailId.getText();
		String passwordField = password.getText();
		String stateField = state.getText();
		String cityField = city.getText();
		String confirmPassword = cPassword.getText();
		
		if (validateInputFields(firstName, lastName, emailID, 
				passwordField, confirmPassword, cityField, stateField)) { 
			if(sellerType.isSelected()) {
				if(!sellerPresent(emailID)) {
					String type = Constants.SELLER;
					Seller seller = new Seller(firstName, lastName, emailID, passwordField, stateField, cityField, type);
					SellerService ss = new SellerService(seller);
					try{
						ss.addSellerToDb();
						registeredSuccessfully();
						clearAllFields();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
		} else if(buyerType.isSelected()) {
			if(!buyerPresent(emailID)) {
				String type = Constants.BUYER;
				Buyer buyer = new Buyer(firstName, lastName, emailID, passwordField, stateField, cityField, type);
				BuyerService bs = new BuyerService(buyer);
				try{
					bs.addBuyerToDb();
					clearAllFields();
					registeredSuccessfully();
				}catch(Exception e) {
					e.printStackTrace();
					}	
				}
			} else {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Choose type of USER either Seller or Buyer.");
				alert.showAndWait();
			}
	
		}
		
	}

	private void registeredSuccessfully() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success Message");
		alert.setHeaderText(null);
		alert.setContentText("User added successfully");	
		Optional<ButtonType> option = alert.showAndWait();
		login();
	}
	
	public void clearAllFields() {
		fName.setText("");
		lName.setText("");
		emailId.setText("");
		password.setText("");
		state.setText("");
		city.setText("");
		cPassword.setText("");
		buyerType.setSelected(false);
		sellerType.setSelected(false);
	}
	
	public void onLogin() {
		login();
	}
	
	public void login() {
		Main m = new Main();
		m.changeScene("login.fxml", null);
	}
	
	private boolean validateInputFields(String fName, String lName, 
			String emailId ,String password, String cnfrmPassword, 
			String city, String state) {
		boolean formValid = true;
		String finalMsg = "";
		Validations val = new Validations();
		
		String validateFname = val.validateName(fName, "First Name");
		if (validateFname != null && !validateFname.trim().equals("")) {
			finalMsg += validateFname +"\n"; 
			formValid = false;
		}
		String validateLname = val.validateName(lName, "Last Name");
		if (validateLname != null && !validateLname.trim().equals("")) {
			finalMsg += validateLname +"\n"; 
			formValid = false;
		}
		
		String emailValidate = val.validateEmail(emailId);
		if(emailValidate != null && !emailValidate.trim().equals("")) {
			finalMsg += emailValidate +"\n"; 
			formValid = false;
		} 
		String validatePassword = val.validatePassword(password);
		if (validatePassword != null && !validatePassword.trim().equals("")) {
			finalMsg += validatePassword +"\n"; 
			formValid = false;
		}
		String validateConfirmPassword = val.validateConfirmPassword(password, cnfrmPassword);
		if (validateConfirmPassword != null && !validateConfirmPassword.trim().equals("")) {
			finalMsg += validateConfirmPassword +"\n"; 
			formValid = false;
		}
		String validateCity = val.validateName(city, "City");
		if (validateCity != null && !validateCity.trim().equals("")) {
			finalMsg += validateCity +"\n"; 
			formValid = false;
		}
		String validateState = val.validateName(state, "State");
		if (validateState != null && !validateState.trim().equals("")) {
			finalMsg += validateState +"\n"; 
			formValid = false;
		} 
		
		if (!formValid) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Form");
			alert.setHeaderText(null);
			alert.setContentText(finalMsg);
			alert.showAndWait();
		}
		return formValid;
	}
	
	public boolean sellerPresent(String emailId) {
		SellerService ss = new SellerService();
		Seller s = null;
		try {
			s = ss.sellerExist(emailId);
			if (s != null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Email Id Exist");
				alert.setHeaderText(null);
				alert.setContentText(emailId + " exist in our database against Seller profile.");
				alert.showAndWait();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean buyerPresent(String emailId) {
		BuyerService bs = new BuyerService();
		Buyer b = null;
		try {
			b = bs.buyerExist(emailId);
			if (b != null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Email Id Exist");
				alert.setHeaderText(null);
				alert.setContentText(emailId + " exist in our database against Buyer profile.");
				alert.showAndWait();
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ToggleGroup toggleGroup = new ToggleGroup();
		sellerType.setToggleGroup(toggleGroup);
		buyerType.setToggleGroup(toggleGroup);
		
		File img = new File("src/images/imgLogo.png");
		 Image image = new Image(img.toURI().toString());
		 leftImageView.setImage(image);
		 
		 File wordImage = new File("src/images/biggest.png");
		 Image imageRight = new Image(wordImage.toURI().toString());
		 rightImageView.setImage(imageRight);
		
	} 
}
