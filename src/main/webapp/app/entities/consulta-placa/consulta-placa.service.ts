import { Injectable } from '@angular/core';
import {Http, RequestOptions, Response, ResponseContentType, Headers} from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ConsultaPlaca } from './consulta-placa.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import * as FileSaver from "file-saver";

@Injectable()
export class ConsultaPlacaService {

    private resourceUrl = SERVER_API_URL + 'api/consulta-placas';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(consultaPlaca: ConsultaPlaca): Observable<ConsultaPlaca> {
        const copy = this.convert(consultaPlaca);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(consultaPlaca: ConsultaPlaca): Observable<ConsultaPlaca> {
        const copy = this.convert(consultaPlaca);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ConsultaPlaca> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    generateReport(): any {
        return this.http.get(this.resourceUrl + '/reporte', { responseType: ResponseContentType.Blob }).map(
            (res) => {
                return new Blob([res.blob()], { type: 'application/pdf' })
            }
        );
    }

    generateReportPdf(issi?: any, municipio?: string, corporacion?: any, estado?: any, desde?: any, hasta?: any) {
        return this.http.get(this.resourceUrl + '/reporte/PDF'+"/"+issi+"/"+municipio+"/"+corporacion+"/"+estado+"/"+desde+"/"+hasta, { responseType: ResponseContentType.Blob }).map(
            (res) => {
                return new Blob([res.blob()], { type: 'application/pdf' })
            }
        );
    }

    generateReportXls(issi?: any, municipio?: string, corporacion?: any, estado?: any, desde?: any, hasta?: any) {
        return this.http.get(this.resourceUrl + '/reporte/XLS'+"/"+issi+"/"+municipio+"/"+corporacion+"/"+estado+"/"+desde+"/"+hasta, { responseType: ResponseContentType.Blob }).map(
            (res) => {
                return new Blob([res.blob()], { type: 'application/vnd.ms-excel' })
            }
        );
    }

    generateReportXlsx(issi?: any, municipio?: string, corporacion?: any, estado?: any, desde?: any, hasta?: any) {
        return this.http.get(this.resourceUrl + '/reporte/XLSX'+"/"+issi+"/"+municipio+"/"+corporacion+"/"+estado+"/"+desde+"/"+hasta, { responseType: ResponseContentType.Blob }).map(
            (res) => {
                return new Blob([res.blob()], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.‌​sheet' })
            }
        );
    }

    generateReportCsv(issi?: any, municipio?: string, corporacion?: string, estado?: boolean, desde?: any, hasta?: any) {
        return this.http.get(this.resourceUrl + '/reporte/CSV'+"/"+issi+"/"+municipio+"/"+corporacion+"/"+estado+"/"+desde+"/"+hasta, { responseType: ResponseContentType.Blob }).map(
            (res) => {
                return new Blob([res.blob()], { type: 'text/csv' })
            }
        );
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to ConsultaPlaca.
     */
    private convertItemFromServer(json: any): ConsultaPlaca {
        const entity: ConsultaPlaca = Object.assign(new ConsultaPlaca(), json);
        entity.fecha = this.dateUtils
            .convertLocalDateFromServer(json.fecha);
        return entity;
    }

    /**
     * Convert a ConsultaPlaca to a JSON which can be sent to the server.
     */
    private convert(consultaPlaca: ConsultaPlaca): ConsultaPlaca {
        const copy: ConsultaPlaca = Object.assign({}, consultaPlaca);
        copy.fecha = this.dateUtils
            .convertLocalDateToServer(consultaPlaca.fecha);
        return copy;
    }
}
