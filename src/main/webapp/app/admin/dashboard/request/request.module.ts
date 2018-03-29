import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RequestComponent} from './request.component';
import {UplaceSharedModule} from "../../../shared";
import {RouterModule} from "@angular/router";
import {RequestPopupComponent} from "./request-popup.component";

@NgModule({
    imports: [
        CommonModule,
        UplaceSharedModule,
        RouterModule
    ],
    entryComponents: [
        RequestPopupComponent
    ],
    declarations: [
        RequestComponent,
        RequestPopupComponent
    ],
    providers: []
})
export class RequestModule {
}
