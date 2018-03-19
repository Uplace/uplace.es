import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Terrain } from './terrain.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Terrain>;

@Injectable()
export class TerrainService {

    private resourceUrl =  SERVER_API_URL + 'api/terrains';

    constructor(private http: HttpClient) { }

    create(terrain: Terrain): Observable<EntityResponseType> {
        const copy = this.convert(terrain);
        return this.http.post<Terrain>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(terrain: Terrain): Observable<EntityResponseType> {
        const copy = this.convert(terrain);
        return this.http.put<Terrain>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Terrain>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Terrain[]>> {
        const options = createRequestOption(req);
        return this.http.get<Terrain[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Terrain[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Terrain = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Terrain[]>): HttpResponse<Terrain[]> {
        const jsonResponse: Terrain[] = res.body;
        const body: Terrain[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Terrain.
     */
    private convertItemFromServer(terrain: Terrain): Terrain {
        const copy: Terrain = Object.assign({}, terrain);
        return copy;
    }

    /**
     * Convert a Terrain to a JSON which can be sent to the server.
     */
    private convert(terrain: Terrain): Terrain {
        const copy: Terrain = Object.assign({}, terrain);
        return copy;
    }
}
