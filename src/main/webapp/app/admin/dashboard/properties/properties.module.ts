import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PropertiesComponent} from './properties.component';
import {PropertiesNewComponent} from "./properties-new/properties-new.component";
import {UplaceSharedModule} from "../../../shared";
import {RouterModule} from "@angular/router";
import {dashboardPropertyRoute} from "./properties.route";

const DASHBOARD_PROPERTY_ROUTES = [
    ...dashboardPropertyRoute
];

@NgModule({
    imports: [
        UplaceSharedModule,
        CommonModule,
        RouterModule.forChild(DASHBOARD_PROPERTY_ROUTES)
    ],
    declarations: [
        PropertiesComponent,
        PropertiesNewComponent
    ]
})
export class PropertiesModule {
}
