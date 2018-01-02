import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TipoRadio } from './tipo-radio.model';
import { TipoRadioPopupService } from './tipo-radio-popup.service';
import { TipoRadioService } from './tipo-radio.service';

@Component({
    selector: 'jhi-tipo-radio-dialog',
    templateUrl: './tipo-radio-dialog.component.html'
})
export class TipoRadioDialogComponent implements OnInit {

    tipoRadio: TipoRadio;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tipoRadioService: TipoRadioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tipoRadio.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipoRadioService.update(this.tipoRadio));
        } else {
            this.subscribeToSaveResponse(
                this.tipoRadioService.create(this.tipoRadio));
        }
    }

    private subscribeToSaveResponse(result: Observable<TipoRadio>) {
        result.subscribe((res: TipoRadio) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TipoRadio) {
        this.eventManager.broadcast({ name: 'tipoRadioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-tipo-radio-popup',
    template: ''
})
export class TipoRadioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoRadioPopupService: TipoRadioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tipoRadioPopupService
                    .open(TipoRadioDialogComponent as Component, params['id']);
            } else {
                this.tipoRadioPopupService
                    .open(TipoRadioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
