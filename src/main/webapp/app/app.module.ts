import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { UplaceSharedModule, UserRouteAccessService } from './shared';
import { UplaceAppRoutingModule} from './app-routing.module';
import { UplaceHomeModule } from './home/home.module';
import { UplaceAdminModule } from './admin/admin.module';
import { UplaceAccountModule } from './account/account.module';
import { UplaceEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    UpMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

import { AgmCoreModule } from '@agm/core';

@NgModule({
    imports: [
        BrowserModule,
        UplaceAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        UplaceSharedModule,
        UplaceHomeModule,
        UplaceAdminModule,
        UplaceAccountModule,
        UplaceEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyA2cKil90Egaj6l1QY-h42Slh0UScns69Y'
        })
    ],
    declarations: [
        UpMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ UpMainComponent ]
})
export class UplaceAppModule {}
