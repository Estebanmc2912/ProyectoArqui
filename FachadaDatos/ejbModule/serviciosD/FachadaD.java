package serviciosD;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import model.*;

/**
 * Session Bean implementation class FachadaD
 */
@Stateless
@LocalBean
public class FachadaD implements FachadaDRemote {
	@PersistenceContext(unitName="EntidadesPU",type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public FachadaD() {
    }

    @Override
    public List<Usuario> findUsuario(String userName, String password) {
	    String consulta = "SELECT u FROM Usuario u WHERE u.username=:userName AND u.password=:password";
	    TypedQuery<Usuario> query = entityManager.createQuery(consulta, Usuario.class);
	    query.setParameter("userName", userName);
	    query.setParameter("password", password);
	    query.setMaxResults(1);
	    List<Usuario> resultList = query.getResultList();
	    return resultList;
    }
    @Override
    public String addUsuario(String nom,String ape, String usern,String pasw,String correo) {
    	Usuario newUsuario = new Usuario();
    	newUsuario.setNombres(nom);
		newUsuario.setApellidos(ape);
		newUsuario.setUsername(usern);
		newUsuario.setPassword(pasw);
		newUsuario.setCorreo(correo);
		newUsuario.setMediosP(0);
	    entityManager.persist(newUsuario);
	    return "insertado";
   
	 }
    @Override
    public List<Usuario> getAllUsuarios() {
    	return entityManager.createQuery("SELECT p FROM Usuario p", Usuario.class).getResultList();
    }

	@Override
	public String addProveedor(String nom, String correo,String nomUser,String password) {
		Proveedor newProv = new Proveedor();
		newProv.setNombre(nom);
	    newProv.setCorreo(correo);
	    newProv.setNomUser(nomUser);
	    newProv.setPassword(password);;
	    entityManager.persist(newProv);
	    return "insertado";
	}

	@Override
	public List<Proveedor> findproveedor(String PName, String password) {
		String consulta = "SELECT p FROM Proveedor p WHERE p.nomUser=:PName AND p.password=:password";
		TypedQuery<Proveedor> query = entityManager.createQuery(consulta, Proveedor.class);
		query.setParameter("PName", PName);
		query.setParameter("password", password);
		query.setMaxResults(1);
		List<Proveedor> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Proveedor> getAllProveedores() {
		return entityManager.createQuery("SELECT p FROM Proveedor p", Proveedor.class).getResultList();
		
	}

	@Override
	public List<Producto> getProductos(int pag) {
		String consulta = "SELECT p FROM Producto p";
	    TypedQuery<Producto> query = entityManager.createQuery(consulta, Producto.class);
	    query.setMaxResults(100);
	    query.setFirstResult(pag*100);
	    List<Producto> resultList = query.getResultList();
	    return resultList;
	 
	}
	@Override
	@Transactional
	public String ActualizarUsuario(Usuario u) {
		Usuario u2=entityManager.find(Usuario.class, u.getIdUsuario());
		u2.setCorreo(u.getCorreo());
		u2.setPersonalKey(u.getPersonalKey());
		u2.setNombres(u.getNombres());
		u2.setApellidos(u.getApellidos());
		u2.setMediosP(u.getMediosP());
		entityManager.merge(u2);
	    return "Nice";
	 
	}

	@Override
	public void presistCompra(Factura factura) {
		for (Producto p:factura.getProductos()) {
			Producto prod=entityManager.find(Producto.class, p.getIdproducto());
			prod.setCantidad(prod.getCantidad()-1);
			entityManager.merge(prod);
		}
		entityManager.persist(factura);
		
	
	}

}
