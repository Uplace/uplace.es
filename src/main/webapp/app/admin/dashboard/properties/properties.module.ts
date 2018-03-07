import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PropertiesComponent} from './properties.component';
import {PropertiesNewComponent} from "./properties-new/properties-new.component";
import {UplaceSharedModule} from "../../../shared";
import {PropertyService} from "../../../entities/property";
import {PropertyResolvePagingParams} from "../dashboard.route";

@NgModule({
    imports: [
        UplaceSharedModule,
        CommonModule,
    ],
    declarations: [
        PropertiesComponent,
        PropertiesNewComponent
    ],
    providers: [
        PropertyService,
        PropertyResolvePagingParams
    ]
})
export class PropertiesModule {
}
