package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;




/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
@Table(name="Usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUsuario;

	private String apellidos;

	private String nombres;

	private String password;

	private String username;
	
	@Column(name="correo")
	private String correo;
	
	@Column(name="personalkey")
	private String personalKey;
	
	@Column(name="mediosPago")
	private int mediosP;

	public Usuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPersonalKey() {
		return personalKey;
	}

	public void setPersonalKey(String personalKey) {
		this.personalKey = personalKey;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getMediosP() {
		return mediosP;
	}

	public void setMediosP(int mediosP) {
		this.mediosP = mediosP;
	}



}