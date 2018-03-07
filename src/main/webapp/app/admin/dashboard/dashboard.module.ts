import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {UplaceSharedModule} from "../../shared";
import {PropertiesModule} from "./properties/properties.module";
import {RouterModule} from "@angular/router";
const DASHBOARD_ROUTES = [

];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(DASHBOARD_ROUTES),
        CommonModule,
        PropertiesModule
    ],
    declarations: [
        DashboardComponent
    ]
})
export class DashboardModule {
}
