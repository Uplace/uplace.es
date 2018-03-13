import {Component, OnInit} from '@angular/core';
import {Property, PropertyService, TransactionType} from "../../../entities/property";
import {JhiAlertService} from "ng-jhipster";
import {HttpResponse} from "@angular/common/http";

@Component({
    selector: 'up-home-properties',
    templateUrl: './home-properties.component.html',
    styles: []
})
export class HomePropertiesComponent implements OnInit {

    properties: any[];

    constructor(private propertyService: PropertyService,
                private jhiAlertService: JhiAlertService) {
    }

    ngOnInit() {
        this.propertyService.query({
            size: 8,
            sort: ['created,desc']
        }).subscribe((res: HttpResponse<Property[]>) => {
            this.properties = res.body;
        })
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

}
