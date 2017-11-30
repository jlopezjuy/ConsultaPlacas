import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ConsultaPlacasMarcaModule } from './marca/marca.module';
import { ConsultaPlacasMunicipioModule } from './municipio/municipio.module';
import { ConsultaPlacasCorporacionModule } from './corporacion/corporacion.module';
import { ConsultaPlacasRadioModule } from './radio/radio.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ConsultaPlacasMarcaModule,
        ConsultaPlacasMunicipioModule,
        ConsultaPlacasCorporacionModule,
        ConsultaPlacasRadioModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConsultaPlacasEntityModule {}
