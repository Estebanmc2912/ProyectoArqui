package fachada;

import java.util.List;

import javax.ejb.Remote;


import model.*;

@Remote
public interface FachadaNRemote {
	public List<Usuario> findUsuario(String userName, String password);
	public String RegistrarUsuario(String nombre, String Apellido, String username, String password,String Correo);
	public String RegistrarProveedor(String nom, String correo,String nomUser,String password);
	public Usuario autenticarUsuario(String username, String password);
	public Proveedor autenticarProveedor(String nomUser, String password);
	public List<Proveedor> ListarProveedores();
	public List<Usuario> ListarUsuarios();
	public List<Producto> verCatalogo(int pag);
	public Usuario confirmarCompra(String year,String mes, String num, String cod,Usuario u);
	public int realizarCompra(List<Producto> carro, Usuario u,int tot);

}
