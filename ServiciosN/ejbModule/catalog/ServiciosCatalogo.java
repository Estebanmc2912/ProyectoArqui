package catalog;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;


import ldatos.DelegadoDatosLocal;
import model.Producto;

/**
 * Session Bean implementation class ServiciosCatalogo
 */
@Stateful
@LocalBean
public class ServiciosCatalogo implements ServiciosCatalogoLocal {
	@EJB DelegadoDatosLocal dl;
	private List<Producto> Catalogo;
	private List<Producto> Carro;
	private int numPag;
    /**
     * Default constructor. 
     */
    public ServiciosCatalogo() {
        this.Catalogo=new ArrayList<Producto>();
        this.Carro=new ArrayList<Producto>();
        this.numPag=0;
    }
	@Override
	public List<Producto> verCatalogo(int pag) {
			if(pag==1) {
				this.numPag=0;
			}
			if(pag%5==1) {
				this.numPag++;
				this.Catalogo=dl.getProductos(this.numPag-1);
			}
			int aux=pag%5;
			if(aux==0) {
				aux=5;
			}
			List<Producto> resultList=new ArrayList<Producto>();
			for(int i=((aux-1)*20);i<(aux*20);i++) {
				resultList.add(this.Catalogo.get(i));
			}
			
			return resultList;
	}


}
