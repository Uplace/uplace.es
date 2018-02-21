import {Component, OnInit} from '@angular/core';
import {ResponseWrapper} from "../../../shared";
import {PropertyService, TransactionType} from "../../../entities/property";
import {JhiAlertService} from "ng-jhipster";

@Component({
    selector: 'up-home-properties',
    templateUrl: './home-properties.component.html',
    styles: []
})
export class HomePropertiesComponent implements OnInit {

    properties: any[];
    propertiesRent: any[] = [];
    propertiesBuy: any[] = [];
    propertiesRentBuy: any[] = [];
    propertiesTransfer: any[] = [];

    tabShow: TransactionType;
    TransactionType = TransactionType;

    constructor(private propertyService: PropertyService,
                private jhiAlertService: JhiAlertService) {
    }

    ngOnInit() {
        this.propertyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.properties = res.json;
                this.properties.forEach((property) => {

                    switch (property.transaction) {

                        case TransactionType[TransactionType.TRANSFER]:
                            this.propertiesTransfer.push(property);
                            break;

                        case TransactionType[TransactionType.RENT]:
                            this.propertiesRent.push(property);
                            break;

                        case TransactionType[TransactionType.BUY]:
                            this.propertiesBuy.push(property);
                            break;

                        case TransactionType[TransactionType.RENT_BUY]:
                            this.propertiesRentBuy.push(property);
                            break;

                    }
                });
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    changeTab(type?: TransactionType) {
        this.tabShow = type;
    }

}
