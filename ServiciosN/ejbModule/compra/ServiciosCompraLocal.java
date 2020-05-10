package compra;

import java.util.List;

import javax.ejb.Local;


import model.Producto;
import model.Usuario;

@Local
public interface ServiciosCompraLocal {
	public int verificarTarjeta();
	public Usuario confirmarCompra(String year,String mes, String num, String cod,Usuario u);
	public int realizarCompra(List<Producto> carro, Usuario u,int tot);


}
