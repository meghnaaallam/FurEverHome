package Controllers;


import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.ActivityData;
import Model.Buyer;
import Model.InterestedBuyerInfo;
import Model.PetData;
import Model.Seller;
import Services.PetService;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Services.BuyerService;
import utilities.Constants;
import utilities.DbConnection;

public class FormController implements Initializable {
	FileChooser fileChooser = new FileChooser();
	
	 List<File> selectedFile = new ArrayList<>();
	

    @FXML
    private RadioButton birds;

    @FXML
    private RadioButton cats;
    
    @FXML
    private ImageView imageView;

    @FXML
    private RadioButton dogs;

    @FXML
    private RadioButton female;
    
    @FXML
    private RadioButton male;

    @FXML
    private ToggleGroup PetCategory;
    
    
    @FXML
    private TableColumn<PetData, String> petAgetb;

    @FXML
    private TextField petAgeInput;

    @FXML
    private Label petAgeLbl;

    @FXML
    private TableColumn<PetData, String> petBreedtb;
    
    
    @FXML
    private TableColumn<PetData, String> petCategorytb;

    @FXML
    private Label petCategoryLabel;


    @FXML
    private TableColumn<PetData, String> petNametb;

    @FXML
    private TableColumn<PetData, String> petSextb;
    
    @FXML
    private TextField petNameInput;

    @FXML
    private Label petNameLabel;

    @FXML
    private TextField priceInput;

    @FXML
    private Label priceLbl;

    @FXML
    private ToggleGroup sex;

    @FXML
    private Label sexLbl;

    @FXML
    private AnchorPane showActivity;
    

    @FXML
    private TableView<PetData> showHome;

    @FXML
    private Button submitBtn;

    @FXML
    private Button uploadPicturesBtn;

    @FXML
    private Label uploadPicturesLbl;
    
    @FXML
    private TableView<PetData> activityTable;
 
    @FXML
    private TableColumn<ActivityData, String> buyerName;
    
    @FXML
    private TableColumn<ActivityData, String> message;
    

    @FXML
    private TableColumn<ActivityData, String> status;

    @FXML
    private AnchorPane addPetsPage;
    


    @FXML
    private AnchorPane homePage;

    @FXML
    private Button activityBtn;
    
    @FXML
    private Button pDEditBtn, pDDeleteBtn;
    

    @FXML
    private TextField petBreedInput;

    @FXML
    private Button addPetsBtn;
    @FXML
    private Button homeBtn;
    

    @FXML
	public Button logoutBtn;
    
    @FXML
    private TextField pDPetCategory, pDPetName, pDPetBreed;
    
    @FXML
    private TextField pDPetAge,pDPetSellerChoice, pDPetSellPriceField;
    
    @FXML
    private Label pDPetSellPriceLbl;
    
    @FXML
    private RadioButton PDPetFemale, pDPetMale;
    
    @FXML
    private AnchorPane petDetailsPane;
  

    @FXML
    private ListView<String> listview;   

	@FXML
	private ChoiceBox<String> myChoiceBox;
	
	@FXML
	private TableColumn activityPetSexColumn;
	
	@FXML
	private TableColumn activityPetCategoryColumn;
	@FXML
	private TableColumn activityPetNameColumn;
	@FXML
	private TableColumn activityPetBreedColumn;
	@FXML
	private TableColumn activityPetAgeColumn;
	
	@FXML
	private Label adPetCategoryLbl;
	
	@FXML
	private Label adPetCategoryInput;
	@FXML
	private Label adPetNameLbl;
	@FXML
	private Label adPetNameInput;
	@FXML
	private Label adAgeLbl;
	@FXML
	private Label adAgeInput;
	@FXML
	private Label adSexLbl;
	@FXML
	private Label adSexInput;
	@FXML
	private Label adBreedLbl;
	@FXML
	private Label adBreedInput;
	
	@FXML
	private AnchorPane sellerAdPane;
	
	@FXML
	private TableView<InterestedBuyerInfo> adTable;
	@FXML
	private TableColumn<InterestedBuyerInfo, String> adBuyerFNameColumn;
	@FXML
	private TableColumn<InterestedBuyerInfo, String> adBuyerLNameColumn;
	@FXML
	private TableColumn<InterestedBuyerInfo, String> adMessageColumn;
	@FXML
	private TableColumn<InterestedBuyerInfo, String> adStatusColumn;
	
	
	@FXML
	private Button adApproveBtn;
	
	
	private ObservableList<PetData> sellerPetActivityList;
	
	private HashMap<PetData, ArrayList<InterestedBuyerInfo>> interestedBuyersMap;
	
	private String[] userOptions = {"Donate", "Sell"};
	
	private Seller seller;
		
	public void initData(Object obj) {
		showHome.setVisible(false);
		sellerAdPane.setVisible(false);
	//	petDetailsPane.setVisible(false);
		this.seller = (Seller) obj;
		try {
			
			addPetsList = fetchPetDataList();
			addPetsShowListTable();
			showHome.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(seller.getSellerId());
	}
	
	public void onSubmitbtn(ActionEvent event) {
		
		try {
		
			
			if(petNameInput.getText().isEmpty()
					||petAgeInput.getText().isEmpty()
					||petBreedInput.getText().isEmpty()
		|| myChoiceBox.getSelectionModel().getSelectedItem() == null
		|| (RadioButton)sex.getSelectedToggle() == null
		|| (RadioButton)PetCategory.getSelectedToggle() == null) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all the fields in the form to proceed");
				alert.showAndWait();
				
				//clears the form
				addPetsClear();
			}
			
			else {

				 
						 String petName = petNameInput.getText();
				int petAge = Integer.parseInt(petAgeInput.getText());
				String breed = petBreedInput.getText();
				int price=0;
				if(!priceInput.getText().isEmpty()) {
					price = Integer.parseInt(priceInput.getText());
				}

				List<File> allPhotoItems = selectedFile;
	            System.out.println("All Items:");
	            for (File item : allPhotoItems) {
	            	 byte[] fileData = readFileAsBytes(item);
	            }
	            
	           
				String choiceOfSelection = (String)myChoiceBox.getSelectionModel().getSelectedItem();
				//String photoTextBox = listview.getSelectionModel().getSelectedItem();;
				
				//System.out.println(photoTextBox);
				String selectedpetSex=null;
				RadioButton selectedRadioButton = (RadioButton)sex.getSelectedToggle(); 
				if (selectedRadioButton != null) {
					selectedpetSex = selectedRadioButton.getText();
				}
				String selectedPetCategory=null;
				RadioButton rb = (RadioButton)PetCategory.getSelectedToggle(); 
				if (rb != null) {
					selectedPetCategory = rb.getText();
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Message");
				alert.setHeaderText(null);
				alert.setContentText("Successfully Added");
				alert.showAndWait();
				
				//clears the form
				addPetsClear();
				
				
			

			PetData pd = new PetData(petName,petAge,breed,price, choiceOfSelection,selectedPetCategory,selectedpetSex,allPhotoItems);
			
String sql = "INSERT INTO `petinfo`(`petCategory`,`petName`,`age`,`breed`,`sellerChoice`,`sellerID`,`sex`,`price`, `image`) "

			        + "VALUES ('" + pd.getPetCategory()+ "','" + pd.getPetName()+ "','" + pd.getAge()+ "','" + pd.getBreed()+ "','" 
			+ pd.getChoiceOfSelection()+ "', "+seller.getSellerId()+" ,'" + pd.getSex()+"', '" + pd.getPrice()+"','" + pd.getImage()+"')";
System.out.println(sql);
        DbConnection.query(sql);   
        
      //show in the table
		addPetsShowListTable();
		}	
		}

	    
		catch(NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all the information correctly");
			alert.showAndWait();
			addPetsClear();
			}
		
		catch(Exception e) {
			e.printStackTrace();
			addPetsClear();
		}
	
}
    private byte[] readFileAsBytes(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputStream.read(buffer);
            inputStream.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        
        
	public void addPetsClear() {
        ObservableList<String> items = FXCollections.observableArrayList();
      //  listview.setItems(items);
		petNameInput.setText("");
		petAgeInput.setText("");
		petBreedInput.setText("");
		priceInput.setText("");
		items.clear();
		myChoiceBox.getSelectionModel().clearSelection();
		sex.selectToggle(null);
		PetCategory.selectToggle(null);
	}
	
	public void switchForm(ActionEvent event) throws SQLException {
		if(event.getSource() == homeBtn ) {
			addPetsList = fetchPetDataList();
			addPetsShowListTable();
			showHome.setVisible(true);
			addPetsPage.setVisible(false);
			showActivity.setVisible(false);
			sellerAdPane.setVisible(false);
			petDetailsPane.setVisible(false);
		}
		else if(event.getSource() == addPetsBtn) {
			showHome.setVisible(false);
			addPetsPage.setVisible(true);
			showActivity.setVisible(false);
			sellerAdPane.setVisible(false);
			petDetailsPane.setVisible(false);
		}
		
		else if(event.getSource() == activityBtn) {
			fetchPetAndBuyerInfo();
			fillSellerActivityTable();
			showHome.setVisible(false);
			addPetsPage.setVisible(false);
			showActivity.setVisible(true);
			sellerAdPane.setVisible(false);
			petDetailsPane.setVisible(false);
		}
	}
	
	private void fillSellerActivityTable() {
		// TODO Auto-generated method stub
		if(sellerPetActivityList != null && sellerPetActivityList.size() > 0) {
			activityPetCategoryColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("petCategory"));
			activityPetNameColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("petName"));
			activityPetBreedColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("breed"));
			activityPetAgeColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("age"));
			activityPetSexColumn.setCellValueFactory(new PropertyValueFactory<PetData, String>("sex"));
			
			
				activityTable.setItems(sellerPetActivityList);
				
				activityTable.setRowFactory(tv -> {
				    TableRow<PetData> row = new TableRow<PetData>();
				    row.setOnMouseClicked(event -> {
				        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY) {
				            PetData selectedPet = row.getItem();
				            try {
//					        	loadPetDetailsPage(selectedPet);
				            	showHome.setVisible(false);
				    			addPetsPage.setVisible(false);
				    			showActivity.setVisible(false);
				    			sellerAdPane.setVisible(true);
				    			petDetailsPane.setVisible(false);
				    			
				    			if(interestedBuyersMap.containsKey(selectedPet)) {
				    				setActivityPetDetail(selectedPet);
				    				ArrayList<InterestedBuyerInfo> buyer = interestedBuyersMap.get(selectedPet);
				    				if(buyer != null && buyer.size() > 0) {
				    					ObservableList<InterestedBuyerInfo> ibiObservableList = FXCollections.observableArrayList(buyer);
				    					filladTable(ibiObservableList, selectedPet.getPetId());
				    				}
				    			}
					        }
					        catch(Exception e) {
					       
					        } 
				        }
				    });
				    return row ;
				});
			
		}
	}

	private void filladTable(ObservableList<InterestedBuyerInfo> ibiObservableList, int petId) {
		// TODO Auto-generated method stub
		adTable.refresh();
		adBuyerFNameColumn.setCellValueFactory(new PropertyValueFactory<InterestedBuyerInfo, String>("buyerFName"));
		adBuyerLNameColumn.setCellValueFactory(new PropertyValueFactory<InterestedBuyerInfo, String>("buyerLName"));
		adMessageColumn.setCellValueFactory(new PropertyValueFactory<InterestedBuyerInfo, String>("msg"));
		adStatusColumn.setCellValueFactory(new PropertyValueFactory<InterestedBuyerInfo, String>("status"));
		adTable.setItems(ibiObservableList);
		
		adTable.setRowFactory(tv -> {
		    TableRow<InterestedBuyerInfo> row = new TableRow<InterestedBuyerInfo>();
		    row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY) {
		        	InterestedBuyerInfo selectedBuyerRow = row.getItem();
		          	adApproveBtn.setOnAction(btnEvent -> {
		        		ArrayList<Integer> ibiRejectArr = new ArrayList<Integer>();
		        		  for(InterestedBuyerInfo ibi: ibiObservableList) {
		        			  if (ibi.getBuyerId() == selectedBuyerRow.getBuyerId()) {
		        				  continue;
		        			  } else {
		        				  ibiRejectArr.add(ibi.getBuyerId());
		        			  }
		        		  }
		        		  System.out.println("petId inside button click: " + petId);
		        		  ArrayList<Integer> approveArr = new ArrayList<Integer>();
		        		  approveArr.add(selectedBuyerRow.getBuyerId());
				        	setApproveStatus(approveArr, petId);
				        	setRejectStatus(ibiRejectArr, petId);
				        	
				        	Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Message");
							alert.setHeaderText(null);
							alert.setContentText("Approved a buyer");
							alert.showAndWait();
							
							  for(InterestedBuyerInfo ibi: ibiObservableList) {
			        			  if (ibi.getBuyerId() == selectedBuyerRow.getBuyerId()) {
			        				  ibi.setStatus(Constants.APPROVED);
			        			  } else {
			        				  ibi.setStatus(Constants.REJECTED);
			        			  }
			        		  }
							  adTable.setItems(ibiObservableList);
							  adApproveBtn.setVisible(false);
		        		});

		        	}
		        });
		    return row;
		});
		
		boolean allPending = true;
		for(InterestedBuyerInfo b : ibiObservableList){
			if(b.getStatus().equals(Constants.APPROVED)) {
				allPending = false;
				break;
			}
		}
		if(allPending) {
			adApproveBtn.setVisible(true);
		}else {
			adApproveBtn.setVisible(false);
		}
		adTable.refresh();
	}
	
	private void setApproveStatus(ArrayList<Integer> asList, int petId) {
		// TODO Auto-generated method stub
		BuyerService bs = new BuyerService();
		bs.setStatus(asList, Constants.APPROVED, petId);
		
		
	}

	private void setRejectStatus(ArrayList<Integer> ibiRejectArr, int petId) {
		// TODO Auto-generated method stub 
		BuyerService bs = new BuyerService();
		bs.setStatus(ibiRejectArr, Constants.REJECTED, petId);
		
	}

 

	private void setActivityPetDetail(PetData selectedPet) {
		// TODO Auto-generated method stub
		
		adPetCategoryInput.setText(selectedPet.getPetCategory());
		adPetNameInput.setText(selectedPet.getPetName());
		adAgeInput.setText(selectedPet.getAge()+"");
		adSexInput.setText(selectedPet.getSex());
		adBreedInput.setText(selectedPet.getBreed());
	}

	private void fetchPetAndBuyerInfo() throws SQLException {
		PetService ps = new PetService();
		ResultSet resultSet = ps.getPetAndBuyerInfo(seller.getSellerId());
		if (resultSet != null) {
			sellerPetActivityList = FXCollections.observableArrayList(); 
			interestedBuyersMap = new HashMap<PetData, ArrayList<InterestedBuyerInfo>>();
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
				PetData alreadyAddedPetData;
				int buyerId = Integer.parseInt(resultSet.getString("buyerId"));
				String buyerFName = resultSet.getString("firstName");
				String buyerLastName = resultSet.getString("lastName");
				String msg = resultSet.getString("buyerMessage");
				String status = resultSet.getString("status");
				InterestedBuyerInfo ibi = new InterestedBuyerInfo(buyerId, buyerFName, buyerLastName, status, msg);
				ArrayList<InterestedBuyerInfo> buyer;
				if(searchPetData(pd) != null) {
					alreadyAddedPetData = searchPetData(pd);
					buyer = interestedBuyersMap.get(alreadyAddedPetData);
					buyer.add(ibi);
				} else {
					sellerPetActivityList.add(pd);
					buyer = new ArrayList<InterestedBuyerInfo>();
					buyer.add(ibi);
					interestedBuyersMap.put(pd, buyer);
				}
			}
		}
	}
	
	private PetData searchPetData(PetData petData ) {
		if(sellerPetActivityList != null && sellerPetActivityList.size()>0) {
			for(PetData pd : sellerPetActivityList) {
		        if(pd.getPetId()== petData.getPetId()) {
		            return pd;
		        }
		    }
		}
		return null;
	}
	
	private ObservableList<PetData> addPetsList;
    public void addPetsShowListTable() throws SQLException {
    	addPetsList = fetchPetDataList();
	petCategorytb.setCellValueFactory(new PropertyValueFactory<>("petCategory"));
	petNametb.setCellValueFactory(new PropertyValueFactory<>("petName"));
	petBreedtb.setCellValueFactory(new PropertyValueFactory<>("breed"));
	petAgetb.setCellValueFactory(new PropertyValueFactory<>("age"));
	petSextb.setCellValueFactory(new PropertyValueFactory<>("sex"));
	showHome.setItems(addPetsList);
	
	showHome.setRowFactory(tv -> {
	    TableRow<PetData> row = new TableRow<PetData>();
	    row.setOnMouseClicked(event -> {
	        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY) {
	            PetData selectedPet = row.getItem();
	            try {
	            	showHome.setVisible(false);
	    			addPetsPage.setVisible(false);
	    			showActivity.setVisible(false);
	    			sellerAdPane.setVisible(false);
	    			petDetailsPane.setVisible(true);
	    			
	    			setSelectedPetDetails(selectedPet);
	    			
	    			pDEditBtn.setOnAction(btnEvent -> {
	    					String breed = pDPetBreed.getText();
	    					String petName = pDPetName.getText();
	    					int age = Integer.parseInt(pDPetAge.getText());
	    					int price = (pDPetSellPriceField.getText() != null && !pDPetSellPriceField.getText().equals(""))?Integer.parseInt(pDPetSellPriceField.getText()): 0;
	    					selectedPet.setAge(age);
	    					selectedPet.setBreed(breed);
	    					selectedPet.setPetName(petName);
	    					if (price > 0) {
	    						selectedPet.setPrice(price);
	    					}
	    					if(pDPetMale.isSelected()) {
	    						selectedPet.setSex("Male");
	    					} else if (PDPetFemale.isSelected()) {
	    						selectedPet.setSex("Female");
	    					}
		        			updatePetDetails(selectedPet);
		        		});
	    			
	    			pDDeleteBtn.setOnAction(btnEvent -> {
	    				PetService ps = new PetService();
	    				try {
							ps.deletePetDetails(selectedPet);
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Success Message");
							alert.setHeaderText(null);
							alert.setContentText("Pet Info Deleted Successfully");
							
							Optional<ButtonType> option = alert.showAndWait();
							
							
							addPetsList = fetchPetDataList();
			    			addPetsShowListTable();
			    			
							showHome.setVisible(true);
			    			addPetsPage.setVisible(false);
			    			showActivity.setVisible(false);
			    			sellerAdPane.setVisible(false);
			    			petDetailsPane.setVisible(false);
			    			
			    			
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    			});
		        }
		        catch(Exception e) {
		       
		        } 
	        }
	    });
	    return row ;
	});
	
}
	
	private void updatePetDetails(PetData selectedPet) {
		// TODO Auto-generated method stub
		PetService ps = new PetService();
		try {
			ps.updatePetDetails(selectedPet);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success Message");
		alert.setHeaderText(null);
		alert.setContentText("Pet Info Updated Successfully");
		
		Optional<ButtonType> option = alert.showAndWait();
		
		setSelectedPetDetails(selectedPet);
	}

	private void setSelectedPetDetails(PetData selectedPet) {
		// TODO Auto-generated method stub
		ToggleGroup toggleGroup = new ToggleGroup();
		PDPetFemale.setToggleGroup(toggleGroup);
		pDPetMale.setToggleGroup(toggleGroup);
		
		if(selectedPet.getSex().equals("Male")) {
			pDPetMale.setSelected(true);
		} else if (selectedPet.getSex().equals("Female")) {
			PDPetFemale.setSelected(true);
		} else {
			
		}
		
		pDPetCategory.setText(selectedPet.getPetCategory());
		pDPetCategory.setDisable(true);
		pDPetName.setText(selectedPet.getPetName());
		pDPetBreed.setText(selectedPet.getBreed());
		pDPetAge.setText(selectedPet.getAge()+"");
		pDPetSellerChoice.setText(selectedPet.getChoiceOfSelection());
		pDPetSellerChoice.setDisable(true);
		
		if(selectedPet.getChoiceOfSelection().equals("Sell")) {
			pDPetSellPriceLbl.setVisible(true);
			pDPetSellPriceField.setVisible(true);
			pDPetSellPriceField.setText(selectedPet.getPrice()+ "");
		} else {
			pDPetSellPriceLbl.setVisible(false);
			pDPetSellPriceField.setVisible(false);
		}
		
	}

	private double x= 0;
	private double y=0;
	
	public void logout() {
		
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
	}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		 File wordImage = new File("src/images/biggest.png");
		 Image imageRight = new Image(wordImage.toURI().toString());
		 imageView.setImage(imageRight);
		
		myChoiceBox.getItems().addAll(userOptions);
		sellerAdPane.setVisible(false);
		myChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if((String)myChoiceBox.getSelectionModel().getSelectedItem()== "Sell") {
				priceLbl.setVisible(true);
				priceInput.setVisible(true);
			}
			else {
				priceLbl.setVisible(false);
				priceInput.setVisible(false);
			}
			});
	}
    
	
	@FXML
    void uploadImages(MouseEvent event) {
		 fileChooser.setInitialDirectory(new File("C:\\Program Files"));
		 fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PNG Files", "*.png"));
  selectedFile = fileChooser.showOpenMultipleDialog(null);		
		
		if(selectedFile!=null) {
			for(int i=0;i<selectedFile.size();i++) {
			listview.getItems().add(selectedFile.get(i).getAbsolutePath());
		}
		}
		else {
			System.out.println("File is not valid");
		}

}
	
	
	  public ObservableList<PetData> fetchPetDataList() throws SQLException {

	        ObservableList<PetData> petdataList = FXCollections.observableArrayList();
	       if(seller != null) {
	    	   
	       
	        String query = "SELECT *\r\n"
	        		+ "FROM petinfo pi\r\n"
	        		+ "WHERE pi.id NOT IN (\r\n"
	        		+ "    SELECT id\r\n"
	        		+ "    FROM petBuyer\r\n"
	        		+ "    WHERE status IN ('Approved', 'Rejected') \r\n"
	        		+ ") and pi.sellerID = " + seller.getSellerId() + ";";


	        //System.out.println("query " + query);

	        ResultSet resultSet = DbConnection.selectQuery(query);

	        if (resultSet != null) {

	            while (resultSet.next()) {
	            	

	            String petName = resultSet.getString("petName");
	            int price = (resultSet.getString("price") != null)?Integer.parseInt(resultSet.getString("price")): 0;

	            int age = Integer.parseInt(resultSet.getString("age"));
	            String petCategory = resultSet.getString("petCategory");

	            String sellerChoice = resultSet.getString("sellerChoice");

	            String sex = resultSet.getString("sex");
	            String breed = resultSet.getString("breed");

	            int petId = Integer.parseInt(resultSet.getString("id"));
	            int sellerId = Integer.parseInt(resultSet.getString("sellerID"));


//	            List<File> allPhotoItems = new ArrayList<>();
//	            while (resultSet.next()) {
//	                String filePath = resultSet.getString("image");
//	                if(filePath != null) {
//	                	File file = new File(filePath);
//		                allPhotoItems.add(file);
//	                }
//	                


	    //        List<File> allPhotoItems = new ArrayList<>();
//	            while (resultSet.next()) {
//	                String filePath = resultSet.getString("image");
//	                File file = new File(filePath);
//	                allPhotoItems.add(file);

//	            }
//	        	
//	            for (File file : allPhotoItems) {
//	                System.out.println(file.getAbsolutePath());
//	            }
	           PetData petDataInfo = new PetData(petName,age,breed,price,sellerChoice,petCategory,sex,null); 
	           petDataInfo.setPetId(petId);
	           petDataInfo.setSellerId(sellerId);

	          //  boolean empStatus = dbStatus.equals("0") ? false : true;

	           // justiceDepartmentEmployee.setStatus(empStatus);

	          //  justiceDepartmentEmployee.setId(id);

	       petdataList.add(petDataInfo);
	      // System.out.println(sellerChoice);

	           

	        }  

	        }
	  }

	        return petdataList;

	    }
}
