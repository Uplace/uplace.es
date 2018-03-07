import {Component, OnInit, Renderer2} from '@angular/core';
import {JhiLanguageHelper, LoginModalService, LoginService, Principal} from "../../shared";
import {VERSION} from "../../app.constants";
import {JhiLanguageService} from "ng-jhipster";
import {ProfileService} from "../profiles/profile.service";
import {Router} from "@angular/router";
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'up-admin-navbar',
    templateUrl: './admin-navbar.component.html',
})
export class AdminNavbarComponent implements OnInit {

    inProduction: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    isSidebarCollapsed: boolean;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private renderer: Renderer2
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isSidebarCollapsed = false;
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
    }

    toggleSidebar(){
        if (!this.isSidebarCollapsed) {
            this.renderer.addClass(document.body, 'admin-sidebar-minimal');
        } else {
            this.renderer.removeClass(document.body, 'admin-sidebar-minimal');
        }
        this.isSidebarCollapsed = !this.isSidebarCollapsed;
    }

    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.loginService.logout();
        this.router.navigate(['']);
    }


    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

}
