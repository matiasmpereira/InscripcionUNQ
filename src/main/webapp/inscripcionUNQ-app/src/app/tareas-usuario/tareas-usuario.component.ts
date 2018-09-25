import { Component} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';


@Component({
  selector: 'app-tareas-usuario',
  templateUrl: './tareas-usuario.component.html',
  styleUrls: ['./tareas-usuario.component.css']
})
export class TareasUsuarioComponent {
    constructor(
      private restService: RestService,
      private utilesService: UtilesService
    ) {}

    irACarreras() {

      this.restService.getCarreras().subscribe(carreras => {
        localStorage.setItem('carreras',JSON.stringify(carreras));
        this.utilesService.irA('carreras');
      },
      (err) => {
          this.utilesService.mostrarMensajeDeError(err);
      });
    }

}
