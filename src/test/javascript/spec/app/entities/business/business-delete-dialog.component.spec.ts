/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { UplaceTestModule } from '../../../test.module';
import { BusinessDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/business/business-delete-dialog.component';
import { BusinessService } from '../../../../../../main/webapp/app/entities/business/business.service';

describe('Component Tests', () => {

    describe('Business Management Delete Component', () => {
        let comp: BusinessDeleteDialogComponent;
        let fixture: ComponentFixture<BusinessDeleteDialogComponent>;
        let service: BusinessService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [BusinessDeleteDialogComponent],
                providers: [
                    BusinessService
                ]
            })
            .overrideTemplate(BusinessDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BusinessDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BusinessService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
