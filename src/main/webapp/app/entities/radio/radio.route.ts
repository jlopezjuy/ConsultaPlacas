import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RadioComponent } from './radio.component';
import { RadioDetailComponent } from './radio-detail.component';
import { RadioPopupComponent } from './radio-dialog.component';
import { RadioDeletePopupComponent } from './radio-delete-dialog.component';

@Injectable()
export class RadioResolvePagingParams implements Resolve<any> {

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

export const radioRoute: Routes = [
    {
        path: 'radio',
        component: RadioComponent,
        resolve: {
            'pagingParams': RadioResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'radio/:id',
        component: RadioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const radioPopupRoute: Routes = [
    {
        path: 'radio-new',
        component: RadioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'radio/:id/edit',
        component: RadioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'radio/:id/delete',
        component: RadioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'consultaPlacasApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
