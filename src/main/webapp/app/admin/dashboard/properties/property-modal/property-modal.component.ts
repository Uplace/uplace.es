import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'up-property-modal',
    templateUrl: 'property-modal.component.html'
})
export class PropertyModalComponent implements OnInit {

    @Input() name;

    constructor(public activeModal: NgbActiveModal) { }

    ngOnInit() { }


}
