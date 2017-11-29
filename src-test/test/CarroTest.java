package test;

import java.util.List;

import br.com.eliasjr.domain.Carro;
import br.com.eliasjr.domain.CarroService;
import junit.framework.TestCase;

public class CarroTest extends TestCase {
	private CarroService carroService = new CarroService();
	
	public void testListaCarros() {
		List<Carro> carros = carroService.getCarros();
		assertNotNull(carros);
		//Valida se encontrou algo
		assertTrue(carros.size()>0);
		
	}
	
	
	public void testSalvar() {
		Carro c  = new Carro();
		c.setDesc("4x4 monstra");
		c.setNome("eowq0e875r");
		c.setTipo("pickup");
		System.out.println(carroService.save(c)+"");
	}
}
