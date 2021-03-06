import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Radio } from './radio.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RadioService {

    private resourceUrl = SERVER_API_URL + 'api/radios';

    constructor(private http: Http) { }

    create(radio: Radio): Observable<Radio> {
        const copy = this.convert(radio);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(radio: Radio): Observable<Radio> {
        const copy = this.convert(radio);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(issi: number): Observable<Radio> {
        return this.http.get(`${this.resourceUrl}/${issi}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(issi: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${issi}`);
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
     * Convert a returned JSON object to Radio.
     */
    private convertItemFromServer(json: any): Radio {
        const entity: Radio = Object.assign(new Radio(), json);
        return entity;
    }

    /**
     * Convert a Radio to a JSON which can be sent to the server.
     */
    private convert(radio: Radio): Radio {
        const copy: Radio = Object.assign({}, radio);
        return copy;
    }
}
