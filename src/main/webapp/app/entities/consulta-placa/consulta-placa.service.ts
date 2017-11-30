import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ConsultaPlaca } from './consulta-placa.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

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
