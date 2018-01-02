import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Corporacion } from './corporacion.model';
import { CorporacionService } from './corporacion.service';

@Component({
    selector: 'jhi-corporacion-detail',
    templateUrl: './corporacion-detail.component.html'
})
export class CorporacionDetailComponent implements OnInit, OnDestroy {

    corporacion: Corporacion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private corporacionService: CorporacionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCorporacions();
    }

    load(id) {
        this.corporacionService.find(id).subscribe((corporacion) => {
            this.corporacion = corporacion;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCorporacions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'corporacionListModification',
            (response) => this.load(this.corporacion.id)
        );
    }
}
