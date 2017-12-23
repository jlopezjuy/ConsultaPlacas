import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';

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
    SearchfilterReportePipeBoolean,
    SearchfilterReportePipeFechaInicial,
    SearchfilterReportePipeFechaFinal,
    SearchfilterReportePipeString,
    SearchfilterReportePipeNumber,
} from './';

const ENTITY_STATES = [
    ...consultaPlacaRoute,
    ...consultaPlacaPopupRoute,
];

@NgModule({
    imports: [
        ConsultaPlacasSharedModule,
        NgxPaginationModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConsultaPlacaComponent,
        ConsultaPlacaDetailComponent,
        ConsultaPlacaDialogComponent,
        ConsultaPlacaDeleteDialogComponent,
        ConsultaPlacaPopupComponent,
        ConsultaPlacaDeletePopupComponent,
        SearchfilterReportePipeBoolean,
        SearchfilterReportePipeFechaInicial,
        SearchfilterReportePipeFechaFinal,
        SearchfilterReportePipeString,
        SearchfilterReportePipeNumber,
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
export class ConsultaPlacasConsultaPlacaModule { }
