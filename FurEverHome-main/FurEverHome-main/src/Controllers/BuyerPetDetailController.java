package Controllers;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import Model.Buyer;
import Model.PetBuyer;
import Model.PetData;
import Services.PetService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import utilities.Constants;

public class BuyerPetDetailController {
	
	@FXML
	private Label nameLbl;  
		
	@FXML
	private Label ageLbl;
	
	@FXML
	private Label categoryLbl;
	
	@FXML
	private Label breedLbl;
	
	@FXML
	private Label sexLbl;
	
	@FXML
	private Label priceLbl;
	
	@FXML
	private Label price;
	
	@FXML
	private Label statusLbl;
	
	@FXML
	private Label statusLblInput;
	
	@FXML
	private Button adoptAndBuy;
	
	@FXML
	private Button buyerSubmitBtn;
	
	@FXML
	private Button buyerCancelMsgbtn;
	
	@FXML
	private AnchorPane messagePane;
	
    @FXML
    private List<File> showImage;
	
	@FXML
	private TextArea buyerMessage;
	
	private PetData petData;
	private Buyer buyer;
	private PetBuyer petBuyer;
	
	public void initData(PetData petData, Buyer buyer) {
		this.petData = petData;
		this.buyer = buyer;
		setPetData();
		getPetBuyerDetails();
		if(petBuyer != null) {
			setPetBuyerDetails();
			messagePane.setVisible(true);
			setStatus(petBuyer.getStatus(), true);
		}
		else {
			setPriceVisibility();
			messagePane.setVisible(false);
			setStatus("", false);
		}
		showMessagePane();
	}
	
	public void setPetData() {
		priceLbl.setText(petData.getPrice() + "");
		sexLbl.setText(petData.getSex());
		breedLbl.setText(petData.getBreed());
		categoryLbl.setText(petData.getPetCategory());
		ageLbl.setText(petData.getAge() + "");
		nameLbl.setText(petData.getPetName());
	}

	public void setPriceVisibility() {
		if(petData.getPrice() == 0) {
			priceLbl.setVisible(false);
			price.setVisible(false);
			adoptAndBuy.setText("Adopt");
		}else {
			adoptAndBuy.setText("Buy");
		}
	}
	
	public void showMessagePane() {
		adoptAndBuy.setOnMouseClicked(event ->{
			if(event.getButton()== MouseButton.PRIMARY) {
				messagePane.setVisible(true);
			}
		});
	}
	
	public void onCancel() {
		buyerMessage.setText("");
		messagePane.setVisible(false);
	}
	
	public void onSubmit() {
		String msg = buyerMessage.getText();
		if(validateMessage(msg)) {
			submitBuyerInterest(msg);
			setStatus(Constants.PENDING, true);
		}else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Writing a personalized message to seller increases your chances.\n Press OK to write the message and Cancel to submit without message.");
			Optional<ButtonType> option = alert.showAndWait();
			if(option.get().equals(ButtonType.CANCEL)) {
				submitBuyerInterest(msg);
				setStatus(Constants.PENDING, true);
			}
			
		}
		
	}
	
	public boolean validateMessage(String msg) {
		if(msg.length() > 0) {
			return true;
		}
		return false;
	}
	
	public void submitBuyerInterest(String msg) {
		PetService ps = new PetService();
		ps.addBuyerInterest(msg, petData.getPetId(), buyer.getBuyerId());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Seller now knows you are interested!");
		Optional<ButtonType> option = alert.showAndWait();
		setBuyerInterest();
	}
	
	public void setBuyerInterest() {
		adoptAndBuy.setVisible(false);
		buyerCancelMsgbtn.setVisible(false);
		buyerSubmitBtn.setVisible(false);
		buyerMessage.setEditable(false);
	}
	public void getPetBuyerDetails() {
		PetService ps = new PetService();
		try {
			petBuyer = ps.retrievePetBuyerData(petData.getPetId(), buyer.getBuyerId());
			System.out.println(petBuyer);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPetBuyerDetails() {
		setBuyerInterest();
		buyerMessage.setText(petBuyer.getMsg());
	}
	
	public void setStatus(String status, boolean visibility) {
		statusLbl.setVisible(visibility);
		statusLblInput.setVisible(visibility);
		if(visibility) {
			statusLblInput.setText(status);
		}
	}
}