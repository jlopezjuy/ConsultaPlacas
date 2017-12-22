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
    SearchfilterReportePipe,
    SearchfilterReportePipeFechaInicial,
    SearchfilterReportePipeFechaFinal,
} from './';

const ENTITY_STATES = [
    ...consultaPlacaRoute,
    ...consultaPlacaPopupRoute,
];

@NgModule({
    imports: [
        ConsultaPlacasSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        NgxPaginationModule
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
