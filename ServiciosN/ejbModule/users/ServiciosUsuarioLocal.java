package users;

import java.util.List;

import javax.ejb.Local;

import model.*;

@Local
public interface ServiciosUsuarioLocal {
	public List<Usuario> findUsuario(String userName, String password);
	public String RegistrarUsuario(String nombre, String Apellido, String username, String password,String correo);
	public String RegistrarProveedor(String nom, String correo,String nomUser,String password);
	public Usuario autenticarUsuario(String username, String password);
	public Proveedor autenticarProveedor(String nomUser, String password);
	public List<Proveedor> ListarProveedores();
	public List<Usuario> ListarUsuarios();
}
