import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AgentComponent } from './agent.component';
import { AgentDetailComponent } from './agent-detail.component';
import { AgentPopupComponent } from './agent-dialog.component';
import { AgentDeletePopupComponent } from './agent-delete-dialog.component';

export const agentRoute: Routes = [
    {
        path: 'agent',
        component: AgentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agent/:id',
        component: AgentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const agentPopupRoute: Routes = [
    {
        path: 'agent-new',
        component: AgentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agent/:id/edit',
        component: AgentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agent/:id/delete',
        component: AgentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
