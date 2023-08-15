package Controllers;

import java.io.IOException;
import java.sql.SQLException;

import Model.Buyer;
import Model.PetData;
import Services.PetService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;

public class BuyerActivityController {
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
	
	@FXML
	private BorderPane mainPane;
	
	private Buyer buyer;
	
	private ObservableList<PetData> activityList;
	
	public void initData(Buyer buyer, BorderPane mainPane) {
		this.buyer = buyer;
		this.mainPane = mainPane;
		fetchActivityList();
		System.out.println(activityList.size());
		fillActivityPetTable();
	}
	
	public void fetchActivityList() {
		PetService ps = new PetService();
		try {
			activityList = ps.fetchBuyerActivityData(buyer.getBuyerId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fillActivityPetTable() {
		petCategoryColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("petCategory"));
		petNameColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("petName"));
		breedColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("breed"));
		ageColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("age"));
		sexColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("sex"));
		
		if (activityList != null && activityList.size() > 0) {
			tableView.setItems(activityList);
			
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
	
	public void loadPetDetailsPage(PetData petData) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/buyerPetDetailsPane.fxml"));
		Parent root = loader.load();
		BuyerPetDetailController bpc = loader.getController();
		bpc.initData(petData, buyer);
		mainPane.setCenter(root);
	}
}
