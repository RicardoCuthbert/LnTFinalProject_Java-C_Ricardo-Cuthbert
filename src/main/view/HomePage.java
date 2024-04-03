package main.view;

import java.util.ArrayList;
import java.util.List;

import database.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;
import main.model.Menu;

public class HomePage {
	private Stage stage;
	private BorderPane root = new BorderPane();
	private GridPane gp = new GridPane();
	private Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
	private Label headerlb1 = new Label("Add New Menu");
	private Label idlbl = new Label("Menu Code");
	private Label namelb1 = new Label("Menu Name");
	private Label pricelb1 = new Label("Menu Price");
	private Label stoklb1 = new Label("Menu Stock");
	private TextField idtf = new TextField();
	private TextField nametf = new TextField();
	private TextField pricetf = new TextField();
	private TextField stoktf = new TextField();
	private TableView<Menu> table = new TableView<Menu>();
	private TableColumn<Menu, String> idcol = new TableColumn<Menu, String>("Code");
	private TableColumn<Menu, String> namecol = new TableColumn<Menu, String>("Name");
	private TableColumn<Menu, Integer> pricecol = new TableColumn<Menu, Integer>("Price");
	private TableColumn<Menu, Integer> stokcol = new TableColumn<Menu, Integer>("Stock");
	private Button addBtn = new Button("Add");
	private Button updateBtn = new Button("Update");
	private Button deleteBtn = new Button("Delete");
	private HBox buttonBox = new HBox(updateBtn, deleteBtn);
	private Menu selected;
	private ObservableList<Menu> menuList = FXCollections.observableArrayList();
	
	public HomePage(Stage stage) {
		this.stage = stage;
		this.setComponent();
		this.setStyle(); 
		this.setTableColumns();
		this.setListener();
		DataBase.getInstance();
		this.populateTable();
		this.handleButton();
	}

	@SuppressWarnings("unchecked")
	private void setComponent() {
		gp.add(headerlb1, 0, 0, 2, 1);
		gp.add(idlbl, 0, 1);
		gp.add(idtf, 1, 1);
		gp.add(namelb1, 0, 2);
		gp.add(nametf, 1, 2);
		gp.add(pricelb1, 0, 3);
		gp.add(pricetf, 1, 3);
		gp.add(stoktf, 1, 4);
		gp.add(stoklb1, 0, 4);
		gp.add(buttonBox, 0, 5, 3 ,1);
		gp.add(addBtn, 0, 6, 3 ,1);
		
		table.getColumns().addAll(idcol, namecol,pricecol, stokcol);
		
		
		root.setTop(table);
		root.setCenter(gp);
		stage.setScene(scene);
	}
	
	@SuppressWarnings("deprecation")
	private void setStyle() {
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(15);
		gp.setVgap(15);
		
		GridPane.setHalignment(headerlb1, HPos.CENTER);
		GridPane.setHalignment(buttonBox, HPos.CENTER);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		addBtn.setMinWidth(230);
		updateBtn.setMinWidth(110);
		deleteBtn.setMinWidth(110);
		buttonBox.setSpacing(10);
		stage.setResizable(false);
	}
	
	private void setTableColumns() {
		idcol.setCellValueFactory(new PropertyValueFactory<Menu, String>("code"));
		namecol.setCellValueFactory(new PropertyValueFactory<Menu, String>("nama"));
		stokcol.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("harga"));
		pricecol.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("stok"));
		
	}
	
	private void setListener() {
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			
			if (newValue != null) {
				this.selected = newValue;
				idtf.setText(newValue.getCode());
				nametf.setText(newValue.getNama());
				pricetf.setText(String.valueOf(newValue.getHarga()));
				stoktf.setText(String.valueOf(newValue.getStok()));
			}
		});
	}
	
	private void populateTable() {
		menuList = DataBase.getall();
		table.setItems(menuList);
		idtf.clear();
		nametf.clear();
		pricetf.clear();
		stoktf.clear();
	}
	
	private void handleButton() {
		addBtn.setOnAction(event -> {
			String code = idtf.getText();
			String name = nametf.getText();
			String price = pricetf.getText();
			String stok = stoktf.getText();
			
			
			for (Menu furniture : menuList) {
				if (furniture.getCode().equals(code)) {
					alert(AlertType.ERROR, "Error", "Validation Error", "Code must be different from existed");
					return;
				}
			}
			
			if(code.isEmpty() || name.isEmpty() || price.isEmpty() || stok.isEmpty()) {
				alert(AlertType.ERROR, "Error", "Validation Error", "All box must be filled");
				return;
			}
			
			if(!code.startsWith("PD-")) {
				alert(AlertType.ERROR, "Error", "Validation Error", "Code must start with PD-");
				return;
			}
			
			try {
				Integer.valueOf(price);
			} catch (Exception e) {
				alert(AlertType.ERROR, "Error", "Validation Error", "Price must be numeric");
				return;
			}
			
			try {
				Integer.valueOf(stok);
			} catch (Exception e) {
				alert(AlertType.ERROR, "Error", "Validation Error", "Stock must be numeric");
				return;
			}
			
			DataBase.add(new Menu(code, name, Integer.valueOf(price), Integer.valueOf(stok)));
			this.populateTable();
			alert(AlertType.INFORMATION, "Cool", "Info", "The menu succesfully added");
		});
		
		updateBtn.setOnAction(event->{
			String code = idtf.getText();
			String name = nametf.getText();
			String price = pricetf.getText();
			String stok = stoktf.getText();
			
			if(code.isEmpty() || name.isEmpty() || price.isEmpty() || stok.isEmpty()) {
				alert(AlertType.ERROR, "Error", "Validation Error", "All box must be filled");
				return;
			}
			
			DataBase.update(new Menu(code, name, Integer.parseInt(price), Integer.parseInt(stok)));
			this.populateTable();
			alert(AlertType.INFORMATION, "Cool", "Info", "The menu succesfully updated");
		});
		
		deleteBtn.setOnAction(event->{
			DataBase.delete(selected);
			this.populateTable();
			alert(AlertType.INFORMATION, "Cool", "Info", "The menu succesfully delete");
		});
	}
	
	private void alert(AlertType alerttype, String title, String header, String content) {
		Alert alert = new Alert(alerttype);
		alert.initOwner(stage);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
