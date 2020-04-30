package catalog;

import java.util.List;

import javax.ejb.Local;

import model.Producto;

@Local
public interface ServiciosCatalogoLocal {

	public List<Producto> verCatalogo(int pag);

	

}
