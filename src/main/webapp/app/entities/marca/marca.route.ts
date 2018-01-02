import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MarcaComponent } from './marca.component';
import { MarcaDetailComponent } from './marca-detail.component';
import { MarcaPopupComponent } from './marca-dialog.component';
import { MarcaDeletePopupComponent } from './marca-delete-dialog.component';

@Injectable()
export class MarcaResolvePagingParams implements Resolve<any> {

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

export const marcaRoute: Routes = [
    {
        path: 'marca',
        component: MarcaComponent,
        resolve: {
            'pagingParams': MarcaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.marca.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'marca/:id',
        component: MarcaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.marca.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marcaPopupRoute: Routes = [
    {
        path: 'marca-new',
        component: MarcaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.marca.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'marca/:id/edit',
        component: MarcaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.marca.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'marca/:id/delete',
        component: MarcaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.marca.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
