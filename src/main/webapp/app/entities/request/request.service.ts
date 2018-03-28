import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Request } from './request.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Request>;

@Injectable()
export class RequestService {

    private resourceUrl =  SERVER_API_URL + 'api/requests';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(request: Request): Observable<EntityResponseType> {
        const copy = this.convert(request);
        return this.http.post<Request>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(request: Request): Observable<EntityResponseType> {
        const copy = this.convert(request);
        return this.http.put<Request>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Request>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Request[]>> {
        const options = createRequestOption(req);
        return this.http.get<Request[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Request[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Request = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Request[]>): HttpResponse<Request[]> {
        const jsonResponse: Request[] = res.body;
        const body: Request[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Request.
     */
    private convertItemFromServer(request: Request): Request {
        const copy: Request = Object.assign({}, request);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(request.date);
        return copy;
    }

    /**
     * Convert a Request to a JSON which can be sent to the server.
     */
    private convert(request: Request): Request {
        const copy: Request = Object.assign({}, request);

        copy.date = this.dateUtils.toDate(request.date);
        return copy;
    }
}
