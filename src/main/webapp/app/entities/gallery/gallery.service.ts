import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Gallery } from './gallery.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GalleryService {

    private resourceUrl =  SERVER_API_URL + 'api/galleries';

    constructor(private http: Http) { }

    create(gallery: Gallery): Observable<Gallery> {
        const copy = this.convert(gallery);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(gallery: Gallery): Observable<Gallery> {
        const copy = this.convert(gallery);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Gallery> {
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
     * Convert a returned JSON object to Gallery.
     */
    private convertItemFromServer(json: any): Gallery {
        const entity: Gallery = Object.assign(new Gallery(), json);
        return entity;
    }

    /**
     * Convert a Gallery to a JSON which can be sent to the server.
     */
    private convert(gallery: Gallery): Gallery {
        const copy: Gallery = Object.assign({}, gallery);
        return copy;
    }
}
