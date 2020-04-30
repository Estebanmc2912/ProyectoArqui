package serviciosD;

import java.util.List;

import javax.ejb.Remote;
import model.*;
@Remote
public interface FachadaDRemote {
	public String addUsuario(String nom,String ape, String usern,String pasw,String correo);
	public String addProveedor(String nom, String correo,String nomUser,String password);
	public List<Usuario> findUsuario(String userName, String password);
	public List<Proveedor> findproveedor(String PName, String password);
	public List<Usuario> getAllUsuarios();
	public List<Proveedor> getAllProveedores();
	public List<Producto> getProductos(int pag);
	public String ActualizarUsuario(Usuario u);
}
