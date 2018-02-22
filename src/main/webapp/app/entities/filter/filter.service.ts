import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import {Injectable} from '@angular/core';
import {createRequestOption} from '../../shared';
import {Filter} from './filter.model';

export type EntityResponseType = HttpResponse<Filter>;

@Injectable()
export class FilterService {

    private resourceUrl =  SERVER_API_URL + 'api/filters';

    constructor(private http: HttpClient) { }

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

    findAll(): Observable<Filter> {
        return this.http.get(`${this.resourceUrl}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<HttpResponse<Filter>> {
        const options = createRequestOption(req);
        return this.http.get<Filter>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Filter>) => this.convertResponse(res));
    }

    /*delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }*/

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Filter = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Location.
     */
    private convertItemFromServer(json: any): Filter {
        const entity: Filter = Object.assign(new Filter(), json);
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
