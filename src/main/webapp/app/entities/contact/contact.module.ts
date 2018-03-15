import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContactService} from "./contact.service";

@NgModule({
    imports: [
        CommonModule
    ],
    providers: [
        ContactService
    ],
    declarations: []
})
export class UplaceContactModule {
}
