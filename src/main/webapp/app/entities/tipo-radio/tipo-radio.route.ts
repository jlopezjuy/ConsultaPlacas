import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TipoRadioComponent } from './tipo-radio.component';
import { TipoRadioDetailComponent } from './tipo-radio-detail.component';
import { TipoRadioPopupComponent } from './tipo-radio-dialog.component';
import { TipoRadioDeletePopupComponent } from './tipo-radio-delete-dialog.component';

@Injectable()
export class TipoRadioResolvePagingParams implements Resolve<any> {

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

export const tipoRadioRoute: Routes = [
    {
        path: 'tipo-radio',
        component: TipoRadioComponent,
        resolve: {
            'pagingParams': TipoRadioResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.tipoRadio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tipo-radio/:id',
        component: TipoRadioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.tipoRadio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoRadioPopupRoute: Routes = [
    {
        path: 'tipo-radio-new',
        component: TipoRadioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.tipoRadio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-radio/:id/edit',
        component: TipoRadioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.tipoRadio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-radio/:id/delete',
        component: TipoRadioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.tipoRadio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
