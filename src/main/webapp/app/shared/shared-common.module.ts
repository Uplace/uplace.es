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
import {KeysPipe} from "./pipes/keys.pipe";
import {TruncatePipe} from "./pipes/truncate.pipe";

@NgModule({
    imports: [
        UplaceSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        KeysPipe,
        TruncatePipe,
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
        KeysPipe,
        TruncatePipe,
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
