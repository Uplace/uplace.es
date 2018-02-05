import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UplaceSharedModule } from '../shared';
import { HOME_ROUTE, HomeComponent } from './';
import { FilterComponent } from './filter/filter.component';
import { MapComponent } from './map/map.component';
import { WhyUsComponent } from './why-us/why-us.component';
import { FeaturedComponent } from './featured/featured.component';
import { RecentComponent } from './recent/recent.component';
import {FilterShowDirective} from './directives/filter-show.directive';
import {AgmCoreModule} from '@agm/core';
import { AgmJsMarkerClustererModule } from '@agm/js-marker-clusterer';

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild([ HOME_ROUTE ]),
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyA2cKil90Egaj6l1QY-h42Slh0UScns69Y'
        }),
        AgmJsMarkerClustererModule
    ],
    declarations: [
        HomeComponent,
        FilterComponent,
        MapComponent,
        WhyUsComponent,
        FeaturedComponent,
        RecentComponent,
        FilterShowDirective
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceHomeModule {}
