import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import {Injectable} from '@angular/core';
import {Filter} from './filter.model';

export type EntityResponseType = HttpResponse<Filter>;

@Injectable()
export class FilterService {

    private resourceUrl =  SERVER_API_URL + 'api/filters';

    constructor(private http: HttpClient) { }

    find(): Observable<HttpResponse<Filter>> {
        return this.http.get<Filter>(this.resourceUrl, { observe: 'response' })
            .map((res: HttpResponse<Filter>) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Filter = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Filter.
     */
    private convertItemFromServer(json: any): Filter {
        const entity: Filter = Object.assign(new Filter(), json);
        return entity;
    }

}
