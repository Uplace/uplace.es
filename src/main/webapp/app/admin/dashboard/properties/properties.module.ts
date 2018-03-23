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
import { PropertyModalComponent } from './property-modal/property-modal.component';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import { PropertyEstateComponent } from './property-estate/property-estate.component';
import { PropertyPriceComponent } from './property-price/property-price.component';

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
        PropertyPhotosComponent,
        PropertyModalComponent,
        PropertyEstateComponent,
        PropertyPriceComponent
    ],
    entryComponents: [
        PropertyModalComponent
    ],
    providers: [
        PropertyService,
        PropertyResolvePagingParams,
        NgbActiveModal
    ]
})
export class PropertiesModule {
}
