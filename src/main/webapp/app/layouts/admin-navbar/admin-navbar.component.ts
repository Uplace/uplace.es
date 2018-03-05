import {Component, OnInit, Renderer2} from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { ProfileService } from '../profiles/profile.service';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from '../../shared';

import { VERSION } from '../../app.constants';

@Component({
    selector: 'up-admin-navbar',
    templateUrl: './admin-navbar.component.html',
})
export class AdminNavbarComponent implements OnInit {


    constructor(

    ) {

    }

    ngOnInit() {

    }

}
