package Controllers;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.Buyer;
import Model.PetData;
import Services.PetService;
import application.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class BuyerController implements Initializable {
	
	@FXML 
	private Button homeBtn;
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private Button myActivityBtn;
	
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private AnchorPane homePane;
	
	@FXML
	private Label userDetailLabel;
	
	@FXML
	private TableView<PetData> tableView;
	
	@FXML
	private TableColumn<PetData, String> petCategoryColumn;
	
	@FXML
	private TableColumn<PetData, String> petNameColumn;
	
	@FXML
	private TableColumn<PetData, String> breedColumn;
	
	@FXML
	private TableColumn<PetData, String> ageColumn;
	
	@FXML
	private TableColumn<PetData, String> sexColumn;
	
	
	private ObservableList<PetData> petDetailsList;
	
	
	private Buyer buyer;
	
	public void initData(Object obj) {
		this.buyer = (Buyer) obj;
		System.out.println("Buyer details " + buyer.getfName());
		getAllPetDetails();
		fillHomePetTable();
		
	}
	
	public void onMyActivity() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/buyerActivityPane.fxml"));
		Parent root = loader.load();
		BuyerActivityController bac = loader.getController();
		bac.initData(buyer, mainPane);
		mainPane.setCenter(root);
	}
	
	public void onHome() throws IOException {
		mainPane.setCenter(homePane);
		getAllPetDetails();
		fillHomePetTable();
	}
	
	public void onLogOut() throws IOException {
		try {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout Message");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to logout?");
		
		Optional<ButtonType> option = alert.showAndWait();
		
		if(option.get().equals(ButtonType.OK)) {
			Main m = new Main();
			m.changeScene("login.fxml", null);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {		
		 File wordImage = new File("src/images/biggest.png");
		 Image imageRight = new Image(wordImage.toURI().toString());
		 imageView.setImage(imageRight);
	}
	
	public void fillHomePetTable() {
		petCategoryColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("petCategory"));
		petNameColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("petName"));
		breedColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("breed"));
		ageColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("age"));
		sexColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("sex"));
		
		if (petDetailsList != null && petDetailsList.size() > 0) {
			tableView.setItems(petDetailsList);
			
			tableView.setRowFactory(tv -> {
			    TableRow<PetData> row = new TableRow<PetData>();
			    row.setOnMouseClicked(event -> {
			        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY) {
			            PetData selectedPet = row.getItem();
			            try {
				        	loadPetDetailsPage(selectedPet);
				        }
				        catch(Exception e) {
				       
				        } 
			        }
			    });
			    return row ;
			});
		}
	}
	
	
	public void getAllPetDetails() {
		if(buyer != null) {
			try {
				PetService ps = new PetService();
				petDetailsList = ps.fetchBuyerPetDashboardInfo(buyer.getBuyerId());
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void loadPetDetailsPage(PetData petData) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/buyerPetDetailsPane.fxml"));
		Parent root = loader.load();
		BuyerPetDetailController bpc = loader.getController();
		bpc.initData(petData, buyer);
		mainPane.setCenter(root);
	}

}
