import {Component, ElementRef, OnInit} from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Account, LoginModalService, Principal } from '../shared';
import {HttpClient} from '@angular/common/http';
import {AgmMap, MapTypeStyle} from '@agm/core';
import {MarkerModel} from '../entities/marker/marker.model';
import {MarkerService} from '../entities/marker/marker.service';

@Component({
    selector: 'up-home',
    templateUrl: './home.component.html',
    providers: [MarkerService]

})

export class HomeComponent implements OnInit {

    account: Account;
    modalRef: NgbModalRef;
    markers: MarkerModel[] = [];

    filterOpen;

    horizontalResults = false;


    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private http: HttpClient,
        private elementRef: ElementRef,
        private markersService: MarkerService
    ) { }

    ngOnInit(): void {
        // this.getUserLocation();
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        this.markersService.query().subscribe((result) => {
            this.markers = result.json;
        });

    }

    /*private getUserLocation() {
        /// locate the user
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                this.latitude = position.coords.latitude;
                this.longitude = position.coords.longitude;
            });
        }
    }*/

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    toggleOrientationHorizontal(value: boolean) {
        this.horizontalResults = value;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
