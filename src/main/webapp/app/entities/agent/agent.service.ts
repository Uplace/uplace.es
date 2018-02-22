import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Agent } from './agent.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Agent>;

@Injectable()
export class AgentService {

    private resourceUrl =  SERVER_API_URL + 'api/agents';

    constructor(private http: HttpClient) { }

    create(agent: Agent): Observable<EntityResponseType> {
        const copy = this.convert(agent);
        return this.http.post<Agent>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(agent: Agent): Observable<EntityResponseType> {
        const copy = this.convert(agent);
        return this.http.put<Agent>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Agent>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Agent[]>> {
        const options = createRequestOption(req);
        return this.http.get<Agent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Agent[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Agent = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Agent[]>): HttpResponse<Agent[]> {
        const jsonResponse: Agent[] = res.body;
        const body: Agent[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Agent.
     */
    private convertItemFromServer(agent: Agent): Agent {
        const copy: Agent = Object.assign({}, agent);
        return copy;
    }

    /**
     * Convert a Agent to a JSON which can be sent to the server.
     */
    private convert(agent: Agent): Agent {
        const copy: Agent = Object.assign({}, agent);
        return copy;
    }
}
