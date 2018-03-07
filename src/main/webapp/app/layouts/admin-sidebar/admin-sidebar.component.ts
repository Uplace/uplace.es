import {Component, OnInit} from '@angular/core';
import {VERSION} from "../../app.constants";

@Component({
    selector: 'up-admin-sidebar',
    templateUrl: './admin-sidebar.component.html',
    styles: []
})

export class AdminSidebarComponent implements OnInit {

    version: string;

    constructor() {
        this.version = VERSION ? 'v' + VERSION : '';
    }

    ngOnInit() {

    }

}
