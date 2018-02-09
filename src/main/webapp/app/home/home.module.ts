import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UplaceSharedModule } from '../shared';
import { HOME_ROUTE, HomeComponent } from './';
import {AgmCoreModule} from '@agm/core';
import {AgmJsMarkerClustererModule, ClusterManager} from '@agm/js-marker-clusterer';

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
        HomeComponent
    ],
    entryComponents: [
    ],
    providers: [
        ClusterManager
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceHomeModule {}
