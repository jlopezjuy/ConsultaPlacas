import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConsultaPlacasSharedModule } from '../../shared';
import {
    RadioService,
    RadioPopupService,
    RadioComponent,
    RadioDetailComponent,
    RadioDialogComponent,
    RadioPopupComponent,
    RadioDeletePopupComponent,
    RadioDeleteDialogComponent,
    radioRoute,
    radioPopupRoute,
    RadioResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...radioRoute,
    ...radioPopupRoute,
];

@NgModule({
    imports: [
        ConsultaPlacasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RadioComponent,
        RadioDetailComponent,
        RadioDialogComponent,
        RadioDeleteDialogComponent,
        RadioPopupComponent,
        RadioDeletePopupComponent,
    ],
    entryComponents: [
        RadioComponent,
        RadioDialogComponent,
        RadioPopupComponent,
        RadioDeleteDialogComponent,
        RadioDeletePopupComponent,
    ],
    providers: [
        RadioService,
        RadioPopupService,
        RadioResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConsultaPlacasRadioModule {}
