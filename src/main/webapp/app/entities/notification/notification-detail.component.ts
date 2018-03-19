import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Notification } from './notification.model';
import { NotificationService } from './notification.service';

@Component({
    selector: 'up-notification-detail',
    templateUrl: './notification-detail.component.html'
})
export class NotificationDetailComponent implements OnInit, OnDestroy {

    notification: Notification;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private notificationService: NotificationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNotifications();
    }

    load(id) {
        this.notificationService.find(id)
            .subscribe((notificationResponse: HttpResponse<Notification>) => {
                this.notification = notificationResponse.body;
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

    registerChangeInNotifications() {
        this.eventSubscriber = this.eventManager.subscribe(
            'notificationListModification',
            (response) => this.load(this.notification.id)
        );
    }
}
