package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import model.vo.Cliente;
import model.vo.Endereco;
import model.vo.LinhaTelefonica;
import model.vo.Telefone;
import util.DateUtils;

public class LinhaTelefonicaDAO {
	public LinhaTelefonica inserir(LinhaTelefonica novaLinha) {
		Connection conexao = Banco.getConnection();
		String sql = " INSERT INTO LINHA_TELEFONICA(ID_CLIENTE, ID_TELEFONE, DT_ATIVACAO, DT_DESATIVACAO)"
				+ "VALUES (?, ?, ?, ?);";

		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);

		try {
			stmt.setInt(1, novaLinha.getIdCliente());
			stmt.setInt(2, novaLinha.getTelefone().getId());
			stmt.setObject(3, novaLinha.getDataAtivacao());
			stmt.setObject(4, novaLinha.getDataDesativacao());

			stmt.execute();

			ResultSet chavesGeradas = stmt.getGeneratedKeys();
			if (chavesGeradas.next()) {
				novaLinha.setId(chavesGeradas.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao inserir linha telef?nica. Causa:" + e.getMessage());
		}

		return novaLinha;
	}

	public boolean atualizar(LinhaTelefonica linhaTelefonica) {
		boolean atualizou = false;
		Connection conexao = Banco.getConnection();
		String sql = " UPDATE LINHA_TELEFONICA "
				+ " SET ID_CLIENTE=?, ID_LINHA_TELEFONICA=?, DT_ATIVACAO=?, DT_DESATIVACAO=? " + " WHERE ID=? ";

		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);

		try {
			stmt.setInt(1, linhaTelefonica.getIdCliente());
			stmt.setInt(2, linhaTelefonica.getTelefone().getId());
			stmt.setObject(3, linhaTelefonica.getDataAtivacao());
			stmt.setObject(4, linhaTelefonica.getDataDesativacao());

			int linhasAfetadas = stmt.executeUpdate();
			atualizou = linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar linha telef?nica. Causa:" + e.getMessage());
		}

		return atualizou;
	}

	public boolean remover(int id) {
		boolean removeu = false;

		Connection conexao = Banco.getConnection();
		String sql = " DELETE FROM LINHA_TELEFONICA " + " WHERE ID=?";
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);

		try {
			stmt.setInt(1, id);
			removeu = stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao remover linha telef?nica. Causa:" + e.getMessage());
		}

		return removeu;
	}

	public LinhaTelefonica consultar(int id) {
		LinhaTelefonica linhaTelefonicaConsultada = null;
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM LINHA_TELEFONICA " + " WHERE ID=?";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);

		try {
			stmt.setInt(1, id);
			ResultSet resultado = stmt.executeQuery();

			if (resultado.next()) {
				linhaTelefonicaConsultada = construirDoResultSet(resultado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar linha telef?nica (id:" + id + ". Causa:" + e.getMessage());
		}

		return linhaTelefonicaConsultada;
	}

	private LinhaTelefonica construirDoResultSet(ResultSet resultado) throws SQLException {
		LinhaTelefonica linhaTelefonicaConsultada = new LinhaTelefonica();
		linhaTelefonicaConsultada.setId(resultado.getInt("id"));
		linhaTelefonicaConsultada.setIdCliente(resultado.getInt("id_cliente"));

		Timestamp dataAtivacao = resultado.getTimestamp("dt_ativacao");
		Timestamp dataDesativacao = resultado.getTimestamp("dt_desativacao");
		
		//FONTE: https://stackoverflow.com/questions/23263490/how-to-convert-java-sql-timestamp-to-localdate-java8-java-time
		if(dataAtivacao != null) {
			linhaTelefonicaConsultada.setDataAtivacao(dataAtivacao.toLocalDateTime());
		}

		if(dataDesativacao != null) {
			linhaTelefonicaConsultada.setDataDesativacao(dataDesativacao.toLocalDateTime());
		}

		TelefoneDAO telefoneDAO = new TelefoneDAO();
		Telefone t = telefoneDAO.consultar(resultado.getInt("id_telefone"));
		linhaTelefonicaConsultada.setTelefone(t);

		return linhaTelefonicaConsultada;
	}

	public ArrayList<LinhaTelefonica> consultarTodos() {
		ArrayList<LinhaTelefonica> linhaTelefonicas = new ArrayList<LinhaTelefonica>();
		String sql = " SELECT * FROM LINHA_TELEFONICA ";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);

		try {
			ResultSet resultado = stmt.executeQuery();

			while(resultado.next()) {
				LinhaTelefonica linhaTelefonicaConsultada = construirDoResultSet(resultado);
				linhaTelefonicas.add(linhaTelefonicaConsultada);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todas as linhas telef?nicas. Causa:" + e.getMessage());
		}

		return linhaTelefonicas;
	}

	public ArrayList<LinhaTelefonica> consultarPorIdCliente(int idCliente) {
		ArrayList<LinhaTelefonica> linhasDoCliente = new ArrayList<LinhaTelefonica>();
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM LINHA_TELEFONICA " + " WHERE ID_CLIENTE=?";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);

		try {
			stmt.setInt(1, idCliente);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				LinhaTelefonica linha = construirDoResultSet(resultado);
				linhasDoCliente.add(linha);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar linhas telef?nicas do cliente (idCliente:" + idCliente + ". Causa:"
					+ e.getMessage());
		}

		return linhasDoCliente;
	}

	public Cliente obterDonoAtualDoTelefone(int id) {
		Cliente donoLinhaTelefonica = null;
		Connection conexao = Banco.getConnection();
		String sql = " SELECT ID_CLIENTE FROM LINHA_TELEFONICA WHERE LINHA_TELEFONICA.ID_TELEFONE = ? AND LINHA_TELEFONICA.DT_DESATIVACAO IS NULL;";
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);

		try {
			stmt.setInt(1, id);
			ResultSet resultado = stmt.executeQuery();

			if (resultado.next()) {
				int idDono = resultado.getInt(1);
				ClienteDAO clienteDAO = new ClienteDAO();
				donoLinhaTelefonica = clienteDAO.consultar(idDono);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar linha telef?nica (id:" + id + ". Causa:" + e.getMessage());
		}

		//return linhaTelefonicaConsultada;
		return donoLinhaTelefonica;
	}

	public boolean desativarLinhaAtual(int id) {
		boolean desativou = false;
		Connection conexao = Banco.getConnection();
		
		String sql = " UPDATE LINHA_TELEFONICA 1 SET dt_desativacao = ? WHERE l.id_telefone = ? AND dt_desativacao IS NULL ";
		
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);
		
		try {
			stmt.setInt(1, id);
			int linhasAfetadas = stmt.executeUpdate();
			desativou = linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao desativar linha atual do telefone (" + id + "). Causa:" + e.getMessage());
		}
		
		return desativou;
	}
}
