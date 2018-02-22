import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { IndustrialPlant } from './industrial-plant.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<IndustrialPlant>;

@Injectable()
export class IndustrialPlantService {

    private resourceUrl =  SERVER_API_URL + 'api/industrial-plants';

    constructor(private http: HttpClient) { }

    create(industrialPlant: IndustrialPlant): Observable<EntityResponseType> {
        const copy = this.convert(industrialPlant);
        return this.http.post<IndustrialPlant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(industrialPlant: IndustrialPlant): Observable<EntityResponseType> {
        const copy = this.convert(industrialPlant);
        return this.http.put<IndustrialPlant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IndustrialPlant>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<IndustrialPlant[]>> {
        const options = createRequestOption(req);
        return this.http.get<IndustrialPlant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<IndustrialPlant[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: IndustrialPlant = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<IndustrialPlant[]>): HttpResponse<IndustrialPlant[]> {
        const jsonResponse: IndustrialPlant[] = res.body;
        const body: IndustrialPlant[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to IndustrialPlant.
     */
    private convertItemFromServer(industrialPlant: IndustrialPlant): IndustrialPlant {
        const copy: IndustrialPlant = Object.assign({}, industrialPlant);
        return copy;
    }

    /**
     * Convert a IndustrialPlant to a JSON which can be sent to the server.
     */
    private convert(industrialPlant: IndustrialPlant): IndustrialPlant {
        const copy: IndustrialPlant = Object.assign({}, industrialPlant);
        return copy;
    }
}
