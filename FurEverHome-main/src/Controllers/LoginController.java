package Controllers;



import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Model.Buyer;
import Model.Seller;
import Services.LoginService;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utilities.Validations;

public class LoginController implements Initializable {
	
	public LoginController () {
		
	}
	
	@FXML
    private Button button;
	
	@FXML
	private ImageView leftimageView;
	
	@FXML
	private ImageView rightimageView;
	
	@FXML
	private TextField emailIdField;
	
	@FXML
	private PasswordField passwordField;
	
    @FXML
    private Hyperlink forgotPasswordBtn;
    
    

	
	public void onLogin() {
		String emailId = emailIdField.getText();
		String password = passwordField.getText();
		if (validateInputFields(emailId, password)) {
			LoginService ls = new LoginService(password, emailId);
			try {
				Seller s = ls.loginSeller();
				Buyer b = ls.loginBuyer();
				if (s != null && b == null) {
					signInSeller(s);
				} else if (b != null && s == null) {
					signInBuyer(b);
				} else if (s != null && b != null) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			        alert.setTitle("Login Alert");
			        alert.setHeaderText("This email id is linked to 'Seller' and 'Buyer' type. \n Choose your Login Type.");
			        ButtonType buyerButton = new ButtonType("Buyer");
			        ButtonType sellerButton = new ButtonType("Seller");
			        alert.getButtonTypes().setAll(buyerButton, sellerButton);
			        alert.showAndWait().ifPresent(res -> {
			            if (res == buyerButton) {
			                System.out.println("Buyer button!");
			                signInBuyer(b);
			            } else if (res == sellerButton) {
			                System.out.println("Seller button!");
			                signInSeller(s);
			            }
			        });
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Login Error");
					alert.setHeaderText("Wrong Email ID or Password entered.");
					alert.showAndWait();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	public void onSignUp() {
		Main m = new Main();
		m.changeScene("register.fxml", null);
	}
	
	public boolean validateInputFields(String emailId, String Password) {
		Validations val = new Validations();
		String finalErrorMsg = "";
		boolean validFields = true;
		String emailIdError = val.validateEmail(emailId);
		String passwordError = val.validatePassword(Password);
		if (emailIdError != null && !emailIdError.trim().equals("")) {
			finalErrorMsg += emailIdError + "\n"; 
			validFields = false;
		}
		if (passwordError != null && !passwordError.trim().equals("")) {
			finalErrorMsg += passwordError + "\n"; 
			validFields = false;
		}
		if (!validFields) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Error");
			alert.setHeaderText(finalErrorMsg);
			alert.showAndWait();
		}
		return validFields;
	}
	
	public void signInBuyer(Buyer buyer) {
		Main m = new Main();
		m.changeScene("buyerDashboard.fxml", buyer);
	}
	
	public void signInSeller(Seller seller) {
		Main m = new Main();
		m.changeScene("SellerDashboard.fxml", seller);
	}
	
    @FXML
    void handleForgotPassword(ActionEvent event) {
    	Main m = new Main();
           m.changeScene("ForgotPassword.fxml",null);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		File img = new File("src/images/imgLogo.png");
		 Image image = new Image(img.toURI().toString());
		 leftimageView.setImage(image);
		 
		 File wordImage = new File("src/images/biggest.png");
		 Image imageRight = new Image(wordImage.toURI().toString());
		 rightimageView.setImage(imageRight);
		
	} 
}
