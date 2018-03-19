import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Notification } from './notification.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Notification>;

@Injectable()
export class NotificationService {

    private resourceUrl =  SERVER_API_URL + 'api/notifications';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(notification: Notification): Observable<EntityResponseType> {
        const copy = this.convert(notification);
        return this.http.post<Notification>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(notification: Notification): Observable<EntityResponseType> {
        const copy = this.convert(notification);
        return this.http.put<Notification>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Notification>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Notification[]>> {
        const options = createRequestOption(req);
        return this.http.get<Notification[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Notification[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Notification = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Notification[]>): HttpResponse<Notification[]> {
        const jsonResponse: Notification[] = res.body;
        const body: Notification[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Notification.
     */
    private convertItemFromServer(notification: Notification): Notification {
        const copy: Notification = Object.assign({}, notification);
        copy.creation = this.dateUtils
            .convertDateTimeFromServer(notification.creation);
        return copy;
    }

    /**
     * Convert a Notification to a JSON which can be sent to the server.
     */
    private convert(notification: Notification): Notification {
        const copy: Notification = Object.assign({}, notification);

        copy.creation = this.dateUtils.toDate(notification.creation);
        return copy;
    }
}
