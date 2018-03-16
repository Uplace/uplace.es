import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CompanyComponent} from './company.component';
import {UplaceSharedModule} from "../../../shared";

@NgModule({
    imports: [
        UplaceSharedModule,
        CommonModule
    ],
    declarations: [CompanyComponent]
})
export class CompanyModule {
}
