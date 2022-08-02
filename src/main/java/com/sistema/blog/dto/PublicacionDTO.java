package com.sistema.blog.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sistema.blog.entidades.Comentario;

public class PublicacionDTO {

	private Long id;
	
	@NotEmpty
	@Size(min = 2,message = "El titulo de la publicación deberia tener al menos 2 caracteres")
	private String titulo;
	
	@NotEmpty
	@Size(min = 10,message = "La descripción de la publicación deberia tener al menos 10 caracteres")
	private String descripcion;
	
	@NotEmpty
	private String usuario;
	
	@NotEmpty
	private Date fecha_desaparicion;
	
	@NotEmpty
	private String estado;
	
	@NotEmpty
	private String ultima_localizacion;
	
	@NotEmpty
	private String status;
	
	@NotEmpty
	private String img;
	
	@NotEmpty
	private String numero_contacto;
	
	private Set<Comentario> comentarios;

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
	
	public Date getFechaDesaparicion() {
		return fecha_desaparicion;
	}

	public void setFechaDesaparicion(Date fecha_desaparicion) {
		this.fecha_desaparicion = fecha_desaparicion;
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
	
	public String getNumeroContacto() {
		return numero_contacto;
	}

	public void setNumeroContacto(String numero_contacto) {
		this.numero_contacto = numero_contacto;
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

	public PublicacionDTO() {
		super();
	}

}
