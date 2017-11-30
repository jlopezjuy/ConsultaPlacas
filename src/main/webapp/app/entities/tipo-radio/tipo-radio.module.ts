import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConsultaPlacasSharedModule } from '../../shared';
import {
    TipoRadioService,
    TipoRadioPopupService,
    TipoRadioComponent,
    TipoRadioDetailComponent,
    TipoRadioDialogComponent,
    TipoRadioPopupComponent,
    TipoRadioDeletePopupComponent,
    TipoRadioDeleteDialogComponent,
    tipoRadioRoute,
    tipoRadioPopupRoute,
    TipoRadioResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tipoRadioRoute,
    ...tipoRadioPopupRoute,
];

@NgModule({
    imports: [
        ConsultaPlacasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TipoRadioComponent,
        TipoRadioDetailComponent,
        TipoRadioDialogComponent,
        TipoRadioDeleteDialogComponent,
        TipoRadioPopupComponent,
        TipoRadioDeletePopupComponent,
    ],
    entryComponents: [
        TipoRadioComponent,
        TipoRadioDialogComponent,
        TipoRadioPopupComponent,
        TipoRadioDeleteDialogComponent,
        TipoRadioDeletePopupComponent,
    ],
    providers: [
        TipoRadioService,
        TipoRadioPopupService,
        TipoRadioResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConsultaPlacasTipoRadioModule {}
