import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TipoRadio } from './tipo-radio.model';
import { TipoRadioService } from './tipo-radio.service';

@Component({
    selector: 'jhi-tipo-radio-detail',
    templateUrl: './tipo-radio-detail.component.html'
})
export class TipoRadioDetailComponent implements OnInit, OnDestroy {

    tipoRadio: TipoRadio;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tipoRadioService: TipoRadioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTipoRadios();
    }

    load(id) {
        this.tipoRadioService.find(id).subscribe((tipoRadio) => {
            this.tipoRadio = tipoRadio;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTipoRadios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipoRadioListModification',
            (response) => this.load(this.tipoRadio.id)
        );
    }
}
