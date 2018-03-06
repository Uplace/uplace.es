import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserDialogComponent,
    UserDeleteDialogComponent,
    UserMgmtDetailComponent,
    UserMgmtDialogComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    UpMetricsMonitoringModalComponent,
    UpMetricsMonitoringComponent,
    UpHealthModalComponent,
    UpHealthCheckComponent,
    UpConfigurationComponent,
    UpDocsComponent,
    AuditsService,
    UpConfigurationService,
    UpHealthService,
    UpMetricsService,
    LogsService,
    UserResolvePagingParams,
    UserResolve,
    UserModalService
} from './';
import {DashboardModule} from "./dashboard/dashboard.module";

@NgModule({
    imports: [
        UplaceSharedModule,
        DashboardModule,
        RouterModule.forChild(adminState),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserDialogComponent,
        UserDeleteDialogComponent,
        UserMgmtDetailComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        UpConfigurationComponent,
        UpHealthCheckComponent,
        UpHealthModalComponent,
        UpDocsComponent,
        UpMetricsMonitoringComponent,
        UpMetricsMonitoringModalComponent
    ],
    entryComponents: [
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        UpHealthModalComponent,
        UpMetricsMonitoringModalComponent,
    ],
    providers: [
        AuditsService,
        UpConfigurationService,
        UpHealthService,
        UpMetricsService,
        LogsService,
        UserResolvePagingParams,
        UserResolve,
        UserModalService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceAdminModule {}
