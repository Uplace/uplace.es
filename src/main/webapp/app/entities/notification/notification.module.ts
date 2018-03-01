import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import { UplaceAdminModule } from '../../admin/admin.module';
import {
    NotificationService,
    NotificationPopupService,
    NotificationComponent,
    NotificationDetailComponent,
    NotificationDialogComponent,
    NotificationPopupComponent,
    NotificationDeletePopupComponent,
    NotificationDeleteDialogComponent,
    notificationRoute,
    notificationPopupRoute,
    NotificationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...notificationRoute,
    ...notificationPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NotificationComponent,
        NotificationDetailComponent,
        NotificationDialogComponent,
        NotificationDeleteDialogComponent,
        NotificationPopupComponent,
        NotificationDeletePopupComponent,
    ],
    entryComponents: [
        NotificationComponent,
        NotificationDialogComponent,
        NotificationPopupComponent,
        NotificationDeleteDialogComponent,
        NotificationDeletePopupComponent,
    ],
    providers: [
        NotificationService,
        NotificationPopupService,
        NotificationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceNotificationModule {}
