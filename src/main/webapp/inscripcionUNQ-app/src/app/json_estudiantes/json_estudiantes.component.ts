import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { UtilesService } from '../utiles.service';
import { Usuario } from '../autenticacion/usuario.model';
import { DialogosService } from '../dialogos.service';

@Component({
	selector: 'app-json_estudiantes',
	templateUrl: './json_estudiantes.component.html',
	styleUrls: ['../estilo-abm.component.css']
})
export class JsonEstudiantesComponent implements OnInit {

	perfiles: String[];
	usuarios: Usuario[];
	usuarioBuscado: string;
	mostrarUsuarios;
	perfilActual;
	archivo: File = null;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogosService: DialogosService
	) { }

	ngOnInit() {
		this.perfilSeleccionado('ADMINISTRADOR');
		this.getPerfiles();
	}

	getPerfiles() {
		this.restService.getTipoPerfiles().subscribe(perfiles => {
			this.perfiles = perfiles;
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}


	getUsuariosSegunPerfil() {
		this.restService.getUsuarios(this.perfilActual).subscribe(usuarios => {
			this.usuarios = usuarios;
			this.mostrarUsuarios = this.usuarios.length > 0;
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	perfilSeleccionado(perfil) {
		this.perfilActual = perfil;
		this.getUsuariosSegunPerfil();
	}

	archivoSeleccionado(event){
		this.archivo = <File> event.target.files[0];

	}

	guardarArchivo(){
		let formData: FormData = new FormData();
		formData.append('file', this.archivo);
		this.restService.guardarArchivo(formData).subscribe(perfiles => {
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}
}