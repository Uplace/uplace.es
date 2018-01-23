import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Agent } from './agent.model';
import { AgentService } from './agent.service';

@Component({
    selector: 'up-agent-detail',
    templateUrl: './agent-detail.component.html'
})
export class AgentDetailComponent implements OnInit, OnDestroy {

    agent: Agent;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private agentService: AgentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAgents();
    }

    load(id) {
        this.agentService.find(id).subscribe((agent) => {
            this.agent = agent;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAgents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'agentListModification',
            (response) => this.load(this.agent.id)
        );
    }
}
