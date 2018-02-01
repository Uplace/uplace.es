import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import { FilterComponent } from './filter/filter.component';
import { MapComponent } from './map/map.component';
import { WhyUsComponent } from './why-us/why-us.component';
import { FeaturedComponent } from './featured/featured.component';
import { RecentComponent } from './recent/recent.component';

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild([ HOME_ROUTE ])
    ],
    declarations: [
        HomeComponent,
        FilterComponent,
        MapComponent,
        WhyUsComponent,
        FeaturedComponent,
        RecentComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceHomeModule {}
