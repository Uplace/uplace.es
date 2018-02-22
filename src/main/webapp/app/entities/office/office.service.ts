import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Office } from './office.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Office>;

@Injectable()
export class OfficeService {

    private resourceUrl =  SERVER_API_URL + 'api/offices';

    constructor(private http: HttpClient) { }

    create(office: Office): Observable<EntityResponseType> {
        const copy = this.convert(office);
        return this.http.post<Office>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(office: Office): Observable<EntityResponseType> {
        const copy = this.convert(office);
        return this.http.put<Office>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Office>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Office[]>> {
        const options = createRequestOption(req);
        return this.http.get<Office[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Office[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Office = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Office[]>): HttpResponse<Office[]> {
        const jsonResponse: Office[] = res.body;
        const body: Office[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Office.
     */
    private convertItemFromServer(office: Office): Office {
        const copy: Office = Object.assign({}, office);
        return copy;
    }

    /**
     * Convert a Office to a JSON which can be sent to the server.
     */
    private convert(office: Office): Office {
        const copy: Office = Object.assign({}, office);
        return copy;
    }
}
