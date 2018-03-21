import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { UplacePropertyModule } from './property/property.module';
import { UplaceAgentModule } from './agent/agent.module';
import { UplaceLocationModule } from './location/location.module';
import { UplacePhotoModule } from './photo/photo.module';
import { UplaceNotificationModule } from './notification/notification.module';
import {UplaceFilterModule} from './filter/filter.module';
import {UplaceMarkerModule} from './marker/marker.module';
import { UplaceCompanyModule } from './company/company.module';
import { UplaceRealEstateModule } from './real-estate/real-estate.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        UplacePropertyModule,
        UplaceAgentModule,
        UplaceLocationModule,
        UplacePhotoModule,
        UplaceNotificationModule,
        UplaceFilterModule,
        UplaceMarkerModule,
        UplaceCompanyModule,
        UplaceRealEstateModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceEntityModule {}
