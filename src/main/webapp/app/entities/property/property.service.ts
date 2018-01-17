import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Property } from './property.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PropertyService {

    private resourceUrl =  SERVER_API_URL + 'api/properties';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(property: Property): Observable<Property> {
        const copy = this.convert(property);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(property: Property): Observable<Property> {
        const copy = this.convert(property);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Property> {
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
     * Convert a returned JSON object to Property.
     */
    private convertItemFromServer(json: any): Property {
        const entity: Property = Object.assign(new Property(), json);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        entity.updated = this.dateUtils
            .convertDateTimeFromServer(json.updated);
        return entity;
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
