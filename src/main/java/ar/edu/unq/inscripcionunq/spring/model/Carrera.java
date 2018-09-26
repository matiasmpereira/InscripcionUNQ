package ar.edu.unq.inscripcionunq.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "Carrera")
public class Carrera extends BaseEntity {
	@Column(unique = true)
	private String codigo;
	private String descripcion;
	@Enumerated(EnumType.STRING)
	private TypeStatus estado = TypeStatus.ENABLED;

	public Carrera() {

	}

	public Carrera(String codigo, String descripcion) {
		this.setCodigo(codigo);
		this.setDescripcion(descripcion);
	}

	public TypeStatus getEstado() {
		return this.estado;
	}

	private void setEstado(TypeStatus estado) {
		this.estado = estado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void deshabilitar() {
		this.setEstado(TypeStatus.DISABLED);
	}

}