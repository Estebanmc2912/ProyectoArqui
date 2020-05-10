package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Factura
 *
 */
@Entity
@Table(name="Factura")
@NamedQuery(name="Factura.findAll", query="SELECT f FROM Factura f")
public class Factura implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFactura", updatable = false, nullable = false)
	private int idFactura;

	private String nombreCli;

	private String ApeCli;

	@Temporal(TemporalType.DATE)
	private Date fecha;
    @OneToMany
	private List<Producto> productos;

	private int total;

	private String correo;
	
	public Factura() {
		super();
	}
	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public String getNombreCli() {
		return nombreCli;
	}
	public void setNombreCli(String nombreCli) {
		this.nombreCli = nombreCli;
	}
	public String getApeCli() {
		return ApeCli;
	}
	public void setApeCli(String apeCli) {
		ApeCli = apeCli;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
   
}
