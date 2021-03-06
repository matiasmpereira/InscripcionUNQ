package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.TipoIncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.YaExisteTipoDeIncidenciaException;
import ar.edu.unq.inscripcionunq.spring.service.IncidenciaService;
import ar.edu.unq.inscripcionunq.spring.service.TipoIncidenciaService;
import ar.edu.unq.inscripcionunq.spring.service.TipoEstadoIncidenciaService;

@RestController
public class IncidenciaController {

	@Autowired
	private TipoIncidenciaService tipoIncidenciaServiceImp;

	@Autowired
	private IncidenciaService incidenciaServiceImp;

	@Autowired
	private TipoEstadoIncidenciaService tipoEstadoincidenciaServiceImp;

	@GetMapping("/tipoIncidencias")
	public ResponseEntity<List> getTipoIncidencias() {
		return ResponseEntity.ok().body(tipoIncidenciaServiceImp.getTipoIncidencias().stream()
				.map(tipoIncidencia -> new TipoIncidenciaJson(tipoIncidencia)).collect(Collectors.toList()));
	}

	@GetMapping("/tipoEstadosIncidencias")
	public ResponseEntity<List> getTipoEstadosIncidencias() {
		
		return ResponseEntity.ok().body(tipoEstadoincidenciaServiceImp.getTipoEstadosIncidencias().stream()
		.map(tipoIncidencia -> new String(tipoIncidencia.getEstado())).collect(Collectors.toList()));
	}

	@PutMapping("/nuevoTipoIncidencia")
	public ResponseEntity agregarNuevoTipoIncidencia(@RequestBody TipoIncidenciaJson tipoIncidenciaJson) {
		try {
			tipoIncidenciaServiceImp.agregarNuevoTipoIncidencia(tipoIncidenciaJson);
		} catch (YaExisteTipoDeIncidenciaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));

		}
		
		return ResponseEntity.ok().build();
	}

	@PostMapping("/actualizarTipoIncidencia")
	public ResponseEntity actualizarTipoIncidencia(@RequestBody TipoIncidenciaJson tipoIncidenciaJson) {
		try {
			tipoIncidenciaServiceImp.actualizarTipoIncidencia(tipoIncidenciaJson);
		} catch (YaExisteTipoDeIncidenciaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		
		return ResponseEntity.ok().build();
	}

	@GetMapping("/incidencias/{idTipoIncidencia}")
	public ResponseEntity<List> getIncidenciasDelTipo(@PathVariable String idTipoIncidencia) {
		return ResponseEntity.ok().body(incidenciaServiceImp.getIncidenciasJson(idTipoIncidencia));
	}

	@PutMapping("/incidencia")
	public ResponseEntity agregarIncidencia(@RequestBody IncidenciaJson incidenciaJson) {
		try {
			incidenciaServiceImp.agregarIncidencia(incidenciaJson);
		} catch (EmailInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		
		return ResponseEntity.ok().build();
	}

	@PostMapping("/actualizar-incidencia")
	public ResponseEntity actualizarIncidencia(@RequestBody IncidenciaJson incidenciaJson) {
		incidenciaServiceImp.actualizarIncidencia(incidenciaJson);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/incidencias")
	public ResponseEntity<List> getIncidencias() {
		return ResponseEntity.ok().body(incidenciaServiceImp.getIncidenciasJson());
	}
}