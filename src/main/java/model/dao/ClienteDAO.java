package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.vo.Cliente;
import model.vo.Endereco;
import model.vo.LinhaTelefonica;

public class ClienteDAO implements BaseDAO<Cliente>{
	
	public Cliente inserir(Cliente novoCliente) {
		Connection conexao = Banco.getConnection();
		String sql = " INSERT INTO CLIENTE(NOME, CPF)" 
					+ "VALUES (?, ?);";
		
		//TODO como inserir o endereço e as linhas telefônicas?
		
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setString(1, novoCliente.getNome());
			stmt.setString(2, novoCliente.getCpf());
			
			stmt.execute();
			
			ResultSet chavesGeradas = stmt.getGeneratedKeys();
			if(chavesGeradas.next()) {
				novoCliente.setId(chavesGeradas.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao inserir cliente. Causa:" + e.getMessage());
		}
		
		return novoCliente;
	}
	
	public boolean atualizar(Cliente cliente) {
		boolean atualizou = false;
		Connection conexao = Banco.getConnection();
		String sql = " UPDATE CLIENTE "
					+" SET NOME=?, CPF=? "
					+" WHERE ID=? ";
		
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getCpf());
			
			int linhasAfetadas = stmt.executeUpdate();
			atualizou = linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar cliente. Causa:" + e.getMessage());
		}
		
		return atualizou;
	}
	
	public boolean remover(int id) {
		boolean removeu = false;
		
		Connection conexao = Banco.getConnection();
		String sql = " DELETE FROM CLIENTE "
					+" WHERE ID=?";
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setInt(1, id);
			removeu = stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao remover cliente. Causa:" + e.getMessage());
		}		
		
		return removeu;
	}
	
	public Cliente consultar(int id) {
		Cliente clienteConsultado = null;
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM CLIENTE "
					+" WHERE ID=?";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);
		
		try {
			stmt.setInt(1, id);
			ResultSet resultado = stmt.executeQuery();
			
			if(resultado.next()) {
				clienteConsultado = construirDoResultSet(resultado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente (id:" + id + ". Causa:" + e.getMessage());
		}
		
		return clienteConsultado;
	}
	
	private Cliente construirDoResultSet(ResultSet resultado) throws SQLException {
		Cliente clienteConsultado = new Cliente();
		clienteConsultado.setId(resultado.getInt("id"));
		clienteConsultado.setCpf(resultado.getString("cpf"));
		clienteConsultado.setNome(resultado.getString("nome"));
		
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		Endereco enderecoDoCliente = enderecoDAO.consultar(resultado.getInt("id_endereco"));
		clienteConsultado.setEndereco(enderecoDoCliente);
		
		LinhaTelefonicaDAO linhaDAO = new LinhaTelefonicaDAO();
		ArrayList<LinhaTelefonica> linhas = linhaDAO.consultarPorIdCliente(resultado.getInt("id_cliente"));
		clienteConsultado.setLinhas(linhas);
		return null;
	}

	public ArrayList<Cliente> consultarTodos(){
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM CLIENTE ";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);
		
		try {
			ResultSet resultado = stmt.executeQuery();
			
			while(resultado.next()) {
				Cliente clienteConsultado = construirDoResultSet(resultado);
				clientes.add(clienteConsultado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os clientes. Causa:" + e.getMessage());
		}
		
		return clientes;
	}
}
