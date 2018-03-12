import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PropertiesComponent} from './properties.component';
import {PropertiesNewComponent} from "./properties-new/properties-new.component";
import {UplaceSharedModule} from "../../../shared";
import {PropertyService} from "../../../entities/property";
import {PropertyResolvePagingParams} from "../dashboard.route";
import { PropertyMapComponent } from './property-map/property-map.component';
import { PropertyAmenitiesComponent } from './property-amenities/property-amenities.component';
import { PropertyPhotosComponent } from './property-photos/property-photos.component';
import {FileUploadModule} from 'ng2-file-upload';
import {RouterModule} from "@angular/router";

@NgModule({
    imports: [
        UplaceSharedModule,
        CommonModule,
        FileUploadModule,
        RouterModule
    ],
    declarations: [
        PropertiesComponent,
        PropertiesNewComponent,
        PropertyMapComponent,
        PropertyAmenitiesComponent,
        PropertyPhotosComponent
    ],
    providers: [
        PropertyService,
        PropertyResolvePagingParams
    ]
})
export class PropertiesModule {
}
