import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import {Injectable} from '@angular/core';
import {createRequestOption, ResponseWrapper} from '../../shared';
import {FilterModel} from './filter.model';

@Injectable()
export class FilterService {

    private resourceUrl =  SERVER_API_URL + 'api/filters';

    constructor(private http: Http) { }

    /*create(location: Location): Observable<Location> {
        const copy = this.convert(location);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }*/

    /*update(location: Location): Observable<Location> {
        const copy = this.convert(location);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }*/

    findAll(): Observable<FilterModel> {
        return this.http.get(`${this.resourceUrl}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl)
            .map((res: Response) => this.convertResponse(res));
    }

    /*delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }*/

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        this.convertItemFromServer(jsonResponse);
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    /**
     * Convert a returned JSON object to Location.
     */
    private convertItemFromServer(json: any): FilterModel {
        const entity: FilterModel = Object.assign(new FilterModel(), json);
        return entity;
    }

    /**
     * Convert a Location to a JSON which can be sent to the server.
     */
    /*private convert(location: Location): Location {
        const copy: Location = Object.assign({}, location);
        return copy;
    }*/
}
