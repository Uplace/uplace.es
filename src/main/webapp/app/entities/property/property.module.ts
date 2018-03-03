import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    PropertyService,
    PropertyPopupService,
    PropertyComponent,
    PropertyDetailComponent,
    PropertyDialogComponent,
    PropertyPopupComponent,
    PropertyDeletePopupComponent,
    PropertyDeleteDialogComponent,
    propertyRoute,
    propertyPopupRoute,
} from './';
import {PropertyResolvePagingParams} from "./property.route";
import { WidgetActionComponent } from './widgets/widget-action/widget-action.component';
import { WidgetAgentComponent } from './widgets/widget-agent/widget-agent.component';
import { WidgetInquireComponent } from './widgets/widget-inquire/widget-inquire.component';
import { WidgetRecentsComponent } from './widgets/widget-recents/widget-recents.component';
import { PropertyFeaturesComponent } from './property-features/property-features.component';
import { PropertyFeatureComponent } from './property-feature/property-feature.component';
import { PropertyFilterComponent } from './property-filter/property-filter.component';

const ENTITY_STATES = [
    ...propertyRoute,
    ...propertyPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PropertyComponent,
        PropertyDetailComponent,
        PropertyDialogComponent,
        PropertyDeleteDialogComponent,
        PropertyPopupComponent,
        PropertyDeletePopupComponent,
        WidgetActionComponent,
        WidgetAgentComponent,
        WidgetInquireComponent,
        WidgetRecentsComponent,
        PropertyFeaturesComponent,
        PropertyFeatureComponent,
        PropertyFilterComponent

    ],
    entryComponents: [
        PropertyComponent,
        PropertyDialogComponent,
        PropertyPopupComponent,
        PropertyDeleteDialogComponent,
        PropertyDeletePopupComponent,
    ],
    providers: [
        PropertyService,
        PropertyPopupService,
        PropertyResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplacePropertyModule {}
