package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.TelefonePosJava;
import model.UserPosJava;

public class UserPosDAO {
	private Connection connection;

	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(UserPosJava userposjava) {
		String sql = "insert into userposjava ( nome, email) values (?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userposjava.getNome());
			preparedStatement.setString(2, userposjava.getEmail());
			preparedStatement.execute();
			connection.commit();

		} catch (Exception e) {

			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
		}

	}
	
	public void salvartelefone(TelefonePosJava telefonePosJava) {
		String sql = "INSERT INTO telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?); ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, telefonePosJava.getNumero());
			preparedStatement.setString(2, telefonePosJava.getTipo());
			preparedStatement.setLong(3, telefonePosJava.getUsuario());
			preparedStatement.execute();
			connection.commit();

		} catch (Exception e) {

			try {
				connection.rollback(); // reverte operação
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
		}

	}
	
	public List<UserPosJava> listar() throws SQLException {
		List<UserPosJava> list = new ArrayList<UserPosJava>();

		String sql = "Select * from userposjava";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			UserPosJava user = new UserPosJava();
			user.setId(resultSet.getLong("id"));
			user.setNome(resultSet.getString("nome"));
			user.setEmail(resultSet.getString("email"));

			list.add(user);
		}
		return list;
	}
	
	public List<BeanUserFone> listaBeanUserFones (Long iduser) throws SQLException{
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		String sql = "select nome, numero, email from telefoneuser as fone inner join userposjava as userp on fone.usuariopessoa = userp.id";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			BeanUserFone beanUserfone = new BeanUserFone();
			beanUserfone.setEmail(resultSet.getString("email"));
			beanUserfone.setNome(resultSet.getString("nome"));
			beanUserfone.setNumero(resultSet.getString("numero"));
			
			beanUserFones.add(beanUserfone);
		}
		
		return beanUserFones;
	}
	

	public UserPosJava buscar(Long id) throws SQLException {
		UserPosJava retorno = new UserPosJava();
		String sql = "select * from userposjava where id = " + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			retorno.setId(resultSet.getLong("id"));
			retorno.setNome(resultSet.getString("nome"));
			retorno.setEmail(resultSet.getString("email"));
		}
		return retorno;
	}

	public void atualizar(UserPosJava userPosJava) {
		try {
			String sql = "update userposjava set nome = ? where id =" + userPosJava.getId();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userPosJava.getNome());
			statement.execute();

			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void deletar(Long id) {
		try {
			String sql = "delete from userposjava where id = " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
	
	public void deleteFonesPorUser(Long idUser) {
		try {

			String sqlFone = "delete from telefoneuser where usuariopessoa =" + idUser;
			String sqlUser = "delete from userposjava where id =" + idUser;

			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

}
