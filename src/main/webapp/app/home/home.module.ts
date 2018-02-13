///<reference path="sections/home-featured/home-featured.component.ts"/>
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UplaceSharedModule } from '../shared';
import { HOME_ROUTE, HomeComponent } from './';
import {AgmCoreModule} from '@agm/core';
import {AgmJsMarkerClustererModule, ClusterManager} from '@agm/js-marker-clusterer';
import {HomeService} from "./home.service";
import {HomeWhyUsComponent} from "./sections/home-why-us/home-why-us.component";
import {HomePromotionComponent} from "./sections/home-promotion/home-promotion.component";
import {HomeTeamComponent} from "./sections/home-team/home-team.component";
import {HomeRecentComponent} from "./sections/home-recent/home-recent.component";
import {HomePartnersComponent} from "./sections/home-partners/home-partners.component";
import {HomeMapComponent} from "./home-map/home-map.component";
import {HomeFilterComponent} from "./home-filter/home-filter.component";
import {HomeFilterItemComponent} from "./home-filter-item/home-filter-item.component";
import {HomeFeaturedComponent} from "./sections/home-featured/home-featured.component";
import { NouisliderModule } from 'ng2-nouislider';
import { NgxSelectModule } from 'ngx-select-ex';

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild([ HOME_ROUTE ]),
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyA2cKil90Egaj6l1QY-h42Slh0UScns69Y',
            libraries: ['places']
        }),
        AgmJsMarkerClustererModule,
        NgxSelectModule,
        NouisliderModule
    ],
    declarations: [
        HomeComponent,
        HomeFeaturedComponent,
        HomeWhyUsComponent,
        HomePromotionComponent,
        HomeRecentComponent,
        HomeTeamComponent,
        HomePartnersComponent,
        HomeMapComponent,
        HomeFilterComponent,
        HomeFilterItemComponent
    ],
    entryComponents: [
    ],
    providers: [
        ClusterManager,
        HomeService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceHomeModule {}
