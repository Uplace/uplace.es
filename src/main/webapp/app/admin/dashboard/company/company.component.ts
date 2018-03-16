import {Component, OnInit} from '@angular/core';
import {Company, CompanyService} from "../../../entities/company";
import {Location} from '../../../entities/location/location.model';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";

@Component({
    selector: 'up-company',
    templateUrl: './company.component.html',
    styles: []
})
export class CompanyComponent implements OnInit {

    company: Company = new Company();

    constructor(
        private companyService: CompanyService,
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService
    ) {
        this.company.location = new Location();
        this.company.location.latitude = 41.390205;
        this.company.location.longitude = 2.154007;
    }

    ngOnInit() {
        console.log(this.company.location);
        this.companyService.query().subscribe((result) => {
            this.company = result.body;
        });
    }

    onSubmit() {
        console.log(this.company);
        if (this.company.id != null) {
            this.subscribeToSaveResponse(this.companyService.update(this.company));
        } else {
            this.subscribeToSaveResponse(this.companyService.create(this.company));
        }

    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Company>>) {
        window.scrollTo(0,0);
        result.subscribe((res: HttpResponse<Company>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onError(res.error));
    }

    private onSaveSuccess(result: Company) {
        this.eventManager.broadcast({ name: 'companyListModification', content: 'OK'});
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

}
