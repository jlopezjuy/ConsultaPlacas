import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Corporacion } from './corporacion.model';
import { CorporacionPopupService } from './corporacion-popup.service';
import { CorporacionService } from './corporacion.service';

@Component({
    selector: 'jhi-corporacion-delete-dialog',
    templateUrl: './corporacion-delete-dialog.component.html'
})
export class CorporacionDeleteDialogComponent {

    corporacion: Corporacion;

    constructor(
        private corporacionService: CorporacionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.corporacionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'corporacionListModification',
                content: 'Deleted an corporacion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-corporacion-delete-popup',
    template: ''
})
export class CorporacionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private corporacionPopupService: CorporacionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.corporacionPopupService
                .open(CorporacionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
