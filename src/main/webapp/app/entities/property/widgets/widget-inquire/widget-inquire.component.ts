import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../property.model";
import {Contact} from "../../../contact/contact.model";
import {ContactService} from "../../../contact/contact.service";

@Component({
    selector: 'up-widget-inquire',
    templateUrl: './widget-inquire.component.html',
    styles: []
})
export class WidgetInquireComponent implements OnInit {

    @Input() property: Property;

    contact: Contact;

    constructor(
        private contactService: ContactService
    ) {
        this.contact = new Contact();
    }

    ngOnInit() {
    }

    onSubmit() {
        this.contact.property = this.property;
        console.log('Submitted mail');
        console.log(this.contact);
        this.contactService.createContactProperty(this.contact).subscribe((response) => {
            console.log(response);
        });
    }

}
