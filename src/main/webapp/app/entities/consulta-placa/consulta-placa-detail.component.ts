import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ConsultaPlaca } from './consulta-placa.model';
import { ConsultaPlacaService } from './consulta-placa.service';

@Component({
    selector: 'jhi-consulta-placa-detail',
    templateUrl: './consulta-placa-detail.component.html'
})
export class ConsultaPlacaDetailComponent implements OnInit, OnDestroy {

    consultaPlaca: ConsultaPlaca;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private consultaPlacaService: ConsultaPlacaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConsultaPlacas();
    }

    load(id) {
        this.consultaPlacaService.find(id).subscribe((consultaPlaca) => {
            this.consultaPlaca = consultaPlaca;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConsultaPlacas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'consultaPlacaListModification',
            (response) => this.load(this.consultaPlaca.id)
        );
    }
}
