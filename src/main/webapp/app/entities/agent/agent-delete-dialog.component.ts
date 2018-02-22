import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Agent } from './agent.model';
import { AgentPopupService } from './agent-popup.service';
import { AgentService } from './agent.service';

@Component({
    selector: 'up-agent-delete-dialog',
    templateUrl: './agent-delete-dialog.component.html'
})
export class AgentDeleteDialogComponent {

    agent: Agent;

    constructor(
        private agentService: AgentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.agentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'agentListModification',
                content: 'Deleted an agent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-agent-delete-popup',
    template: ''
})
export class AgentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agentPopupService: AgentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.agentPopupService
                .open(AgentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
