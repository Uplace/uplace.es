import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bussiness } from './bussiness.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BussinessService {

    private resourceUrl =  SERVER_API_URL + 'api/bussinesses';

    constructor(private http: Http) { }

    create(bussiness: Bussiness): Observable<Bussiness> {
        const copy = this.convert(bussiness);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(bussiness: Bussiness): Observable<Bussiness> {
        const copy = this.convert(bussiness);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Bussiness> {
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
     * Convert a returned JSON object to Bussiness.
     */
    private convertItemFromServer(json: any): Bussiness {
        const entity: Bussiness = Object.assign(new Bussiness(), json);
        return entity;
    }

    /**
     * Convert a Bussiness to a JSON which can be sent to the server.
     */
    private convert(bussiness: Bussiness): Bussiness {
        const copy: Bussiness = Object.assign({}, bussiness);
        return copy;
    }
}
