package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

@Repository

public class EncuestaDaoImp extends GenericDaoImp<Encuesta> implements EncuestaDao {

	@Override
	protected Class<Encuesta> getDomainClass() {
		return Encuesta.class;
	}

	public List<Encuesta> getTodasLasEncuestasActivasParaDni(String dni) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("select p from Encuesta p join p.estudiantes s where s.dni=:dni ").setParameter("dni", dni)
				.getResultList();
	}

	@Override
	public Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idPoll) throws UserInPollNotFoundException {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Estudiante> query = session.createQuery(
				"select estudiante from Encuesta as encuesta inner join encuesta.estudiantes estudiante where encuesta.id=:idEncuesta and estudiante.dni=:dni  ");
		query.setParameter("idEncuesta", idPoll);
		query.setParameter("dni", dni);
		List<Estudiante> results = query.getResultList();
		if (results.isEmpty()) {
			throw new UserInPollNotFoundException();
		}
		return query.getResultList().get(0);
	}
}
