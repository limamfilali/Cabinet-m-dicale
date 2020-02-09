package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Models.Client;
import Models.Persone;
import javafx.scene.control.Alert;

public class ClientDAOimpl implements IPersoneDao {
	Statement stmt;
	ResultSet rs;
	DatabaseConnection dbcnx;
	public ClientDAOimpl() {
		dbcnx = DatabaseConnection.getInstance();
	}

	@Override
	public List<Persone> getAll() {
		 List<Persone> pList=new ArrayList<Persone>();
		
		try {
			stmt = dbcnx.getConnection().createStatement();
			rs =stmt.executeQuery("select * from Client");  
			
			while(rs.next()) { 
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)); 
				Persone p = new Client(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
				pList.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return pList;
	}

	@Override
	public Persone getOne(int id) {
		
		return null;
	}

	@Override
	public void Add(Persone P) {
		try {
		String Query = "insert into Client values (?,?,?,?)";
			PreparedStatement pstmt = dbcnx.getConnection().prepareStatement(Query);
			pstmt.setInt(1, P.getVersion());
			pstmt.setString(2, P.getTitre());
			pstmt.setString(3, P.getNom());
			pstmt.setString(4, P.getPrenom());
			int rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
			    new Alert(Alert.AlertType.INFORMATION,"A Client was Inserted successfully!").showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		    new Alert(Alert.AlertType.ERROR,"ERROR HAS ACCURED SEE CONSOL").showAndWait();

		}
		

	}

	@Override
	public void Update(Persone P) {
		try {
		String Query = "Update Client set Version = ? ,Titre=?,Nom=?,Prenom=? where ID = ?";
			PreparedStatement pstmt = dbcnx.getConnection().prepareStatement(Query);
			pstmt.setInt(1, P.getVersion());
			pstmt.setString(2, P.getTitre());
			pstmt.setString(3, P.getNom());
			pstmt.setString(4, P.getPrenom());
			pstmt.setInt(5, P.getID());
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
			    new Alert(Alert.AlertType.INFORMATION,"A Client was Updated successfully!").showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		    new Alert(Alert.AlertType.ERROR,"ERROR HAS ACCURED SEE CONSOL").showAndWait();

		}

	}

	@Override
	public void Delete(int id) {
		try {
			String Query = "Delete from Client where ID = ?";
				PreparedStatement pstmt = dbcnx.getConnection().prepareStatement(Query);
				pstmt.setInt(1, id);
				int rowsDeleted = pstmt.executeUpdate();
				if (rowsDeleted > 0) {
				    new Alert(Alert.AlertType.INFORMATION,"A Client was Deleted successfully!").showAndWait();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			    new Alert(Alert.AlertType.ERROR,"ERROR HAS ACCURED SEE CONSOL").showAndWait();

			}
	}

}
