import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Corporacion } from './corporacion.model';
import { CorporacionPopupService } from './corporacion-popup.service';
import { CorporacionService } from './corporacion.service';

@Component({
    selector: 'jhi-corporacion-dialog',
    templateUrl: './corporacion-dialog.component.html'
})
export class CorporacionDialogComponent implements OnInit {

    corporacion: Corporacion;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private corporacionService: CorporacionService,
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
        if (this.corporacion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.corporacionService.update(this.corporacion));
        } else {
            this.subscribeToSaveResponse(
                this.corporacionService.create(this.corporacion));
        }
    }

    private subscribeToSaveResponse(result: Observable<Corporacion>) {
        result.subscribe((res: Corporacion) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Corporacion) {
        this.eventManager.broadcast({ name: 'corporacionListModification', content: 'OK'});
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
    selector: 'jhi-corporacion-popup',
    template: ''
})
export class CorporacionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private corporacionPopupService: CorporacionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.corporacionPopupService
                    .open(CorporacionDialogComponent as Component, params['id']);
            } else {
                this.corporacionPopupService
                    .open(CorporacionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
