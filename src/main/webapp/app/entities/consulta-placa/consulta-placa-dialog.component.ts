import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ConsultaPlaca } from './consulta-placa.model';
import { ConsultaPlacaPopupService } from './consulta-placa-popup.service';
import { ConsultaPlacaService } from './consulta-placa.service';
import { Radio, RadioService } from '../radio';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-consulta-placa-dialog',
    templateUrl: './consulta-placa-dialog.component.html'
})
export class ConsultaPlacaDialogComponent implements OnInit {

    consultaPlaca: ConsultaPlaca;
    isSaving: boolean;

    radios: Radio[];
    fechaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private consultaPlacaService: ConsultaPlacaService,
        private radioService: RadioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.radioService.query()
            .subscribe((res: ResponseWrapper) => { this.radios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    trackRadioById(index: number, item: Radio) {
        return item.issi;
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
