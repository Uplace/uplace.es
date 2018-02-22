import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { IndustrialPlant } from './industrial-plant.model';
import { IndustrialPlantService } from './industrial-plant.service';

@Injectable()
export class IndustrialPlantPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private industrialPlantService: IndustrialPlantService

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
                this.industrialPlantService.find(id)
                    .subscribe((industrialPlantResponse: HttpResponse<IndustrialPlant>) => {
                        const industrialPlant: IndustrialPlant = industrialPlantResponse.body;
                        this.ngbModalRef = this.industrialPlantModalRef(component, industrialPlant);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.industrialPlantModalRef(component, new IndustrialPlant());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    industrialPlantModalRef(component: Component, industrialPlant: IndustrialPlant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.industrialPlant = industrialPlant;
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
