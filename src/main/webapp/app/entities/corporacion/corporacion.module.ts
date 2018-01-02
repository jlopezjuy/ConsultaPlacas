import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConsultaPlacasSharedModule } from '../../shared';
import {
    CorporacionService,
    CorporacionPopupService,
    CorporacionComponent,
    CorporacionDetailComponent,
    CorporacionDialogComponent,
    CorporacionPopupComponent,
    CorporacionDeletePopupComponent,
    CorporacionDeleteDialogComponent,
    corporacionRoute,
    corporacionPopupRoute,
    CorporacionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...corporacionRoute,
    ...corporacionPopupRoute,
];

@NgModule({
    imports: [
        ConsultaPlacasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CorporacionComponent,
        CorporacionDetailComponent,
        CorporacionDialogComponent,
        CorporacionDeleteDialogComponent,
        CorporacionPopupComponent,
        CorporacionDeletePopupComponent,
    ],
    entryComponents: [
        CorporacionComponent,
        CorporacionDialogComponent,
        CorporacionPopupComponent,
        CorporacionDeleteDialogComponent,
        CorporacionDeletePopupComponent,
    ],
    providers: [
        CorporacionService,
        CorporacionPopupService,
        CorporacionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConsultaPlacasCorporacionModule {}
