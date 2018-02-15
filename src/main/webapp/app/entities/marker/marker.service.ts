import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import {MarkerModel} from "./marker.model";
import {Injectable} from "@angular/core";
import {createRequestOption, ResponseWrapper} from "../../shared";

@Injectable()
export class MarkerService {

    private resourceUrl =  SERVER_API_URL + 'api/markers';

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

    findAll(): Observable<MarkerModel> {
        return this.http.get(`${this.resourceUrl}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    /*delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }*/

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Location.
     */
    private convertItemFromServer(json: any): MarkerModel {
        const entity: MarkerModel = Object.assign(new MarkerModel(), json);
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
