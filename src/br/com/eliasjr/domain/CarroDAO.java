package br.com.eliasjr.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

public class CarroDAO extends BaseDAO {
	
	
	public Carro getCarroById(Integer id) throws SQLException {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				Carro c = createCarro(rs);
				rs.close();
				return c;
			}
		}finally {
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		return null;
	}
	public void save(Carro c) throws SQLException {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			if(c.getId() == null) {
				stmt = conn.prepareStatement("insert into carro (nome,descricao,tipo)values(?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				
			}else {
				stmt = conn.prepareStatement("update carro set nome=?,descricao=?,tipo=? where id=?");
				
			}
			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getDesc());
			stmt.setString(3, c.getTipo());
			
			if(c.getId() != null) {
				//update
				stmt.setInt(4, c.getId());
			}
			
			int count  = stmt.executeUpdate();
			if(count == 0) {
				throw new SQLException("erro ao inserir carro");
			}
			//se inseriu, ler o id auto incremento
			if(c.getId() == null) {
				Integer id = getGeneratedId(stmt);
				c.setId(id);
			}
			
		}finally {
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
	}
	// id gerado com o campo auto incremento
	public static Integer getGeneratedId(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		if(rs.next()) {
			Integer id = rs.getInt(1);
			return id;
		}
		return 0;
	}
	
	
	public List<Carro> getCarros() throws SQLException{
		List<Carro> carros = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Carro c = createCarro(rs);
				carros.add(c);
			}
			rs.close();
		}finally {
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		return carros;
	}
	
	public Carro createCarro(ResultSet rs) throws SQLException{
		Carro c = new Carro();
		c.setId(rs.getInt("id"));
		c.setNome(rs.getString("nome"));
		c.setDesc(rs.getString("descricao"));
		c.setTipo(rs.getString("tipo"));
		return c;
	}


	public boolean delete(Integer id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("delete from carro where id = ?");
			stmt.setLong(1, id);
			int count = stmt.executeUpdate();
			return count > 0;
		}finally {
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
	}
}