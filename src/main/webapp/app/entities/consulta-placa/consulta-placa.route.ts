import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConsultaPlacaComponent } from './consulta-placa.component';
import { ConsultaPlacaDetailComponent } from './consulta-placa-detail.component';
import { ConsultaPlacaPopupComponent } from './consulta-placa-dialog.component';
import { ConsultaPlacaDeletePopupComponent } from './consulta-placa-delete-dialog.component';

@Injectable()
export class ConsultaPlacaResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const consultaPlacaRoute: Routes = [
    {
        path: 'consulta-placa',
        component: ConsultaPlacaComponent,
        resolve: {
            'pagingParams': ConsultaPlacaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.consultaPlaca.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'consulta-placa/:id',
        component: ConsultaPlacaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.consultaPlaca.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const consultaPlacaPopupRoute: Routes = [
    {
        path: 'consulta-placa-new',
        component: ConsultaPlacaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.consultaPlaca.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consulta-placa/:id/edit',
        component: ConsultaPlacaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.consultaPlaca.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consulta-placa/:id/delete',
        component: ConsultaPlacaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.consultaPlaca.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
