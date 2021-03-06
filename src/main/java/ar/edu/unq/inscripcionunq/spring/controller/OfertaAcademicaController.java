package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.OfertaAcademicaJson;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.CopiaOfertaMismoPeriodoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.GeneracionDeCodigoException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.service.OfertaAcademicaService;

@RestController
public class OfertaAcademicaController {

	@Autowired
	OfertaAcademicaService ofertaAcademicaServiceImpl;

	@GetMapping("/ofertas-academicas")
	public ResponseEntity<List> getOfertas() {
		return ResponseEntity.ok().body(ofertaAcademicaServiceImpl.getOfertasAcademicasJson());
	}

	@PutMapping("/ofertas-academicas/crearOferta/")
	public ResponseEntity crearNuevaOferta(@RequestBody OfertaAcademicaJson ofertaJson) {
		try {
			ofertaAcademicaServiceImpl.crearOferta(ofertaJson);
		} catch (DescripcionInvalidaException | CodigoInvalidoException | EstadoInvalidoException
				| NombreInvalidoException | GeneracionDeCodigoException e) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/ofertas-academicas/actualizarOferta/")
	public ResponseEntity actualizarOferta(@RequestBody OfertaAcademicaJson ofertaJson) {
		try {
			ofertaAcademicaServiceImpl.actualizarOferta(ofertaJson);
		} catch (DescripcionInvalidaException | CodigoInvalidoException | EstadoInvalidoException
				| NombreInvalidoException | OfertaNoExisteException | GeneracionDeCodigoException e) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/oferta-academica/comisiones/{idOferta}")
	public ResponseEntity getComisionesEnOferta(@PathVariable String idOferta) throws FormatoNumeroIdException {
		return ResponseEntity.ok().body(ofertaAcademicaServiceImpl.getComisionesEnOferta(idOferta));
	}

	@DeleteMapping("/oferta-academica/eliminarComision/{idComision}/{idOferta}")
	public ResponseEntity quitarComisionDeOferta(@PathVariable String idComision, @PathVariable String idOferta)
			throws FormatoNumeroIdException {
		try {
			ofertaAcademicaServiceImpl.quitarComisionDeOferta(idComision, idOferta);
		} catch (ObjectoNoEncontradoEnBDException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/oferta-academica/actualizar-comisiones/{idOferta}")
	public ResponseEntity actualizarComisiones(@PathVariable String idOferta, @RequestBody List<IdJson> idsJson) {
		try {
			ofertaAcademicaServiceImpl.actualizarComisiones(idOferta, idsJson);
		} catch (FormatoNumeroIdException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/oferta-academica/ofertasEnPeriodo/{idPeriodo}")
	public ResponseEntity getOfertasEnPeriodo(@PathVariable String idPeriodo) {
		try {
			return ResponseEntity.ok().body(ofertaAcademicaServiceImpl.getOfertasJsonEnPeriodo(idPeriodo));
		} catch (FormatoNumeroIdException | PeriodoInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
	}

	@PostMapping("/oferta-academica/clonarOferta-academica")
	public ResponseEntity clonarOfertaAcademica(@RequestBody OfertaAcademicaJson ofertaAcademicaJson) {
		Long id;
		try {
			id = ofertaAcademicaServiceImpl.clonarOfertaAcademica(ofertaAcademicaJson);
		} catch (CopiaOfertaMismoPeriodoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().body(id);
	}
}