import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import { HomeMapComponent } from './home-map/home-map.component';
import {HomePropertiesComponent} from "./sections/home-properties/home-properties.component";
import {HomeCategoriesComponent} from "./sections/home-categories/home-categories.component";
import {HomeCarouselComponent} from "./sections/home-carousel/home-carousel.component";
import {HomePricingComponent} from "./sections/home-pricing/home-pricing.component";
import {HomePartnersComponent} from "./sections/home-partners/home-partners.component";

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild([ HOME_ROUTE ])
    ],
    declarations: [
        HomeComponent,
        HomeMapComponent,
        HomePropertiesComponent,
        HomeCategoriesComponent,
        HomeCarouselComponent,
        HomePricingComponent,
        HomePartnersComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceHomeModule {}
