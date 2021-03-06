import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage, LocalStorageService, SessionStorageService  } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { UplaceSharedModule, UserRouteAccessService } from './shared';
import { UplaceAppRoutingModule} from './app-routing.module';
import { UplaceHomeModule } from './home';
import { UplaceAdminModule } from './admin/admin.module';
import { UplaceAccountModule } from './account/account.module';
import { UplaceEntityModule } from './entities/entity.module';
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
import { SidebarComponent } from './layouts/sidebar/sidebar.component';
import {AppComponent} from "./app.component";
import {UpAdminMainComponent} from "./layouts/admin-main/admin-main.component";
import {AdminNavbarComponent} from "./layouts/admin-navbar/admin-navbar.component";
import { AdminFooterComponent } from './layouts/admin-footer/admin-footer.component';
import { AdminSidebarComponent } from './layouts/admin-sidebar/admin-sidebar.component';

@NgModule({
    imports: [
        BrowserModule,
        UplaceAppRoutingModule,
        UplaceAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'up', separator: '-'}),
        UplaceSharedModule,
        UplaceHomeModule,
        UplaceAdminModule,
        UplaceAccountModule,
        UplaceEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        AppComponent,
        UpAdminMainComponent,
        AdminNavbarComponent,
        UpMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        SidebarComponent,
        AdminFooterComponent,
        AdminSidebarComponent
    ],
    providers: [
        ProfileService,
        PaginationConfig,
        UserRouteAccessService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [
                LocalStorageService,
                SessionStorageService
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [
                JhiEventManager
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        }
    ],
    bootstrap: [ AppComponent ]
})
export class UplaceAppModule {}
