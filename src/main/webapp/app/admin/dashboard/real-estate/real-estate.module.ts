import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RealEstateComponent} from './real-estate.component';
import {UplaceSharedModule} from "../../../shared";
import {RouterModule} from "@angular/router";

@NgModule({
    imports: [
        CommonModule,
        UplaceSharedModule,
        RouterModule
    ],
    declarations: [RealEstateComponent]
})
export class RealEstateModule {
}
