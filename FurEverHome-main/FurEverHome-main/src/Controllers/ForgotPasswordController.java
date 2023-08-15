package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Model.Buyer;
import Model.Seller;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import Services.LoginService;

public class ForgotPasswordController {
	
	
    @FXML
    private Label confirmPassLbl;

    @FXML
    private PasswordField confirmPasswordInpt;

    @FXML
    private TextField emailInpt;

    @FXML
    private Label emailLbl;

    @FXML
    private PasswordField newPasswordInpt;

    @FXML
    private Label newPasswordLbl;

    @FXML
    private Button submitBtn;

    @FXML
    void submitPasswordBtn(ActionEvent event) {
    	String emailId = emailInpt.getText();
		String newPassword = newPasswordInpt.getText();
		String confirmPassword = confirmPasswordInpt.getText();
		
		
		try {
		if(newPassword.equals(confirmPassword)) {
		
			if (validateInputFields()) {
			LoginService forgotP = new LoginService(confirmPassword, emailId);
			try {
				Integer sfp = forgotP.updateSellerPassword();
//				Main m = new Main();
//				m.changeScene("login.fxml", null);
				
				if(sfp!=null) {
				Main m1 = new Main();
				m1.changeScene("login.fxml", null);					
			}
//				
					Integer bfp = forgotP.updateBuyerPassword();
				if (bfp!=null) {
					Main m2 = new Main();
					m2.changeScene("login.fxml", null);
				}
				}
			
			
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
		else if(newPassword.isEmpty() || confirmPassword.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all the fields");
			alert.showAndWait();
			clearFields();
		}
		
		
		else if(!newPassword.equals(confirmPassword)) {
		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
		alert.setHeaderText(null);
			alert.setContentText("Both passwords don't match");
			alert.showAndWait();
			clearFields();
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
    
	public void clearFields() {
		emailInpt.setText("");
		newPasswordInpt.setText("");
		confirmPasswordInpt.setText("");
}
	
	public boolean validateInputFields() {
		return true;
	}
	
	public void onLogin() {
		Main m = new Main();
		m.changeScene("login.fxml", null);
	}

}
