import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {UplaceSharedModule} from "../../shared";
import {PropertiesModule} from "./properties/properties.module";
import {RouterModule} from "@angular/router";
import {CompanyModule} from "./company/company.module";
import {RealEstateService} from "../../entities/real-estate";
import {RealEstateModule} from "./real-estate/real-estate.module";
import {NotificationService} from "./notification/notification.service";
import {NotificationModule} from "./notification/notification.module";

@NgModule({
    imports: [
        UplaceSharedModule,
        CommonModule,
        PropertiesModule,
        CompanyModule,
        RealEstateModule,
        NotificationModule,
        RouterModule
    ],
    declarations: [
        DashboardComponent
    ],
    providers: [RealEstateService, NotificationService]
})
export class DashboardModule {
}
