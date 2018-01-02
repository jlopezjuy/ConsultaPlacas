import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { ConsultaPlacasSharedModule, UserRouteAccessService } from './shared';
import { ConsultaPlacasAppRoutingModule} from './app-routing.module';
import { ConsultaPlacasHomeModule } from './home/home.module';
import { ConsultaPlacasAdminModule } from './admin/admin.module';
import { ConsultaPlacasAccountModule } from './account/account.module';
import { ConsultaPlacasEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { NgxPaginationModule } from 'ngx-pagination';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        ConsultaPlacasAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        ConsultaPlacasSharedModule,
        ConsultaPlacasHomeModule,
        ConsultaPlacasAdminModule,
        ConsultaPlacasAccountModule,
        ConsultaPlacasEntityModule,
        NgxPaginationModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class ConsultaPlacasAppModule {}
