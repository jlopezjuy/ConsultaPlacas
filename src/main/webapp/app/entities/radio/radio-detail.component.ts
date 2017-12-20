import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Radio } from './radio.model';
import { RadioService } from './radio.service';

@Component({
    selector: 'jhi-radio-detail',
    templateUrl: './radio-detail.component.html'
})
export class RadioDetailComponent implements OnInit, OnDestroy {

    radio: Radio;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private radioService: RadioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['issi']);
        });
        this.registerChangeInRadios();
    }

    load(issi) {
        this.radioService.find(issi).subscribe((radio) => {
            this.radio = radio;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRadios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'radioListModification',
            (response) => this.load(this.radio.issi)
        );
    }
}
