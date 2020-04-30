package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the proveedor database table.
 * 
 */
@Entity
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM Proveedor p")
public class Proveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id_Proveedor;

	private String correo;

	private String nombre;

	private String nomUser;

	private String password;

	public Proveedor() {
	}

	public int getId_Proveedor() {
		return this.id_Proveedor;
	}

	public void setId_Proveedor(int id_Proveedor) {
		this.id_Proveedor = id_Proveedor;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNomUser() {
		return this.nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}