import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Hotel } from './hotel.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Hotel>;

@Injectable()
export class HotelService {

    private resourceUrl =  SERVER_API_URL + 'api/hotels';

    constructor(private http: HttpClient) { }

    create(hotel: Hotel): Observable<EntityResponseType> {
        const copy = this.convert(hotel);
        return this.http.post<Hotel>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(hotel: Hotel): Observable<EntityResponseType> {
        const copy = this.convert(hotel);
        return this.http.put<Hotel>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Hotel>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Hotel[]>> {
        const options = createRequestOption(req);
        return this.http.get<Hotel[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Hotel[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Hotel = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Hotel[]>): HttpResponse<Hotel[]> {
        const jsonResponse: Hotel[] = res.body;
        const body: Hotel[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Hotel.
     */
    private convertItemFromServer(hotel: Hotel): Hotel {
        const copy: Hotel = Object.assign({}, hotel);
        return copy;
    }

    /**
     * Convert a Hotel to a JSON which can be sent to the server.
     */
    private convert(hotel: Hotel): Hotel {
        const copy: Hotel = Object.assign({}, hotel);
        return copy;
    }
}
