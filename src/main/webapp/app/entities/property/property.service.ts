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
import {Business} from "../../shared/model/business.model";
import {Hotel} from "../../shared/model/hotel.model";
import {IndustrialPlant} from "../../shared/model/industrial-plant.model";
import {Office} from "../../shared/model/office.model";
import {Parking} from "../../shared/model/parking.model";
import {Terrain} from "../../shared/model/terrain.model";

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

        return this.http.get<Property[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Property[]>) => this.convertArrayResponse(res));
    }

    delete(properties: Property[]): Observable<HttpResponse<any>> {
        const references = this.convertPropertyToReferences(properties);
        return this.http.delete<any>(`${this.resourceUrl}/${references}`, { observe: 'response' });
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
        let copy: Property = Object.assign({}, property);
        switch (property.propertyType) {
            case 'Apartment':
                copy = Object.assign(new Apartment(), property);
                break;
            case 'Building':
                copy = Object.assign(new Building(), property);
                break;
            case 'Establishment':
                copy = Object.assign(new Establishment(), property);
                break;
            case 'Business':
                copy = Object.assign(new Business(), property);
                break;
            case 'Hotel':
                copy = Object.assign(new Hotel(), property);
                break;
            case 'IndustrialPlant':
                copy = Object.assign(new IndustrialPlant(), property);
                break;
            case 'Office':
                copy = Object.assign(new Office(), property);
                break;
            case 'Parking':
                copy = Object.assign(new Parking(), property);
                break;
            case 'Terrain':
                copy = Object.assign(new Terrain(), property);
        }

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
