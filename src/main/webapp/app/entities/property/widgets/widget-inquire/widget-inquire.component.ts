import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../property.model";
import {Mail} from "../../../../shared/model/mail.model";
import {PropertyService} from "../../property.service";
import {Request, RequestOrigin} from "../../../../shared/request/request.model";
import {JhiAlertService} from "ng-jhipster";

@Component({
    selector: 'up-widget-inquire',
    templateUrl: './widget-inquire.component.html',
    styles: []
})
export class WidgetInquireComponent implements OnInit {

    @Input() property: Property;

    request: Request = new Request();

    constructor(
        private propertyService: PropertyService,
        private alertService: JhiAlertService
    ) {
    }

    ngOnInit() {
    }

    onSubmit() {
        this.request.property = this.property;
        this.request.origin = RequestOrigin.WEB;
        this.propertyService.inquire(this.property.reference, this.request).subscribe((response) => {
            if (response.ok) {
                this.alertService.success("Thanks for your interest");
            } else {
                this.alertService.error("error");
            }
        });
    }

}
