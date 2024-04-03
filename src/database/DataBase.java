package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Menu;

public class DataBase {

	private final String URL = "jdbc:mysql://localhost:3306/pudding";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	
	private static DataBase instance;
	private static Connection con;
	
	

	
	private DataBase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DataBase getInstance() {
		if (instance == null) {
			instance = new DataBase();
		}
		return instance; 
	}
	
	public PreparedStatement preparestatement(String query) throws SQLException {
		return con.prepareStatement(query);
	}
	
	public static ObservableList<Menu> getall(){
		String query = "SELECT * FROM ptpudding";
		ObservableList<Menu> menulist = FXCollections.observableArrayList();
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				menulist.add(new Menu(rs.getString("code"), rs.getString("nama"), rs.getInt("harga"), rs.getInt("stok")));
 			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menulist;
	}
	
	public static void add(Menu menu) {
		String query = "INSERT INTO ptpudding VALUES (?, ? , ? ,?)";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, menu.getCode());
			ps.setString(2, menu.getNama());
			ps.setInt(3, menu.getHarga());
			ps.setInt(4, menu.getStok());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void update(Menu menu) {
		String query = "UPDATE ptpudding SET nama = ?, harga = ?, stok = ? WHERE code = ?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, menu.getNama());
			ps.setInt(2, menu.getHarga());
			ps.setInt(3, menu.getStok());
			ps.setString(4, menu.getCode());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void delete(Menu menu) {
		String query = "DELETE FROM ptpudding WHERE code = ?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, menu.getCode());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
