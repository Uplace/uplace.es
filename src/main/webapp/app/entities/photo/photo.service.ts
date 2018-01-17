import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Photo } from './photo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PhotoService {

    private resourceUrl =  SERVER_API_URL + 'api/photos';

    constructor(private http: Http) { }

    create(photo: Photo): Observable<Photo> {
        const copy = this.convert(photo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(photo: Photo): Observable<Photo> {
        const copy = this.convert(photo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Photo> {
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
     * Convert a returned JSON object to Photo.
     */
    private convertItemFromServer(json: any): Photo {
        const entity: Photo = Object.assign(new Photo(), json);
        return entity;
    }

    /**
     * Convert a Photo to a JSON which can be sent to the server.
     */
    private convert(photo: Photo): Photo {
        const copy: Photo = Object.assign({}, photo);
        return copy;
    }
}
