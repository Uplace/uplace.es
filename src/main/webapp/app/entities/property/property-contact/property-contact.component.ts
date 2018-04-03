import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../property.model";
import {Company, CompanyService} from "../../company";

@Component({
    selector: 'up-property-contact',
    templateUrl: './property-contact.component.html',
    styles: []
})
export class PropertyContactComponent implements OnInit {

    @Input() property: Property;

    company: Company;

    constructor(
        private companyService: CompanyService
    ) {
    }

    ngOnInit() {
        this.companyService.query().subscribe((result) => {
            this.company = result.body;
        })
    }

}
