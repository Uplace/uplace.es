import {Component, Input} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Request} from "../../../shared/request/request.model";

@Component({
    selector: 'request-popup',
    templateUrl: './request-popup.component.html'
})

export class RequestPopupComponent {
    @Input() request: Request;

    constructor(public activeModal: NgbActiveModal) { }
}
