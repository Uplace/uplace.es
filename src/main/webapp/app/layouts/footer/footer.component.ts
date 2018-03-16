import { Component } from '@angular/core';
import {Company, CompanyService} from "../../entities/company";

@Component({
    selector: 'up-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.css']
})
export class FooterComponent {

    company: Company;

    constructor(private companyService: CompanyService) {
        this.companyService.query().subscribe((result) => {
            this.company = result.body;
        })
    }
}
