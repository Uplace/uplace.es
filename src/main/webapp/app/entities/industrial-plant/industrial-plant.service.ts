import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { IndustrialPlant } from './industrial-plant.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IndustrialPlantService {

    private resourceUrl =  SERVER_API_URL + 'api/industrial-plants';

    constructor(private http: Http) { }

    create(industrialPlant: IndustrialPlant): Observable<IndustrialPlant> {
        const copy = this.convert(industrialPlant);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(industrialPlant: IndustrialPlant): Observable<IndustrialPlant> {
        const copy = this.convert(industrialPlant);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<IndustrialPlant> {
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
     * Convert a returned JSON object to IndustrialPlant.
     */
    private convertItemFromServer(json: any): IndustrialPlant {
        const entity: IndustrialPlant = Object.assign(new IndustrialPlant(), json);
        return entity;
    }

    /**
     * Convert a IndustrialPlant to a JSON which can be sent to the server.
     */
    private convert(industrialPlant: IndustrialPlant): IndustrialPlant {
        const copy: IndustrialPlant = Object.assign({}, industrialPlant);
        return copy;
    }
}
