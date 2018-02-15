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
    latitude: number;
    longitude: number;

    slides = [
        {img: "http://placehold.it/350x150/000000"},
        {img: "http://placehold.it/350x150/111111"},
        {img: "http://placehold.it/350x150/333333"},
        {img: "http://placehold.it/350x150/666666"}
    ];
    slideConfig = {"slidesToShow": 4, "slidesToScroll": 4};


    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private http: HttpClient,
        private elementRef: ElementRef,
        private markersService: MarkerService
    ) { }

    ngOnInit(): void {
        this.getUserLocation();
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        this.markersService.query().subscribe((result) => {
            this.markers = result.json;
        });

    }

    private getUserLocation() {
        /// locate the user
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                this.latitude = position.coords.latitude;
                this.longitude = position.coords.longitude;
            });
        }
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
