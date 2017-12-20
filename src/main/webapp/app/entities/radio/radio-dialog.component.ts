import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Radio } from './radio.model';
import { RadioPopupService } from './radio-popup.service';
import { RadioService } from './radio.service';
import { Marca, MarcaService } from '../marca';
import { Municipio, MunicipioService } from '../municipio';
import { Corporacion, CorporacionService } from '../corporacion';
import { TipoRadio, TipoRadioService } from '../tipo-radio';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-radio-dialog',
    templateUrl: './radio-dialog.component.html'
})
export class RadioDialogComponent implements OnInit {

    radio: Radio;
    isSaving: boolean;

    marcas: Marca[];

    municipios: Municipio[];

    corporacions: Corporacion[];

    tiporadios: TipoRadio[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private radioService: RadioService,
        private marcaService: MarcaService,
        private municipioService: MunicipioService,
        private corporacionService: CorporacionService,
        private tipoRadioService: TipoRadioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.marcaService.query()
            .subscribe((res: ResponseWrapper) => { this.marcas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.municipioService.query()
            .subscribe((res: ResponseWrapper) => { this.municipios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.corporacionService.query()
            .subscribe((res: ResponseWrapper) => { this.corporacions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.tipoRadioService.query()
            .subscribe((res: ResponseWrapper) => { this.tiporadios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.radio.issi !== undefined) {
            this.subscribeToSaveResponse(
                this.radioService.update(this.radio));
        } else {
            this.subscribeToSaveResponse(
                this.radioService.create(this.radio));
        }
    }

    private subscribeToSaveResponse(result: Observable<Radio>) {
        result.subscribe((res: Radio) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Radio) {
        this.eventManager.broadcast({ name: 'radioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMarcaById(index: number, item: Marca) {
        return item.id;
    }

    trackMunicipioById(index: number, item: Municipio) {
        return item.id;
    }

    trackCorporacionById(index: number, item: Corporacion) {
        return item.id;
    }

    trackTipoRadioById(index: number, item: TipoRadio) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-radio-popup',
    template: ''
})
export class RadioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private radioPopupService: RadioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['issi'] ) {
                this.radioPopupService
                    .open(RadioDialogComponent as Component, params['issi']);
            } else {
                this.radioPopupService
                    .open(RadioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
