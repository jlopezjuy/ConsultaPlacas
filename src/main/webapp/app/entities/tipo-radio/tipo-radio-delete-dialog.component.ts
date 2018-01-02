import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoRadio } from './tipo-radio.model';
import { TipoRadioPopupService } from './tipo-radio-popup.service';
import { TipoRadioService } from './tipo-radio.service';

@Component({
    selector: 'jhi-tipo-radio-delete-dialog',
    templateUrl: './tipo-radio-delete-dialog.component.html'
})
export class TipoRadioDeleteDialogComponent {

    tipoRadio: TipoRadio;

    constructor(
        private tipoRadioService: TipoRadioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoRadioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipoRadioListModification',
                content: 'Deleted an tipoRadio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-radio-delete-popup',
    template: ''
})
export class TipoRadioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoRadioPopupService: TipoRadioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tipoRadioPopupService
                .open(TipoRadioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
