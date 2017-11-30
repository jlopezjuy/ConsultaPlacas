import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Radio } from './radio.model';
import { RadioPopupService } from './radio-popup.service';
import { RadioService } from './radio.service';

@Component({
    selector: 'jhi-radio-delete-dialog',
    templateUrl: './radio-delete-dialog.component.html'
})
export class RadioDeleteDialogComponent {

    radio: Radio;

    constructor(
        private radioService: RadioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.radioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'radioListModification',
                content: 'Deleted an radio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-radio-delete-popup',
    template: ''
})
export class RadioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private radioPopupService: RadioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.radioPopupService
                .open(RadioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
