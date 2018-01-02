import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CorporacionComponent } from './corporacion.component';
import { CorporacionDetailComponent } from './corporacion-detail.component';
import { CorporacionPopupComponent } from './corporacion-dialog.component';
import { CorporacionDeletePopupComponent } from './corporacion-delete-dialog.component';

@Injectable()
export class CorporacionResolvePagingParams implements Resolve<any> {

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

export const corporacionRoute: Routes = [
    {
        path: 'corporacion',
        component: CorporacionComponent,
        resolve: {
            'pagingParams': CorporacionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.corporacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'corporacion/:id',
        component: CorporacionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.corporacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const corporacionPopupRoute: Routes = [
    {
        path: 'corporacion-new',
        component: CorporacionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.corporacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'corporacion/:id/edit',
        component: CorporacionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.corporacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'corporacion/:id/delete',
        component: CorporacionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.corporacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
