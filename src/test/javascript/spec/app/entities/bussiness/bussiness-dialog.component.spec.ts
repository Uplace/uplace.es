/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { UplaceTestModule } from '../../../test.module';
import { BussinessDialogComponent } from '../../../../../../main/webapp/app/entities/bussiness/bussiness-dialog.component';
import { BussinessService } from '../../../../../../main/webapp/app/entities/bussiness/bussiness.service';
import { Bussiness } from '../../../../../../main/webapp/app/entities/bussiness/bussiness.model';

describe('Component Tests', () => {

    describe('Bussiness Management Dialog Component', () => {
        let comp: BussinessDialogComponent;
        let fixture: ComponentFixture<BussinessDialogComponent>;
        let service: BussinessService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [BussinessDialogComponent],
                providers: [
                    BussinessService
                ]
            })
            .overrideTemplate(BussinessDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BussinessDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BussinessService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Bussiness(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.bussiness = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bussinessListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Bussiness();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.bussiness = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bussinessListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
