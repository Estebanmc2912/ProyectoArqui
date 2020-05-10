package fachada;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import catalog.ServiciosCatalogoLocal;
import compra.ServiciosCompraLocal;
import fachada.FachadaNRemote;
import model.*;
import users.ServiciosUsuarioLocal;

/**
 * Session Bean implementation class FachadaN
 */
@Stateless
@LocalBean
public class FachadaN implements FachadaNRemote {
	@EJB ServiciosUsuarioLocal su;
	@EJB ServiciosCatalogoLocal sc;
	@EJB ServiciosCompraLocal scom;
    /**
     * Default constructor. 
     */
    public FachadaN() {
      
    }

	@Override
	public List<Usuario> findUsuario(String userName, String password) {	
		return su.findUsuario(userName, password);
	}
	public String RegistrarUsuario(String nombre, String apellido, String username, String password,String Correo) {
		return su.RegistrarUsuario(nombre, apellido, username, password,Correo);		
	}

	@Override
	public String RegistrarProveedor(String nom, String correo,String nomUser,String password) {
		
		return su.RegistrarProveedor(nom, correo, nomUser, password);
			
	}

	@Override
	public Usuario autenticarUsuario(String username, String password) {
	
		return su.autenticarUsuario(username, password);
		
	}

	@Override
	public Proveedor autenticarProveedor(String nomUser, String password) {

		return su.autenticarProveedor(nomUser, password);
		
	}

	@Override
	public List<Proveedor> ListarProveedores() {

		//return su.ListarProveedores();
		return null;
		
	}

	@Override
	public List<Usuario> ListarUsuarios() {
	
		return su.ListarUsuarios();
	
	}
	@Override
	public List<Producto> verCatalogo(int pag) {
		return sc.verCatalogo(pag);
	}


	@Override
	public Usuario confirmarCompra(String year,String mes, String num, String cod,Usuario u) {	
		return scom.confirmarCompra(year,mes,num,cod,u);
	}

	@Override
	public int realizarCompra(List<Producto> carro, Usuario u,int tot) {
		return scom.realizarCompra(carro,u,tot);
	}
}
