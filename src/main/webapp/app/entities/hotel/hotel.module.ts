import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    HotelService,
    HotelPopupService,
    HotelComponent,
    HotelDetailComponent,
    HotelDialogComponent,
    HotelPopupComponent,
    HotelDeletePopupComponent,
    HotelDeleteDialogComponent,
    hotelRoute,
    hotelPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hotelRoute,
    ...hotelPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HotelComponent,
        HotelDetailComponent,
        HotelDialogComponent,
        HotelDeleteDialogComponent,
        HotelPopupComponent,
        HotelDeletePopupComponent,
    ],
    entryComponents: [
        HotelComponent,
        HotelDialogComponent,
        HotelPopupComponent,
        HotelDeleteDialogComponent,
        HotelDeletePopupComponent,
    ],
    providers: [
        HotelService,
        HotelPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceHotelModule {}
