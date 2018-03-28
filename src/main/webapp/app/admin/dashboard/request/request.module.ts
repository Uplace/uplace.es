import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RequestComponent} from './request.component';
import {UplaceSharedModule} from "../../../shared";
import {RouterModule} from "@angular/router";

@NgModule({
    imports: [
        CommonModule,
        UplaceSharedModule,
        RouterModule
    ],
    declarations: [RequestComponent]
})
export class RequestModule {
}
