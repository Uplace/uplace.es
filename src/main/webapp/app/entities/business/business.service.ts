import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Business } from './business.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Business>;

@Injectable()
export class BusinessService {

    private resourceUrl =  SERVER_API_URL + 'api/businesses';

    constructor(private http: HttpClient) { }

    create(business: Business): Observable<EntityResponseType> {
        const copy = this.convert(business);
        return this.http.post<Business>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(business: Business): Observable<EntityResponseType> {
        const copy = this.convert(business);
        return this.http.put<Business>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Business>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Business[]>> {
        const options = createRequestOption(req);
        return this.http.get<Business[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Business[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Business = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Business[]>): HttpResponse<Business[]> {
        const jsonResponse: Business[] = res.body;
        const body: Business[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Business.
     */
    private convertItemFromServer(business: Business): Business {
        const copy: Business = Object.assign({}, business);
        return copy;
    }

    /**
     * Convert a Business to a JSON which can be sent to the server.
     */
    private convert(business: Business): Business {
        const copy: Business = Object.assign({}, business);
        return copy;
    }
}
