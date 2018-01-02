import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConsultaPlacasSharedModule } from '../../shared';
import {
    MarcaService,
    MarcaPopupService,
    MarcaComponent,
    MarcaDetailComponent,
    MarcaDialogComponent,
    MarcaPopupComponent,
    MarcaDeletePopupComponent,
    MarcaDeleteDialogComponent,
    marcaRoute,
    marcaPopupRoute,
    MarcaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...marcaRoute,
    ...marcaPopupRoute,
];

@NgModule({
    imports: [
        ConsultaPlacasSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MarcaComponent,
        MarcaDetailComponent,
        MarcaDialogComponent,
        MarcaDeleteDialogComponent,
        MarcaPopupComponent,
        MarcaDeletePopupComponent,
    ],
    entryComponents: [
        MarcaComponent,
        MarcaDialogComponent,
        MarcaPopupComponent,
        MarcaDeleteDialogComponent,
        MarcaDeletePopupComponent,
    ],
    providers: [
        MarcaService,
        MarcaPopupService,
        MarcaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConsultaPlacasMarcaModule {}
