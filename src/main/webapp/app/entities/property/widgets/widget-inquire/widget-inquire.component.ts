import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../property.model";
import {Mail} from "../../../../shared/model/mail.model";
import {PropertyService} from "../../property.service";

@Component({
    selector: 'up-widget-inquire',
    templateUrl: './widget-inquire.component.html',
    styles: []
})
export class WidgetInquireComponent implements OnInit {

    @Input() property: Property;

    mail: Mail = new Mail();

    constructor(
        private propertyService: PropertyService
    ) {
    }

    ngOnInit() {
    }

    onSubmit() {
        console.log(this.mail);
        this.propertyService.inquire(this.property.reference, this.mail).subscribe((response) => {
            console.log(response);
        });
    }

}
