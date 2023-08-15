module FurEverHome {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens Controllers to javafx.fxml;
	opens Model to javafx.fxml, javafx.base;
}
