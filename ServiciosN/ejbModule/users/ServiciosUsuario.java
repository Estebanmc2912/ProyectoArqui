package users;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ldatos.DelegadoDatosLocal;
import model.Proveedor;
import model.Usuario;



/**
 * Session Bean implementation class ServiciosUsuario
 */
@Stateless
@LocalBean
public class ServiciosUsuario implements ServiciosUsuarioLocal {
	@EJB DelegadoDatosLocal dl;
    /**
     * Default constructor. 
     */
    public ServiciosUsuario() {
    }

	@Override
	public List<Usuario> findUsuario(String userName, String password) {
		return dl.findUsuario(userName, password);
	}

	@Override
	public String RegistrarUsuario(String nombre, String Apellido, String username, String password,String correo) {
		return dl.RegistrarUsuario(nombre, Apellido, username, password ,correo);
	}

	@Override
	public String RegistrarProveedor(String nom, String correo, String nomUser, String password) {
		return dl.RegistrarProveedor(nom, correo, nomUser, password);
	}

	@Override
	public Usuario autenticarUsuario(String username, String password) {
		return dl.autenticarUsuario(username, password);
	}

	@Override
	public Proveedor autenticarProveedor(String nomUser, String password) {
		return dl.autenticarProveedor(nomUser, password);
	}

	@Override
	public List<Proveedor> ListarProveedores() {
		return dl.ListarProveedores();
	}

	@Override
	public List<Usuario> ListarUsuarios() {
		return dl.ListarUsuarios();
	}
	

}
