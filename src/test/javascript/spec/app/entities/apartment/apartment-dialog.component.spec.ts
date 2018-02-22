/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { UplaceTestModule } from '../../../test.module';
import { ApartmentDialogComponent } from '../../../../../../main/webapp/app/entities/apartment/apartment-dialog.component';
import { ApartmentService } from '../../../../../../main/webapp/app/entities/apartment/apartment.service';
import { Apartment } from '../../../../../../main/webapp/app/entities/apartment/apartment.model';

describe('Component Tests', () => {

    describe('Apartment Management Dialog Component', () => {
        let comp: ApartmentDialogComponent;
        let fixture: ComponentFixture<ApartmentDialogComponent>;
        let service: ApartmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [ApartmentDialogComponent],
                providers: [
                    ApartmentService
                ]
            })
            .overrideTemplate(ApartmentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApartmentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApartmentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Apartment(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.apartment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'apartmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Apartment();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.apartment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'apartmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
