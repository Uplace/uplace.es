import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Building } from './building.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BuildingService {

    private resourceUrl =  SERVER_API_URL + 'api/buildings';

    constructor(private http: Http) { }

    create(building: Building): Observable<Building> {
        const copy = this.convert(building);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(building: Building): Observable<Building> {
        const copy = this.convert(building);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Building> {
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
     * Convert a returned JSON object to Building.
     */
    private convertItemFromServer(json: any): Building {
        const entity: Building = Object.assign(new Building(), json);
        return entity;
    }

    /**
     * Convert a Building to a JSON which can be sent to the server.
     */
    private convert(building: Building): Building {
        const copy: Building = Object.assign({}, building);
        return copy;
    }
}
