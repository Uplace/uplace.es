import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import {Marker} from "./marker.model";
import {Injectable} from "@angular/core";
import {createRequestOption} from '../../shared';

@Injectable()
export class MarkerService {

    private resourceUrl =  SERVER_API_URL + 'api/markers';

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

    findAll(): Observable<Marker> {
        return this.http.get(`${this.resourceUrl}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<HttpResponse<Marker[]>> {
        const options = createRequestOption(req);
        return this.http.get<Marker[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Marker[]>) => this.convertArrayResponse(res));
    }

    /*delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }*/

    private convertArrayResponse(res: HttpResponse<Marker[]>): HttpResponse<Marker[]> {
        const jsonResponse: Marker[] = res.body;
        const body: Marker[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Location.
     */
    private convertItemFromServer(json: any): Marker {
        const entity: Marker = Object.assign(new Marker(), json);
        return entity;
    }

    /**
     * Convert a Location to a JSON which can be sent to the server.
     */
    private convert(marker: Marker): Marker {
        const copy: Marker = Object.assign({}, marker);
        return copy;
    }
}
