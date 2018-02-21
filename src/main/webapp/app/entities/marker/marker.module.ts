import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {MarkerService} from './marker.service';

@NgModule({
    imports: [],
    declarations: [],
    entryComponents: [],
    providers: [
        MarkerService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceMarkerModule {}
