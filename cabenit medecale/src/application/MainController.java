package application;

import java.net.URL;
import java.util.ResourceBundle;


import DAO.ClientDAOimpl;
import DAO.CreneauxDAOimpl;
import DAO.ICreneauxDao;
import DAO.IPersoneDao;
import DAO.MedecinDAOimpl;
import Models.Client;
import Models.Creneaux;
import Models.Medcin;
import Models.Persone;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

public class MainController implements Initializable {
	//Client
	@FXML TextField txtVersion;
	@FXML TextField txtNom;
	@FXML TextField txtPrenom;
	@FXML TextField txtTitre;
	@FXML TextField txtID;
	@FXML TableView<Persone> tvClient;
	//Medecin
	@FXML TextField txtVersionM;
	@FXML TextField txtNomM;
	@FXML TextField txtPrenomM;
	@FXML TextField txtTitreM;
	@FXML TextField txtIDM;
	@FXML TableView<Persone> tvMedecin;
	//Creneaux
	@FXML TextField txtVersionCR;
	@FXML TextField txtHDebut;
	@FXML TextField txtHFin;
	@FXML TextField txtMDebut;
	@FXML TextField txtMFin;
	@FXML ComboBox<Persone> ComboMedecin;
	@FXML TableView<Creneaux> tvCR;
	private Persone selectedMedcine;
	@FXML TabPane tabPane;

	
	
	private IPersoneDao PersoneDAO;
	private ICreneauxDao CreneauxDao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//First Tab In tabPane so i should Show 
		PersoneDAO = new ClientDAOimpl();
		initTableView(tvClient);
		
		tvClient.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tvMedecin.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tvClient.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Persone>() {
			@Override
			public void onChanged(Change<? extends Persone> change) {
		       // System.out.println("Selection changed: " + change.getList().get(0).getNom());
				Persone p= change.getList().get(0);
				
		        txtNom.setText(p.getNom());
		        txtPrenom.setText(p.getPrenom());
		        txtTitre.setText(p.getTitre());
		        txtVersion.setText(String.valueOf(p.getVersion()));
		        txtID.setText(String.valueOf(p.getID()));
				
			}
		});
		
		tvMedecin.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Persone>() {
			@Override
			public void onChanged(Change<? extends Persone> change) {
		       // System.out.println("Selection changed: " + change.getList().get(0).getNom());
				Persone p= change.getList().get(0);
				
		        txtNomM.setText(p.getNom());
		        txtPrenomM.setText(p.getPrenom());
		        txtTitreM.setText(p.getTitre());
		        txtVersionM.setText(String.valueOf(p.getVersion()));
		        txtIDM.setText(String.valueOf(p.getID()));
				
			}
		});
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				int index = (int)newValue;
				switch (index) {
				case 0:
					PersoneDAO = new ClientDAOimpl();
					initTableView(tvClient);
					break;
				case 1:
					PersoneDAO = new MedecinDAOimpl();
					initTableView(tvMedecin);
					break;
				case 2:
					CreneauxDao = new CreneauxDAOimpl();
					//to Fill Medecine ComboBox
					PersoneDAO = new MedecinDAOimpl();
					initCreneauxTab();
					break;
				case 3:
					PersoneDAO = new MedecinDAOimpl();
					initTableView(tvMedecin);
					break;
				}
			}
		});
	
	}
	
	/*Client CRUD*/
	
	@FXML
	public void addC (Event e) {
		System.out.println(e.getTarget().toString());
		try {
			int version = Integer.parseInt(txtVersion.getText()) ;
			PersoneDAO.Add(new Client(-1,version,txtTitre.getText() ,txtPrenom.getText(), txtNom.getText()));
			initTableView(tvClient);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
	@FXML
	public void UpdateC() {
		try {
			int version = Integer.parseInt(txtVersion.getText()) ;
			int id = Integer.parseInt(txtID.getText());
			PersoneDAO.Update(new Client(id,version,txtTitre.getText() ,txtPrenom.getText(), txtNom.getText()));
			initTableView(tvClient);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void DeleteC() {
		try {
			int id = Integer.parseInt(txtID.getText());
			PersoneDAO.Delete(id);
			initTableView(tvClient);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	/*Medicine CRUD*/
	
	@FXML
	public void addM (Event e) {
		System.out.println(e.getTarget().toString());
		try {
			int version = Integer.parseInt(txtVersionM.getText()) ;
			PersoneDAO.Add(new Medcin(-1,version,txtTitreM.getText() ,txtPrenomM.getText(), txtNomM.getText()));
			initTableView(tvMedecin);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
	@FXML
	public void UpdateM() {
		try {
			int version = Integer.parseInt(txtVersionM.getText()) ;
			int id = Integer.parseInt(txtIDM.getText());
			PersoneDAO.Update(new Medcin(id,version,txtTitreM.getText() ,txtPrenomM.getText(), txtNomM.getText()));
			initTableView(tvMedecin);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void DeleteM() {
		try {
			int id = Integer.parseInt(txtIDM.getText());
			PersoneDAO.Delete(id);
			initTableView(tvMedecin);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	/*Creneaux CRUD*/
	@FXML
	private void MedcineSelectedChanged(){
		System.out.println(ComboMedecin.getSelectionModel().getSelectedItem());
		selectedMedcine = ComboMedecin.getSelectionModel().getSelectedItem();

	}
	@FXML
	public void addCR () {
		if(selectedMedcine==null) return;
		try {
			int version = Integer.parseInt(txtVersionCR.getText()) ;
			int hdebut = Integer.parseInt(txtHDebut.getText()) ;
			int mdebut = Integer.parseInt(txtMDebut.getText()) ;
			int hfin = Integer.parseInt(txtHFin.getText()) ;
			int mfin = Integer.parseInt(txtMFin.getText()) ;
			//(int iD, int version, int hDebut,int mDebut, int hFin, int mFin,Persone medecin
			CreneauxDao.Add(new Creneaux(-1,version,hdebut,mdebut,hfin,mfin,selectedMedcine));
			initCreneauxTab();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
	@FXML
	public void UpdateCR() {
		try {
			int version = Integer.parseInt(txtVersionM.getText()) ;
			int id = Integer.parseInt(txtIDM.getText());
			PersoneDAO.Update(new Medcin(id,version,txtTitreM.getText() ,txtPrenomM.getText(), txtNomM.getText()));
			initTableView(tvMedecin);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void DeleteCR() {
		try {
			int id = Integer.parseInt(txtIDM.getText());
			PersoneDAO.Delete(id);
			initTableView(tvMedecin);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	//Helper Functions
	private void initCreneauxTab() {
		//this initialize Client and Medecine TableView with data 
		ComboMedecin.getItems().clear();
		ComboMedecin.setItems(FXCollections.observableArrayList(PersoneDAO.getAll()));
		tvCR.getColumns().clear();
		TableColumn<Creneaux, Integer> id = new TableColumn<>("id");
		id.setCellValueFactory(new PropertyValueFactory<>("ID"));
		
		TableColumn<Creneaux, Integer> Version = new TableColumn<>("Version");
		Version.setCellValueFactory(new PropertyValueFactory<>("Version"));
		
		TableColumn<Creneaux, Integer> HDebut = new TableColumn<>("Heur Debut");
		HDebut.setCellValueFactory(new PropertyValueFactory<>("HDebut"));
		
		TableColumn<Creneaux, Integer> MDebut = new TableColumn<>("Mois Debut");
		MDebut.setCellValueFactory(new PropertyValueFactory<>("MDebut"));
		
		TableColumn<Creneaux, Integer> HFin = new TableColumn<>("Heur Fin");
		HFin.setCellValueFactory(new PropertyValueFactory<>("HFin"));
		
		TableColumn<Creneaux, Integer> MFin = new TableColumn<>("Mois Fin");
		MFin.setCellValueFactory(new PropertyValueFactory<>("MFin"));
		
		TableColumn<Creneaux, Integer> Medecin = new TableColumn<>("Medecin");
		Medecin.setCellValueFactory(new PropertyValueFactory<>("Medecin"));
		
		tvCR.getColumns().addAll(id,Medecin,HDebut,HFin,MDebut,MFin,Version);
		ObservableList<Creneaux> Creneaux = FXCollections.observableArrayList(CreneauxDao.getAll());
		
		tvCR.setItems(Creneaux);
	}
	private void initTableView(TableView<Persone> tableview) {
		//this initialize Client and Medecine TableView with data 
		tableview.getColumns().clear();
		TableColumn<Persone, Integer> id = new TableColumn<>("id");
		id.setCellValueFactory(new PropertyValueFactory<>("ID"));
		
		TableColumn<Persone, Integer> Version = new TableColumn<>("Version");
		Version.setCellValueFactory(new PropertyValueFactory<>("Version"));
		
		TableColumn<Persone, String> Prenom = new TableColumn<>("Prenom");
		Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
		
		TableColumn<Persone, String> Nom = new TableColumn<>("Nom");
		Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		
		TableColumn<Persone, String> Titre = new TableColumn<>("Titre");
		Titre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
		
		tableview.getColumns().addAll(id,Prenom,Nom,Titre,Version);
		ObservableList<Persone> Personnes = FXCollections.observableArrayList(PersoneDAO.getAll());
		
		tableview.setItems(Personnes);
		
	}
}
