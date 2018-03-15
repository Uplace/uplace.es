import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../property.model";
import {Contact} from "../../../contact/contact.model";
import {ContactService} from "../../../contact/contact.service";
import {Mail} from "../../../../shared/model/mail.model";

@Component({
    selector: 'up-widget-inquire',
    templateUrl: './widget-inquire.component.html',
    styles: []
})
export class WidgetInquireComponent implements OnInit {

    @Input() property: Property;

    contact: Contact = new Contact();

    constructor(
        private contactService: ContactService
    ) {
        this.contact.mail = new Mail();
    }

    ngOnInit() {
    }

    onSubmit() {
        this.contact.property = this.property;
        this.contactService.createContactProperty(this.contact).subscribe((response) => {

        });
    }

}
