import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Parking } from './parking.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Parking>;

@Injectable()
export class ParkingService {

    private resourceUrl =  SERVER_API_URL + 'api/parkings';

    constructor(private http: HttpClient) { }

    create(parking: Parking): Observable<EntityResponseType> {
        const copy = this.convert(parking);
        return this.http.post<Parking>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(parking: Parking): Observable<EntityResponseType> {
        const copy = this.convert(parking);
        return this.http.put<Parking>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Parking>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Parking[]>> {
        const options = createRequestOption(req);
        return this.http.get<Parking[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Parking[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Parking = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Parking[]>): HttpResponse<Parking[]> {
        const jsonResponse: Parking[] = res.body;
        const body: Parking[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Parking.
     */
    private convertItemFromServer(parking: Parking): Parking {
        const copy: Parking = Object.assign({}, parking);
        return copy;
    }

    /**
     * Convert a Parking to a JSON which can be sent to the server.
     */
    private convert(parking: Parking): Parking {
        const copy: Parking = Object.assign({}, parking);
        return copy;
    }
}
