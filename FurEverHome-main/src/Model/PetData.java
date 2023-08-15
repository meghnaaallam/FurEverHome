package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class PetData {

	private Integer age;
	private String petCategory;
	private String petName;
	private String sex;
	private Integer price;
	private List<File> image;
	private String choiceOfSelection;
	private String breed;

	private int petId;
	private int sellerId;
		//this.listingId = listingId;
	
	public PetData(String petName,Integer age, String breed, Integer price, String choiceOfSelection, String petCategory, String sex, List<File> allPhotoItems) {
		this.age = age;
		this.choiceOfSelection = choiceOfSelection;
		this.price = price;
		this.petCategory = petCategory;
		this.petName = petName;
		this.sex = sex;
		this.breed = breed;
		this.image = allPhotoItems;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public List<File> getImage() {
		return image;
	}


	public void setImage(List<File> image) {
		this.image = image;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getChoiceOfSelection() {
		return choiceOfSelection;
	}

	public void setChoiceOfSelection(String choiceOfSelection) {
		this.choiceOfSelection = choiceOfSelection;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPetCategory() {
		return petCategory;
	}

	public void setPetCategory(String petCategory) {
		this.petCategory = petCategory;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}
}
