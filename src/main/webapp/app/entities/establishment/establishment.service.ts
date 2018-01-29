import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Establishment } from './establishment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EstablishmentService {

    private resourceUrl =  SERVER_API_URL + 'api/establishments';

    constructor(private http: Http) { }

    create(establishment: Establishment): Observable<Establishment> {
        const copy = this.convert(establishment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(establishment: Establishment): Observable<Establishment> {
        const copy = this.convert(establishment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Establishment> {
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
     * Convert a returned JSON object to Establishment.
     */
    private convertItemFromServer(json: any): Establishment {
        const entity: Establishment = Object.assign(new Establishment(), json);
        return entity;
    }

    /**
     * Convert a Establishment to a JSON which can be sent to the server.
     */
    private convert(establishment: Establishment): Establishment {
        const copy: Establishment = Object.assign({}, establishment);
        return copy;
    }
}
