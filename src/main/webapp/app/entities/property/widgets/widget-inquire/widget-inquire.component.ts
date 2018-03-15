import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../property.model";
import {Mail} from "../../../../shared/model/mail.model";
import {NotificationService} from "../../../notification";

@Component({
    selector: 'up-widget-inquire',
    templateUrl: './widget-inquire.component.html',
    styles: []
})
export class WidgetInquireComponent implements OnInit {

    @Input() property: Property;

    mail: Mail = new Mail();

    constructor(
        private notificationService: NotificationService
    ) { }

    ngOnInit() {
    }

    onSubmit() {
        console.log('Sumitted mail');
        console.log(this.mail);
    }

}
