import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Establishment } from './establishment.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Establishment>;

@Injectable()
export class EstablishmentService {

    private resourceUrl =  SERVER_API_URL + 'api/establishments';

    constructor(private http: HttpClient) { }

    create(establishment: Establishment): Observable<EntityResponseType> {
        const copy = this.convert(establishment);
        return this.http.post<Establishment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(establishment: Establishment): Observable<EntityResponseType> {
        const copy = this.convert(establishment);
        return this.http.put<Establishment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Establishment>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Establishment[]>> {
        const options = createRequestOption(req);
        return this.http.get<Establishment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Establishment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Establishment = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Establishment[]>): HttpResponse<Establishment[]> {
        const jsonResponse: Establishment[] = res.body;
        const body: Establishment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Establishment.
     */
    private convertItemFromServer(establishment: Establishment): Establishment {
        const copy: Establishment = Object.assign({}, establishment);
        return copy;
    }

    /**
     * Convert a Establishment to a JSON which can be sent to the server.
     */
    private convert(establishment: Establishment): Establishment {
        const copy: Establishment = Object.assign({}, establishment);
        return copy;
    }
}
