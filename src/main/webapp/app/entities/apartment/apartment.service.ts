import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Apartment } from './apartment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ApartmentService {

    private resourceUrl =  SERVER_API_URL + 'api/apartments';

    constructor(private http: Http) { }

    create(apartment: Apartment): Observable<Apartment> {
        const copy = this.convert(apartment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(apartment: Apartment): Observable<Apartment> {
        const copy = this.convert(apartment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Apartment> {
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
     * Convert a returned JSON object to Apartment.
     */
    private convertItemFromServer(json: any): Apartment {
        const entity: Apartment = Object.assign(new Apartment(), json);
        return entity;
    }

    /**
     * Convert a Apartment to a JSON which can be sent to the server.
     */
    private convert(apartment: Apartment): Apartment {
        const copy: Apartment = Object.assign({}, apartment);
        return copy;
    }
}
