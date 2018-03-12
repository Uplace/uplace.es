import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {UplaceSharedModule} from "../../shared";
import {PropertiesModule} from "./properties/properties.module";
import {RouterModule} from "@angular/router";

@NgModule({
    imports: [
        UplaceSharedModule,
        CommonModule,
        PropertiesModule,
        RouterModule
    ],
    declarations: [
        DashboardComponent
    ],
    providers: []
})
export class DashboardModule {
}
