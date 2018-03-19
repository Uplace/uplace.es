import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Property } from './property.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Property>;

@Injectable()
export class PropertyService {

    private resourceUrl =  SERVER_API_URL + 'api/properties';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(property: Property): Observable<EntityResponseType> {
        const copy = this.convert(property);
        return this.http.post<Property>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(property: Property): Observable<EntityResponseType> {
        const copy = this.convert(property);
        return this.http.put<Property>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Property>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Property[]>> {
        const options = createRequestOption(req);
        return this.http.get<Property[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Property[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Property = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Property[]>): HttpResponse<Property[]> {
        const jsonResponse: Property[] = res.body;
        const body: Property[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Property.
     */
    private convertItemFromServer(property: Property): Property {
        const copy: Property = Object.assign({}, property);
        copy.created = this.dateUtils
            .convertDateTimeFromServer(property.created);
        copy.updated = this.dateUtils
            .convertDateTimeFromServer(property.updated);
        return copy;
    }

    /**
     * Convert a Property to a JSON which can be sent to the server.
     */
    private convert(property: Property): Property {
        const copy: Property = Object.assign({}, property);

        copy.created = this.dateUtils.toDate(property.created);

        copy.updated = this.dateUtils.toDate(property.updated);
        return copy;
    }
}
