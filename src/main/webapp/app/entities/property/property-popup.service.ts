import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Property } from './property.model';
import { PropertyService } from './property.service';

@Injectable()
export class PropertyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private propertyService: PropertyService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.propertyService.find(id)
                    .subscribe((propertyResponse: HttpResponse<Property>) => {
                        const property: Property = propertyResponse.body;
                        property.created = this.datePipe
                            .transform(property.created, 'yyyy-MM-ddTHH:mm:ss');
                        property.updated = this.datePipe
                            .transform(property.updated, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.propertyModalRef(component, property);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.propertyModalRef(component, new Property());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    propertyModalRef(component: Component, property: Property): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.property = property;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
