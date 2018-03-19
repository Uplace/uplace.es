import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Apartment } from './apartment.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Apartment>;

@Injectable()
export class ApartmentService {

    private resourceUrl =  SERVER_API_URL + 'api/apartments';

    constructor(private http: HttpClient) { }

    create(apartment: Apartment): Observable<EntityResponseType> {
        const copy = this.convert(apartment);
        return this.http.post<Apartment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(apartment: Apartment): Observable<EntityResponseType> {
        const copy = this.convert(apartment);
        return this.http.put<Apartment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Apartment>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Apartment[]>> {
        const options = createRequestOption(req);
        return this.http.get<Apartment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Apartment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Apartment = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Apartment[]>): HttpResponse<Apartment[]> {
        const jsonResponse: Apartment[] = res.body;
        const body: Apartment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Apartment.
     */
    private convertItemFromServer(apartment: Apartment): Apartment {
        const copy: Apartment = Object.assign({}, apartment);
        return copy;
    }

    /**
     * Convert a Apartment to a JSON which can be sent to the server.
     */
    private convert(apartment: Apartment): Apartment {
        const copy: Apartment = Object.assign({}, apartment);
        return copy;
    }
}
