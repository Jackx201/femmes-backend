package com.sistema.blog.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "publicaciones", uniqueConstraints = { @UniqueConstraint(columnNames = { "titulo" }) })
public class Publicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "usuario", nullable = false)
	private String usuario;
	
	@Column(name = "numero_contacto", nullable = false)
	private String numero_contacto;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	
	@Column(name = "ultima_localizacion", nullable = false)
	private String ultima_localizacion;
	
	@Column(name = "status", nullable = false)
	private String status;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	@Column(name = "img", nullable = true)
	private String img;

	@JsonBackReference
	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comentario> comentarios = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	public String getNumeroContacto() {
		return numero_contacto;
	}

	public void setNumeroContacto(String numero_contacto) {
		this.numero_contacto = numero_contacto;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getUltimaLocalizacion() {
		return ultima_localizacion;
	}

	public void setUltimaLocalizacion(String ultima_localizacion) {
		this.ultima_localizacion = ultima_localizacion;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	

	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Publicacion() {
		super();
	}

	public Publicacion(Long id, String titulo, String descripcion, String usuario, String estado, String ultima_localizacion, String status, String numero_contacto, String img) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.usuario = usuario;
		//TO-DO Fecha_Desaparición
		this.numero_contacto = numero_contacto;
		this.estado = estado;
		this.ultima_localizacion = ultima_localizacion;
		this.status = status;
		this.descripcion = descripcion;
		this.img = img;
	}

}
