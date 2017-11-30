import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ConsultaPlaca } from './consulta-placa.model';
import { ConsultaPlacaService } from './consulta-placa.service';

@Injectable()
export class ConsultaPlacaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private consultaPlacaService: ConsultaPlacaService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.consultaPlacaService.find(id).subscribe((consultaPlaca) => {
                    if (consultaPlaca.fecha) {
                        consultaPlaca.fecha = {
                            year: consultaPlaca.fecha.getFullYear(),
                            month: consultaPlaca.fecha.getMonth() + 1,
                            day: consultaPlaca.fecha.getDate()
                        };
                    }
                    this.ngbModalRef = this.consultaPlacaModalRef(component, consultaPlaca);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.consultaPlacaModalRef(component, new ConsultaPlaca());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    consultaPlacaModalRef(component: Component, consultaPlaca: ConsultaPlaca): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.consultaPlaca = consultaPlaca;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
