package pos_java_jdbc.pos_java_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import dao.UserPosDAO;
import model.BeanUserFone;
import model.TelefonePosJava;
import model.UserPosJava;


public class TesteBancoJDBC{
	
	@Test
	public void initInsert() {
		UserPosDAO dao = new UserPosDAO();
		UserPosJava user = new UserPosJava();

		user.setNome("teste");
		user.setEmail("tes@gmail");		
		
		dao.salvar(user);
		
	}
	
	@Test
	public void initInsertTelefone() {
		UserPosDAO dao = new UserPosDAO();
		TelefonePosJava telefone = new TelefonePosJava();

		telefone.setNumero("61 998487559");
		telefone.setTipo("celular");
		telefone.setUsuario(8L);
		
		dao.salvartelefone(telefone);
		
	}
	
	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();
		try {
			List<UserPosJava> list = dao.listar();
			for (UserPosJava userPosJava : list) {
				System.out.println(userPosJava);
				System.out.println("------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initListarWithJoin() {
		UserPosDAO dao = new UserPosDAO();
		try {
			List<BeanUserFone> list = dao.listaBeanUserFones(8L);
			for (BeanUserFone beanUserFone : list) {
				System.out.println(beanUserFone);
				System.out.println("------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();
		try {
			UserPosJava user  = dao.buscar(1L);
			System.out.println(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initUpdate() {
		try {
			UserPosDAO dao = new UserPosDAO();
			UserPosJava objBanco = dao.buscar(4L);
			objBanco.setNome("ronaldo");
			dao.atualizar(objBanco);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}	
	
	@Test
	public void initDelete() {
		try {
			UserPosDAO dao = new UserPosDAO();
			dao.deletar(5L);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeDeleteUserFone() {

		UserPosDAO dao = new UserPosDAO();
		dao.deleteFonesPorUser(8L);

	}
}
