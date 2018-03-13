import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Property} from "../../../../entities/property";

@Component({
    selector: 'up-property-modal',
    templateUrl: 'property-modal.component.html'
})
export class PropertyModalComponent implements OnInit {

    @Input() properties: Property[];

    constructor(public activeModal: NgbActiveModal) { }

    ngOnInit() {

    }


}
