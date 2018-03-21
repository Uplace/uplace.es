import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../../app.constants';

import { RealEstate } from '../../../entities/real-estate/real-estate.model';
import { createRequestOption } from '../../../shared/index';
import {Property} from "../../../entities/property";

export type EntityResponseType = HttpResponse<RealEstate>;

@Injectable()
export class RealEstateService {

    private resourceUrl =  SERVER_API_URL + 'api/real-estates';

    constructor(private http: HttpClient) { }

    create(realEstate: RealEstate): Observable<EntityResponseType> {
        const copy = this.convert(realEstate);
        return this.http.post<RealEstate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(realEstate: RealEstate): Observable<EntityResponseType> {
        const copy = this.convert(realEstate);
        return this.http.put<RealEstate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RealEstate>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RealEstate[]>> {
        const options = createRequestOption(req);
        return this.http.get<RealEstate[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RealEstate[]>) => this.convertArrayResponse(res));
    }

    findProperties(reference: string): Observable<HttpResponse<Property[]>> {
        return this.http.get<Property[]>(`${this.resourceUrl}/${reference}/properties`, { observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RealEstate = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RealEstate[]>): HttpResponse<RealEstate[]> {
        const jsonResponse: RealEstate[] = res.body;
        const body: RealEstate[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RealEstate.
     */
    private convertItemFromServer(realEstate: RealEstate): RealEstate {
        const copy: RealEstate = Object.assign({}, realEstate);
        return copy;
    }

    /**
     * Convert a RealEstate to a JSON which can be sent to the server.
     */
    private convert(realEstate: RealEstate): RealEstate {
        const copy: RealEstate = Object.assign({}, realEstate);
        return copy;
    }
}
