import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Building } from './building.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Building>;

@Injectable()
export class BuildingService {

    private resourceUrl =  SERVER_API_URL + 'api/buildings';

    constructor(private http: HttpClient) { }

    create(building: Building): Observable<EntityResponseType> {
        const copy = this.convert(building);
        return this.http.post<Building>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(building: Building): Observable<EntityResponseType> {
        const copy = this.convert(building);
        return this.http.put<Building>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Building>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Building[]>> {
        const options = createRequestOption(req);
        return this.http.get<Building[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Building[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Building = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Building[]>): HttpResponse<Building[]> {
        const jsonResponse: Building[] = res.body;
        const body: Building[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Building.
     */
    private convertItemFromServer(building: Building): Building {
        const copy: Building = Object.assign({}, building);
        return copy;
    }

    /**
     * Convert a Building to a JSON which can be sent to the server.
     */
    private convert(building: Building): Building {
        const copy: Building = Object.assign({}, building);
        return copy;
    }
}
