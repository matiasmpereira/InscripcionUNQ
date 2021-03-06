package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaJson;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

public interface EstudianteDao extends GenericDao<Estudiante> {

	List<MateriaJson> materiasDesaprobadasConComisionesDisponiblesDeUsuario(Long idUsuario);

	Integer getNroEstudiantesPorComision(Long idComision);
}