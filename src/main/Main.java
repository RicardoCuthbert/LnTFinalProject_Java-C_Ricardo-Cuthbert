package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.view.HomePage;

public class Main extends Application{

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	
	@Override
	public void start(Stage stage) throws Exception {
		new HomePage(stage);
		stage.setTitle("PT Pudding");
		
		stage.show();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
}
