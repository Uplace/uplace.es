import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Property } from './property.model';
import { createRequestOption } from '../../shared';
import {Mail} from "../../shared/model/mail.model";
import {UserSearch} from "../../shared/search/search.model";
import {Apartment} from "../../shared/model/apartment.model";
import {Building} from "../../shared/model/building.model";
import {Establishment} from "../../shared/model/establishment.model";

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

    inquire(reference: string, mail: Mail): Observable<EntityResponseType> {
        return this.http.post<Property>(`${this.resourceUrl}/${reference}/inquire`, mail, { observe: 'response' })
            .map((res: EntityResponseType) => res);
    }

    update(property: Property): Observable<EntityResponseType> {
        const copy = this.convert(property);
        return this.http.put<Property>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(reference: string): Observable<EntityResponseType> {
        return this.http.get<any>(`${this.resourceUrl}/${reference}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any, search?: UserSearch): Observable<HttpResponse<Property[]>> {
        const options = createRequestOption(req);
        if (search && Object.keys(search).length === 0) {
            search.category = 'properties'
        }
        return this.http.get<Property[]>(SERVER_API_URL + 'api/search/' + this.transformSearch(search), { params: options, observe: 'response' })
            .map((res: HttpResponse<Property[]>) => this.convertArrayResponse(res));
    }

    delete(properties: Property[]): Observable<HttpResponse<any>> {
        const references = this.convertPropertyToReferences(properties);
        return this.http.delete<any>(`${this.resourceUrl}/${references}`, { observe: 'response' });
    }

    private transformSearch(userSearch: UserSearch): string {
        switch (userSearch.category) {
            case 'Apartment':
                return 'apartments';
            case 'Building':
                return 'buildings';
            case 'Establishment':
                return 'establishments';
            default:
                return 'properties';
        }
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Property = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertPropertyToReferences(properties: Property[]): string {
        let references = [];
        properties.forEach((property) => {
            references.push(property.reference);
        });

        return references.join(',');
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

        copy.created = this.dateUtils.convertDateTimeFromServer(property.created);

        copy.updated = this.dateUtils.convertDateTimeFromServer(property.updated);

        return copy;
    }
}
