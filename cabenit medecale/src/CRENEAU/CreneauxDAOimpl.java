package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Models.Client;
import Models.Creneaux;
import Models.Persone;
import javafx.scene.control.Alert;

public class CreneauxDAOimpl implements ICreneauxDao {
	Statement stmt;
	ResultSet rs;
	DatabaseConnection dbcnx;
	IPersoneDao MedecinD;
	public CreneauxDAOimpl() {
		dbcnx = DatabaseConnection.getInstance();
		MedecinD= new MedecinDAOimpl();
	}

	@Override
	public List<Creneaux> getAll() {
		 List<Creneaux> pList=new ArrayList<Creneaux>();
		
		try {
			stmt = dbcnx.getConnection().createStatement();
			rs =stmt.executeQuery("select * from Creaneaux");  
			
			while(rs.next()) { 
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)); 
				Creneaux C= new Creneaux(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),MedecinD.getOne(rs.getInt(7)));
				pList.add(C);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return pList;
	}


	public void Add(Creneaux C) {
		try {
		String Query = "insert into Creaneaux values (?,?,?,?,?,?)";
			PreparedStatement pstmt = dbcnx.getConnection().prepareStatement(Query);
			pstmt.setInt(1, C.getVersion()); 	
			pstmt.setInt(2, C.getHDebut());
			pstmt.setInt(3, C.getMDebut());
			pstmt.setInt(4, C.getHFin());
			pstmt.setInt(5, C.getMFin());
			pstmt.setInt(6, C.getMedecinID());
			int rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
			    new Alert(Alert.AlertType.INFORMATION,"A Creaneaux was Inserted successfully!").showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		    new Alert(Alert.AlertType.ERROR,"ERROR HAS ACCURED SEE CONSOL").showAndWait();

		}
		

	}

	
	public void Update(Creneaux C) {
		try {
		String Query = "Update Creaneaux set Version = ? ,Hdebut=?,Mdebut=?,HFin=?,MFin=?,ID_Medecin = ? where ID = ?";
			PreparedStatement pstmt = dbcnx.getConnection().prepareStatement(Query);
			pstmt.setInt(1, C.getVersion()); 	
			pstmt.setInt(2, C.getHDebut());
			pstmt.setInt(3, C.getMDebut());
			pstmt.setInt(4, C.getHFin());
			pstmt.setInt(5, C.getMFin());
			pstmt.setInt(6, C.getMedecinID());
			pstmt.setInt(7, C.getID());
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
			    new Alert(Alert.AlertType.INFORMATION,"A Creaneaux was Updated successfully!").showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		    new Alert(Alert.AlertType.ERROR,"ERROR HAS ACCURED SEE CONSOL").showAndWait();

		}

	}

	@Override
	public void Delete(int id) {
		try {
			String Query = "Delete from Creaneaux where ID = ?";
				PreparedStatement pstmt = dbcnx.getConnection().prepareStatement(Query);
				pstmt.setInt(1, id);
				int rowsDeleted = pstmt.executeUpdate();
				if (rowsDeleted > 0) {
				    new Alert(Alert.AlertType.INFORMATION,"A Creaneaux was Deleted successfully!").showAndWait();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			    new Alert(Alert.AlertType.ERROR,"ERROR HAS ACCURED SEE CONSOL").showAndWait();

			}
	}

	public Creneaux getOne(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
