package ldatos;

import java.util.List;

import javax.ejb.Local;

import model.Factura;
import model.Producto;
import model.Proveedor;
import model.Usuario;

@Local
public interface DelegadoDatosLocal {
	public List<Usuario> findUsuario(String userName, String password);
	public String RegistrarUsuario(String nombre, String Apellido, String username, String password,String Correo);
	public String RegistrarProveedor(String nom, String correo,String nomUser,String password);
	public Usuario autenticarUsuario(String username, String password);
	public Proveedor autenticarProveedor(String nomUser, String password);
	public List<Proveedor> ListarProveedores();
	public List<Usuario> ListarUsuarios();
	public List<Producto> getProductos(int i);
	public String ActualizarUsuario(Usuario u);
	public void persistCompra(Factura factura);
}
