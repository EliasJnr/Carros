package br.com.eliasjr.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarroService {
	
	private CarroDAO db = new CarroDAO();
	
	
	public Carro getCarro(Integer id) {
		try {
			return db.getCarroById(id);
		}catch(Exception e) {
			return null;
		}
	}
	
	//lista todos os carros do banco
	public List<Carro> getCarros(){
		try {
			List<Carro> carros = db.getCarros();
			return carros;
		}catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<Carro>();
		}
	}
	//deleta carro pelo id
	public boolean delete(Integer id) {
		try {
			return db.delete(id);
		}catch(SQLException e) {
			return false;
		}
	}
	
	//salva ou edita o carro
	public boolean save(Carro carro) {
		try {
			db.save(carro);
			return true;
		}catch(SQLException e) {
			return false;
		}
	}
	
	
}
