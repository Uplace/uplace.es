import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {UplaceSharedModule} from "../../shared";
import {PropertiesModule} from "./properties/properties.module";
import {FileUploadModule} from 'ng2-file-upload';

@NgModule({
    imports: [
        UplaceSharedModule,
        CommonModule,
        PropertiesModule
    ],
    declarations: [
        DashboardComponent
    ],
    providers: []
})
export class DashboardModule {
}
