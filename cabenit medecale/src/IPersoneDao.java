package DAO;

import java.util.List;

import Models.Persone;

public interface IPersoneDao {
	public List<Persone> getAll();
	public Persone getOne(int id);
	public void Add(Persone P);
	public void Update(Persone P);
	public void Delete(int id);
}
