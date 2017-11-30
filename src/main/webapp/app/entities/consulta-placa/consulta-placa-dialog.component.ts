import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ConsultaPlaca } from './consulta-placa.model';
import { ConsultaPlacaPopupService } from './consulta-placa-popup.service';
import { ConsultaPlacaService } from './consulta-placa.service';
import { Municipio, MunicipioService } from '../municipio';
import { Corporacion, CorporacionService } from '../corporacion';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-consulta-placa-dialog',
    templateUrl: './consulta-placa-dialog.component.html'
})
export class ConsultaPlacaDialogComponent implements OnInit {

    consultaPlaca: ConsultaPlaca;
    isSaving: boolean;

    municipios: Municipio[];

    corporacions: Corporacion[];
    fechaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private consultaPlacaService: ConsultaPlacaService,
        private municipioService: MunicipioService,
        private corporacionService: CorporacionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.municipioService.query()
            .subscribe((res: ResponseWrapper) => { this.municipios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.corporacionService.query()
            .subscribe((res: ResponseWrapper) => { this.corporacions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.consultaPlaca.id !== undefined) {
            this.subscribeToSaveResponse(
                this.consultaPlacaService.update(this.consultaPlaca));
        } else {
            this.subscribeToSaveResponse(
                this.consultaPlacaService.create(this.consultaPlaca));
        }
    }

    private subscribeToSaveResponse(result: Observable<ConsultaPlaca>) {
        result.subscribe((res: ConsultaPlaca) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ConsultaPlaca) {
        this.eventManager.broadcast({ name: 'consultaPlacaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMunicipioById(index: number, item: Municipio) {
        return item.id;
    }

    trackCorporacionById(index: number, item: Corporacion) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-consulta-placa-popup',
    template: ''
})
export class ConsultaPlacaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultaPlacaPopupService: ConsultaPlacaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.consultaPlacaPopupService
                    .open(ConsultaPlacaDialogComponent as Component, params['id']);
            } else {
                this.consultaPlacaPopupService
                    .open(ConsultaPlacaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
