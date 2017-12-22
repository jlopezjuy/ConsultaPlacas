import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConsultaPlacasSharedModule } from '../../shared';
import {
    ConsultaPlacaService,
    ConsultaPlacaPopupService,
    ConsultaPlacaComponent,
    ConsultaPlacaDetailComponent,
    ConsultaPlacaDialogComponent,
    ConsultaPlacaPopupComponent,
    ConsultaPlacaDeletePopupComponent,
    ConsultaPlacaDeleteDialogComponent,
    consultaPlacaRoute,
    consultaPlacaPopupRoute,
    ConsultaPlacaResolvePagingParams,
    SearchfilterReportePipe,
    SearchfilterReportePipeFechaInicial,
    SearchfilterReportePipeFechaFinal,
    SearchfilterReportePipeString,
} from './';

const ENTITY_STATES = [
    ...consultaPlacaRoute,
    ...consultaPlacaPopupRoute,
];

@NgModule({
    imports: [
        ConsultaPlacasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConsultaPlacaComponent,
        ConsultaPlacaDetailComponent,
        ConsultaPlacaDialogComponent,
        ConsultaPlacaDeleteDialogComponent,
        ConsultaPlacaPopupComponent,
        ConsultaPlacaDeletePopupComponent,
        SearchfilterReportePipe,
        SearchfilterReportePipeFechaInicial,
        SearchfilterReportePipeFechaFinal,
        SearchfilterReportePipeString,
    ],
    entryComponents: [
        ConsultaPlacaComponent,
        ConsultaPlacaDialogComponent,
        ConsultaPlacaPopupComponent,
        ConsultaPlacaDeleteDialogComponent,
        ConsultaPlacaDeletePopupComponent,
    ],
    providers: [
        ConsultaPlacaService,
        ConsultaPlacaPopupService,
        ConsultaPlacaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConsultaPlacasConsultaPlacaModule {}
