import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/en';
import { AgmJsMarkerClustererModule, ClusterManager } from "@agm/js-marker-clusterer";
import {
    UplaceSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    UpAlertComponent,
    UpAlertErrorComponent
} from './';
import {EnumToArrayPipe} from "./pipes/enum-to-string.pipe";

@NgModule({
    imports: [
        UplaceSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        EnumToArrayPipe,
        UpAlertComponent,
        UpAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        },
        AgmJsMarkerClustererModule,
        ClusterManager
    ],
    exports: [
        UplaceSharedLibsModule,
        EnumToArrayPipe,
        FindLanguageFromKeyPipe,
        UpAlertComponent,
        UpAlertErrorComponent
    ]
})
export class UplaceSharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
