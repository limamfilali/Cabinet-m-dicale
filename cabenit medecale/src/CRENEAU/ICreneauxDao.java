package DAO;

import java.util.List;

import Models.Creneaux;

public interface ICreneauxDao {
	public List<Creneaux> getAll();
	public Creneaux getOne(int id);
	public void Add(Creneaux C);
	public void Update(Creneaux C);
	public void Delete(int id);
}
