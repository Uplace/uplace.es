import {Component, Input} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'request-popup',
    templateUrl: './request-popup.component.html'
})

export class RequestPopupComponent {
    @Input() request: Request;

    constructor(private activeModal: NgbActiveModal) { }
}
