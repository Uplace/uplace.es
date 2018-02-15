import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CookieModule } from 'ngx-cookie';
import {SlickModule} from "ngx-slick";
import {AgmCoreModule} from "@agm/core";

@NgModule({
    imports: [
        NgbModule.forRoot(),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: true,
            i18nEnabled: true,
            defaultI18nLang: 'en'
        }),
        InfiniteScrollModule,
        CookieModule.forRoot(),
        SlickModule.forRoot(),
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyA2cKil90Egaj6l1QY-h42Slh0UScns69Y',
            libraries: ['places']
        }),
    ],
    exports: [
        FormsModule,
        CommonModule,
        NgbModule,
        NgJhipsterModule,
        InfiniteScrollModule,
        SlickModule,
        AgmCoreModule
    ]
})
export class UplaceSharedLibsModule {}
