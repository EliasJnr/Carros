package br.com.eliasjr.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.eliasjr.domain.Carro;
import br.com.eliasjr.domain.CarroService;
import br.com.eliasjr.domain.ListaCarros;
import br.com.eliasjr.domain.Response;
import br.com.eliasjr.util.JAXBUtil;
import br.com.eliasjr.util.RegexUtil;
import br.com.eliasjr.util.ServletUtil;


@WebServlet("/carros/*")
public class CarrosServlet extends HttpServlet {

	private CarroService carroService = new CarroService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//cria o carro
		Carro carro = getCarroFromRequest(req);
		//salva o carro
		carroService.save(carro);
		//escreve o json do novo carro salvo
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(carro);
		ServletUtil.writeJSON(resp, json);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Carro> carros = carroService.getCarros();
		ListaCarros lista = new ListaCarros();
		lista.setCarros(carros);
		
		//Escreve o json na response do servlet com application/json
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(lista);
		ServletUtil.writeJSON(resp, json);
		
		/*Escreve o xml na response do servlet com application/xml
		String xml = JAXBUtil.toXML(lista);
		ServletUtil.writeXML(resp, json);*/
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestUri = req.getRequestURI();
		Integer id = RegexUtil.matchId(requestUri);
		if(id != null) {
			carroService.delete(id);
			Response r = Response.Ok("carro excluído com sucesso");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(r);
			ServletUtil.writeJSON(resp, json);
		}else {
			resp.sendError(404,"url inválida");
		}
	}
	
	// lê os parametros da request e cria o objeto novo
	private Carro getCarroFromRequest(HttpServletRequest request) {
		Carro c = new Carro();
		String id = request.getParameter("id");
		if(id != null) {
			//se informou id, busca no banco
			c = carroService.getCarro(Integer.parseInt(id));
			
		}
		c.setNome(request.getParameter("nome"));
		c.setDesc(request.getParameter("descricao"));
		c.setTipo(request.getParameter("tipo"));
		return c;
		
	}
	
	
}
