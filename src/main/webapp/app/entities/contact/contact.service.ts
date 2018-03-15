import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import {Injectable} from "@angular/core";
import {Contact} from "./contact.model";

@Injectable()
export class ContactService {

    private resourceUrl =  SERVER_API_URL + 'api/contact-property';

    constructor(private http: HttpClient) { }

    createContactProperty(contact: Contact): Observable<Contact> {
        const copy = this.convert(contact);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            console.log(res);
            return this.convertItemFromServer(jsonResponse);
        });
    }

    /*update(location: Location): Observable<Location> {
        const copy = this.convert(location);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }*/

    /*findAll(): Observable<Marker> {
        return this.http.get(`${this.resourceUrl}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<HttpResponse<Marker[]>> {
        const options = createRequestOption(req);
        return this.http.get<Marker[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Marker[]>) => this.convertArrayResponse(res));
    }*/

    /*delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }*/

    /*private convertArrayResponse(res: HttpResponse<Marker[]>): HttpResponse<Marker[]> {
        const jsonResponse: Marker[] = res.body;
        const body: Marker[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }*/

    /**
     * Convert a returned JSON object to Location.
     */
    private convertItemFromServer(json: any): Contact {
        const entity: Contact = Object.assign(new Contact(), json);
        return entity;
    }
    /**
     * Convert a Location to a JSON which can be sent to the server.
     */
    private convert(contact: Contact): Contact {
        const copy: Contact = Object.assign({}, contact);
        return copy;
    }
}
