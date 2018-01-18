import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Terrain } from './terrain.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TerrainService {

    private resourceUrl =  SERVER_API_URL + 'api/terrains';

    constructor(private http: Http) { }

    create(terrain: Terrain): Observable<Terrain> {
        const copy = this.convert(terrain);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(terrain: Terrain): Observable<Terrain> {
        const copy = this.convert(terrain);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Terrain> {
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
     * Convert a returned JSON object to Terrain.
     */
    private convertItemFromServer(json: any): Terrain {
        const entity: Terrain = Object.assign(new Terrain(), json);
        return entity;
    }

    /**
     * Convert a Terrain to a JSON which can be sent to the server.
     */
    private convert(terrain: Terrain): Terrain {
        const copy: Terrain = Object.assign({}, terrain);
        return copy;
    }
}
