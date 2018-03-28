import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RequestComponent} from './request.component';
import {UplaceSharedModule} from "../../../shared";
import {RouterModule} from "@angular/router";
import {RequestDialogComponent, RequestPopupComponent} from "./request-dialog.component";
import {RequestPopupService} from "./request-popup.service";

@NgModule({
    imports: [
        CommonModule,
        UplaceSharedModule,
        RouterModule
    ],
    entryComponents: [
        RequestDialogComponent,
        RequestPopupComponent
    ],
    declarations: [
        RequestComponent,
        RequestPopupComponent,
        RequestDialogComponent
    ],
    providers: [RequestPopupService]
})
export class RequestModule {
}
