import {Component, OnInit, ViewChild} from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Account, LoginModalService, Principal } from '../shared';
import { AgmMap } from '@agm/core';
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'up-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})

export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    markers: any;


    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private http: HttpClient
    ) { }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.http.get('http://uplace.es/content/data.json').subscribe((result) => {
            this.markers = result;
            console.log(result);
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
