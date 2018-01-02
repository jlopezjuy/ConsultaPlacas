import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { TipoRadio } from './tipo-radio.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TipoRadioService {

    private resourceUrl = SERVER_API_URL + 'api/tipo-radios';

    constructor(private http: Http) { }

    create(tipoRadio: TipoRadio): Observable<TipoRadio> {
        const copy = this.convert(tipoRadio);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(tipoRadio: TipoRadio): Observable<TipoRadio> {
        const copy = this.convert(tipoRadio);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TipoRadio> {
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
     * Convert a returned JSON object to TipoRadio.
     */
    private convertItemFromServer(json: any): TipoRadio {
        const entity: TipoRadio = Object.assign(new TipoRadio(), json);
        return entity;
    }

    /**
     * Convert a TipoRadio to a JSON which can be sent to the server.
     */
    private convert(tipoRadio: TipoRadio): TipoRadio {
        const copy: TipoRadio = Object.assign({}, tipoRadio);
        return copy;
    }
}
