package ldatos;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import model.*;
import serviciosD.FachadaDRemote;

/**
 * Session Bean implementation class DelegadoDatos
 */
@Stateless
@LocalBean
public class DelegadoDatos implements DelegadoDatosLocal {
	@EJB ServiceLocatorLocal sl;
    /**
     * Default constructor. 
     */
    public DelegadoDatos() {
    }

	@Override
	public List<Usuario> findUsuario(String userName, String password) {
		FachadaDRemote fr=sl.getService();
		return fr.findUsuario(userName, password);
	}
	@Override
	public String RegistrarUsuario(String nombre, String Apellido, String username, String password,String correo) {
    	FachadaDRemote services=sl.getService();
		List<Usuario> user=services.findUsuario(username, password);
		System.out.println("------------------------"+"");
		if(user.size()==0) {
			return services.addUsuario(nombre, Apellido, username, password,correo);	
		}else {
			return "Usuario ya registrado";
		}
	}

	@Override
	public String RegistrarProveedor(String nom, String correo, String nomUser, String password) {
		
		FachadaDRemote services=sl.getService();
		List<Proveedor> prov=services.findproveedor(nomUser, password);
		if(prov.size()==0) {
			return services.addProveedor(nom, correo, nomUser, password);
			
		}else {
			return "Proveedor ya registrado";
		}
	}

	@Override
	public Usuario autenticarUsuario(String username, String password) {
		FachadaDRemote services=sl.getService();
		List<Usuario> user=services.findUsuario(username, password);
		if(user.size()==1) {
			return user.get(0);
			
		}else 
			return null;
	
	}

	@Override
	public Proveedor autenticarProveedor(String nomUser, String password) {
		FachadaDRemote services=sl.getService();
		List<Proveedor> prov=services.findproveedor(nomUser, password);
		if(prov.size()==1) {
			return prov.get(0);
			
		}else 
			return null;
	}

	@Override
	public List<Proveedor> ListarProveedores() {
		FachadaDRemote services=sl.getService();
		List<Proveedor> prov=services.getAllProveedores();
		return prov;
	}

	@Override
	public List<Usuario> ListarUsuarios() {
		FachadaDRemote services=sl.getService();
		List<Usuario> user=services.getAllUsuarios();
		return user;
	}

	@Override
	public List<Producto> getProductos(int i) {
		FachadaDRemote services=sl.getService();
		return services.getProductos(i);
	}

	@Override
	public String ActualizarUsuario(Usuario u) {
		FachadaDRemote services=sl.getService();
		return services.ActualizarUsuario(u);
	}

	@Override
	public void persistCompra(Factura factura) {
		FachadaDRemote services=sl.getService();
		services.presistCompra(factura);
		
	}

}
