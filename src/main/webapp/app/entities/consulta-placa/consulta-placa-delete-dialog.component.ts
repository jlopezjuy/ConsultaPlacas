import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ConsultaPlaca } from './consulta-placa.model';
import { ConsultaPlacaPopupService } from './consulta-placa-popup.service';
import { ConsultaPlacaService } from './consulta-placa.service';

@Component({
    selector: 'jhi-consulta-placa-delete-dialog',
    templateUrl: './consulta-placa-delete-dialog.component.html'
})
export class ConsultaPlacaDeleteDialogComponent {

    consultaPlaca: ConsultaPlaca;

    constructor(
        private consultaPlacaService: ConsultaPlacaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.consultaPlacaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'consultaPlacaListModification',
                content: 'Deleted an consultaPlaca'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-consulta-placa-delete-popup',
    template: ''
})
export class ConsultaPlacaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultaPlacaPopupService: ConsultaPlacaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.consultaPlacaPopupService
                .open(ConsultaPlacaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
