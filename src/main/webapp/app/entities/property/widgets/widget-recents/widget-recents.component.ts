import {Component, OnInit} from '@angular/core';
import {PropertyService} from "../../property.service";
import {Property} from "../../property.model";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Notification} from "../../../notification";
import {JhiAlertService} from "ng-jhipster";

@Component({
    selector: 'up-widget-recents',
    templateUrl: './widget-recents.component.html',
    styles: []
})
export class WidgetRecentsComponent implements OnInit {

    properties: Property[];

    constructor(
        private propertyService: PropertyService,
        private jhiAlertService: JhiAlertService,
        ) {
    }

    ngOnInit() {
        this.propertyService.query({
            size: 4,
            sort: ["created,desc"]
        }).subscribe(
            (res: HttpResponse<Notification[]>) => this.onSuccess(res.body),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onSuccess(data) {
        this.properties = data;
        console.log(this.properties);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

}
