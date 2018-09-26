package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.dao.EncuestaDao;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

@Service
@Transactional
public class EncuestaServiceImp extends GenericServiceImp<Encuesta> implements EncuestaService {

	@Autowired
	EstudianteService studentServiceImp;
	@Autowired
	ComisionService commissionServiceImp;

	@Override
	@Transactional

	public List<Encuesta> getTodasLasEncuestasActivasParaDni(String dni) {
		return ((EncuestaDao) genericDao).getTodasLasEncuestasActivasParaDni(dni);
	}

	@Override
	@Transactional
	public Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idPoll) throws UserInPollNotFoundException {
		return ((EncuestaDao) genericDao).getDatosDeUsuarioParaEncuesta(dni, idPoll);
	}

	@Override
	@Transactional
	public Boolean puedeGenerarPDF(String dni, Long idPoll) {
		try {
			return !this.getDatosDeUsuarioParaEncuesta(dni, idPoll).getRegistroComisiones().isEmpty();
		} catch (UserInPollNotFoundException e) {
		}
		return false;
	}

	@Override
	public void setComisionesSeleccionadas(String id, List<IdJson> idsJson) throws IdNumberFormatException,
			StudentNotExistenException, CommissionNotExistenException, VariasComisionesDeUnaMateriaException {
		Estudiante estudiante;
		try {
			estudiante = studentServiceImp.get(new Long(id));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new StudentNotExistenException();
		}
		estudiante.eliminarTodasLasComisionesInscripto();
		for (IdJson idJson : idsJson) {
			Comision comision;
			try {
				comision = commissionServiceImp.get(new Long(idJson.id));
			} catch (NumberFormatException e) {
				throw new IdNumberFormatException();
			} catch (ObjectNotFoundinDBException e) {
				throw new CommissionNotExistenException();

			}
			estudiante.agregarRegistroComisiones(comision);
		}
		studentServiceImp.update(estudiante);
	}

}