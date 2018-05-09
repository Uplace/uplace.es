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
import {ProvideParentForm} from "./directives/parent-form.directive";
import {EnumToArrayPipe} from "./pipes/enumToArray.pipe";
import {RequestService} from "./request/request.service";
import {FilterService} from "./filter/filter.service";
import {SearchService} from "./search/search.service";
import {PropertyPriceDirective} from "./directives/property-price.directive";

@NgModule({
    imports: [
        UplaceSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        KeysPipe,
        EnumToArrayPipe,
        ProvideParentForm,
        PropertyPriceDirective,
        TruncatePipe,
        UpAlertComponent,
        UpAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'es'
        },
        AgmJsMarkerClustererModule,
        ClusterManager,
        RequestService,
        FilterService,
        SearchService
    ],
    exports: [
        UplaceSharedLibsModule,
        KeysPipe,
        TruncatePipe,
        EnumToArrayPipe,
        ProvideParentForm,
        PropertyPriceDirective,
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
