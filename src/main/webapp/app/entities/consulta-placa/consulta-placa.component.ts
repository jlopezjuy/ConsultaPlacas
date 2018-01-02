import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager, JhiParseLinks, JhiAlertService, JhiDateUtils} from 'ng-jhipster';

import {ConsultaPlaca} from './consulta-placa.model';
import {ConsultaPlacaService} from './consulta-placa.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import * as FileSaver from "file-saver";

@Component({
    selector: 'jhi-consulta-placa',
    templateUrl: './consulta-placa.component.html'
})
export class ConsultaPlacaComponent implements OnInit, OnDestroy {

    currentAccount: any;
    consultaPlacas: ConsultaPlaca[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    busquedaEstado: any;
    busquedaFechaInicial: any;
    busquedaFechaFinal: any;
    busquedaIssi: any;
    busquedaMunicipio: any;
    busquedaCorporacion: any;
    p = 1;
    fechaDpInicial: any;
    fechaDpFinal: any;

    constructor(private consultaPlacaService: ConsultaPlacaService,
                private parseLinks: JhiParseLinks,
                private jhiAlertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private dateUtils: JhiDateUtils) {
        this.itemsPerPage = 10;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
        this.consultaPlacaService.queryFilter({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
            issi: this.busquedaIssi === '' ? undefined : this.busquedaIssi,
            municipio: this.busquedaMunicipio === '' ? undefined : this.busquedaMunicipio,
            corporacion: this.busquedaCorporacion === '' ? undefined : this.busquedaCorporacion,
            estado: this.busquedaEstado === '' ? undefined : this.busquedaEstado,
            desde: this.dateUtils.convertLocalDateToServer(this.busquedaFechaInicial),
            hasta: this.dateUtils.convertLocalDateToServer(this.busquedaFechaFinal)

        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    findConsultas() {
        this.page = 0;
        this.router.navigate(['/consulta-placa', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);

        this.consultaPlacaService.queryFilter({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
            issi: this.busquedaIssi === '' ? undefined : this.busquedaIssi,
            municipio: this.busquedaMunicipio === '' ? undefined : this.busquedaMunicipio,
            corporacion: this.busquedaCorporacion === '' ? undefined : this.busquedaCorporacion,
            estado: this.busquedaEstado === '' ? undefined : this.busquedaEstado,
            desde: this.dateUtils.convertLocalDateToServer(this.busquedaFechaInicial),
            hasta: this.dateUtils.convertLocalDateToServer(this.busquedaFechaFinal)

        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/consulta-placa'], {
            queryParams:
                {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/consulta-placa', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }

    ngOnInit() {
        //this.loadAll();
        var now = new Date();
        var now2 = new Date();
        console.log(now);
        now2.setDate(now2.getDate() - 7);
        console.log(now2);
        this.busquedaFechaFinal = {year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate()};
        this.busquedaFechaInicial = {year: now2.getFullYear(), month: now2.getMonth() + 1, day: now2.getDate()};

        console.log(this.busquedaFechaInicial);
        console.log(this.busquedaFechaFinal);
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInConsultaPlacas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ConsultaPlaca) {
        return item.id;
    }

    registerChangeInConsultaPlacas() {
        this.eventSubscriber = this.eventManager.subscribe('consultaPlacaListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        console.log(this.queryCount);
        console.log(this.consultaPlacas);
        // this.page = pagingParams.page;
        this.consultaPlacas = data;
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    printReportPdf() {
        console.log('Entro a imprimir el reporte');
        console.log(this.busquedaFechaInicial);
        console.log(this.busquedaFechaFinal);

        this.consultaPlacaService.generateReportPdf(this.busquedaIssi, this.busquedaMunicipio, this.busquedaCorporacion, this.busquedaEstado, this.dateUtils.convertLocalDateToServer(this.busquedaFechaInicial), this.dateUtils.convertLocalDateToServer(this.busquedaFechaFinal)).subscribe(
            (res) => {
                FileSaver.saveAs(res, "reporte.pdf");
            }
        );
    }

    printReportXls() {
        console.log('Entro a imprimir el reporte');
        this.consultaPlacaService.generateReportXls(this.busquedaIssi, this.busquedaMunicipio, this.busquedaCorporacion, this.busquedaEstado, this.dateUtils.convertLocalDateToServer(this.busquedaFechaInicial), this.dateUtils.convertLocalDateToServer(this.busquedaFechaFinal)).subscribe(
            (res) => {
                FileSaver.saveAs(res, "reporte.xls");
            }
        );
    }

    printReportXlsx() {
        console.log('Entro a imprimir el reporte');
        this.consultaPlacaService.generateReportXlsx(this.busquedaIssi, this.busquedaMunicipio, this.busquedaCorporacion, this.busquedaEstado, this.dateUtils.convertLocalDateToServer(this.busquedaFechaInicial), this.dateUtils.convertLocalDateToServer(this.busquedaFechaFinal)).subscribe(
            (res) => {
                FileSaver.saveAs(res, "reporte.xlsx");
            }
        );
    }

    printReportCsv() {
        console.log('Entro a imprimir el reporte');
        this.consultaPlacaService.generateReportCsv(this.busquedaIssi, this.busquedaMunicipio, this.busquedaCorporacion, this.busquedaEstado, this.dateUtils.convertLocalDateToServer(this.busquedaFechaInicial), this.dateUtils.convertLocalDateToServer(this.busquedaFechaFinal)).subscribe(
            (res) => {
                FileSaver.saveAs(res, "reporte.csv");
            }
        );
    }
}
