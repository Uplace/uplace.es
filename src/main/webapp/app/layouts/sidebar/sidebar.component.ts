import {Component, OnInit, Renderer2} from '@angular/core';
import {LoginService} from '../../shared';
import {Router} from '@angular/router';

@Component({
    selector: 'up-sidebar',
    templateUrl: './sidebar.component.html',
    styles: []
})
export class SidebarComponent implements OnInit {

    isSidebarCollapsed: boolean;

    constructor(
        private loginService: LoginService,
        private router: Router,
        private renderer: Renderer2
    ) {
    }

    ngOnInit() {
    }

    logout() {
        this.collapseSidebar();
        this.loginService.logout();
        this.router.navigate(['/']);
    }

    collapseSidebar() {
        if (this.isSidebarCollapsed) {
            this.renderer.addClass(document.body, 'side-open');
        } else {
            this.renderer.removeClass(document.body, 'side-open');
        }
        this.isSidebarCollapsed = !this.isSidebarCollapsed;
    }

}
