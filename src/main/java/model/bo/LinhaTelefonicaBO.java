package model.bo;

import java.time.LocalDateTime;

import model.dao.LinhaTelefonicaDAO;
import model.dao.TelefoneDAO;
import model.vo.LinhaTelefonica;
import model.vo.Cliente;
import model.vo.Telefone;

public class LinhaTelefonicaBO {

	public LinhaTelefonicaDAO linhaTelefonicaDAO = new LinhaTelefonicaDAO();
	public TelefoneDAO telefoneDAO = new TelefoneDAO();
	
	public Cliente obterDonoAtualDoTelefone(int id) {
		return linhaTelefonicaDAO.obterDonoAtualDoTelefone(id);
	}

	public boolean liberarLinha(int id) {
		boolean desativarTelefone = false;
		boolean liberarLinha = false;
		
		Telefone telefone = telefoneDAO.consultar(id);
		telefone.setAtivo(false);
		desativarTelefone = telefoneDAO.atualizar(telefone);
		
		liberarLinha = linhaTelefonicaDAO.desativarLinhaAtual(id);
		
		return (liberarLinha && desativarTelefone);
	}

	public boolean criarLinha(Telefone telefoneSelecionado, Cliente clienteSelecionado) {
		boolean criouNovaLinha = false;
		boolean ativouTelefone = false;

		//1- criar um novo registro de linha telefônica
		LinhaTelefonica novaLinha = new LinhaTelefonica();
		novaLinha.setTelefone(telefoneSelecionado);
		novaLinha.setIdCliente(clienteSelecionado.getId());
		novaLinha.setDataAtivacao(LocalDateTime.now());
		novaLinha = linhaTelefonicaDAO.inserir(novaLinha);
		
		criouNovaLinha = novaLinha.getId() > 0;
		
		//2 - ativar o telefone
		telefoneSelecionado.setAtivo(true);
		ativouTelefone = telefoneDAO.atualizar(telefoneSelecionado);
		
		return criouNovaLinha && ativouTelefone;
	}

}
