import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Company } from './company.model';
import { createRequestOption } from '../../shared/index';

export type EntityResponseType = HttpResponse<Company>;

@Injectable()
export class CompanyService {

    private resourceUrl =  SERVER_API_URL + 'api/company';

    constructor(private http: HttpClient) { }

    create(company: Company): Observable<EntityResponseType> {
        const copy = this.convert(company);
        return this.http.post<Company>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(company: Company): Observable<EntityResponseType> {
        const copy = this.convert(company);
        return this.http.put<Company>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Company>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Company>> {
        const options = createRequestOption(req);
        return this.http.get<Company>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Company>) => this.convertResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Company = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Company.
     */
    private convertItemFromServer(company: Company): Company {
        const copy: Company = Object.assign({}, company);
        return copy;
    }

    /**
     * Convert a Company to a JSON which can be sent to the server.
     */
    private convert(company: Company): Company {
        const copy: Company = Object.assign({}, company);
        return copy;
    }
}
