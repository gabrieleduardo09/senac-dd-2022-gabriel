package view;

import java.util.ArrayList;
import java.util.List;

import model.vo.Cliente;
import model.vo.Endereco;
import model.vo.Telefone;

public class Executavel04 {

	public static void main(String[] args) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		Endereco endereco01 = new Endereco("Mauro Ramos", "10", "Florianópolis", "SC", "88320-005");
		
		List<Telefone> telefonesDoPele = new ArrayList<Telefone>();
		Telefone tel1 = new Telefone("48", "3232-5555", Telefone.TIPO_FIXO, true);
		Telefone tel2 = new Telefone("47", "3232-1010", Telefone.TIPO_MOVEL, true);
		
		telefonesDoPele.add(tel1);
		telefonesDoPele.add(tel2);
		
		Cliente pele = new Cliente("Edson Arantes", "01001011100", endereco01, telefonesDoPele);
		
		clientes.add(pele);
		
		for(Telefone t: pele.getTelefones()) {
			System.out.println(t.getNumero());
		}
	}

}
