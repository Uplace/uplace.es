import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { UplacePropertyModule } from './property/property.module';
import { UplaceApartmentModule } from './apartment/apartment.module';
import { UplaceOfficeModule } from './office/office.module';
import { UplaceAgentModule } from './agent/agent.module';
import { UplaceParkingModule } from './parking/parking.module';
import { UplaceTerrainModule } from './terrain/terrain.module';
import { UplaceLocationModule } from './location/location.module';
import { UplaceGalleryModule } from './gallery/gallery.module';
import { UplacePhotoModule } from './photo/photo.module';
import { UplaceBusinessModule } from './business/business.module';
import { UplaceNotificationModule } from './notification/notification.module';
import { UplaceBuildingModule } from './building/building.module';
import { UplaceHotelModule } from './hotel/hotel.module';
import { UplaceIndustrialPlantModule } from './industrial-plant/industrial-plant.module';
import { UplaceEstablishmentModule } from './establishment/establishment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        UplacePropertyModule,
        UplaceApartmentModule,
        UplaceOfficeModule,
        UplaceAgentModule,
        UplaceParkingModule,
        UplaceTerrainModule,
        UplaceLocationModule,
        UplaceGalleryModule,
        UplacePhotoModule,
        UplaceBusinessModule,
        UplaceNotificationModule,
        UplaceBuildingModule,
        UplaceHotelModule,
        UplaceIndustrialPlantModule,
        UplaceEstablishmentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceEntityModule {}
