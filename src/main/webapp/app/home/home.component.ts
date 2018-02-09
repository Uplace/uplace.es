import {Component, ElementRef, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Account, LoginModalService, Principal } from '../shared';
import {HttpClient} from '@angular/common/http';
import {AgmMap, MapTypeStyle} from '@agm/core';
import {MarkerModel} from '../entities/marker/marker.model';
import {MarkerService} from "../entities/marker/marker.service";

@Component({
    selector: 'up-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ],
    encapsulation: ViewEncapsulation.None,
    providers: [
        MarkerService
    ]

})

export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    markers: MarkerModel[] = [];
    @ViewChild("map") map: AgmMap;
    customStyle: MapTypeStyle[];
    latitude;
    longitude;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private http: HttpClient,
        private elementRef: ElementRef,
        private markersService: MarkerService
    ) { }

    ngOnInit() {

        this.getUserLocation();
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        //this.map.mapReady.subscribe(() => {
            this.markersService.query().subscribe((result) => {
                this.markers = result.json;
               // this.map.triggerResize();
            });
        // });

        this.customStyle = [{
            'featureType': 'administrative',
            'elementType': 'labels.text.fill',
            'stylers': [{'color': '#c6c6c6'}]
        }, {'featureType': 'landscape', 'elementType': 'all', 'stylers': [{'color': '#f2f2f2'}]}, {
            'featureType': 'poi',
            'elementType': 'all',
            'stylers': [{'visibility': 'off'}]
        }, {
            'featureType': 'road',
            'elementType': 'all',
            'stylers': [{'saturation': -100}, {'lightness': 45}]
        }, {
            'featureType': 'road.highway',
            'elementType': 'all',
            'stylers': [{'visibility': 'simplified'}]
        }, {
            'featureType': 'road.highway',
            'elementType': 'geometry.fill',
            'stylers': [{'color': '#ffffff'}]
        }, {
            'featureType': 'road.arterial',
            'elementType': 'labels.icon',
            'stylers': [{'visibility': 'off'}]
        }, {
            'featureType': 'transit',
            'elementType': 'all',
            'stylers': [{'visibility': 'off'}]
        }, {'featureType': 'water', 'elementType': 'all', 'stylers': [{'color': '#dde6e8'}, {'visibility': 'on'}]}]
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
