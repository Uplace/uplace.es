/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { UplaceTestModule } from '../../../test.module';
import { PropertyDialogComponent } from '../../../../../../main/webapp/app/entities/property/property-dialog.component';
import { PropertyService } from '../../../../../../main/webapp/app/entities/property/property.service';
import { Property } from '../../../../../../main/webapp/app/entities/property/property.model';
import { GalleryService } from '../../../../../../main/webapp/app/entities/gallery';

describe('Component Tests', () => {

    describe('Property Management Dialog Component', () => {
        let comp: PropertyDialogComponent;
        let fixture: ComponentFixture<PropertyDialogComponent>;
        let service: PropertyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [PropertyDialogComponent],
                providers: [
                    GalleryService,
                    PropertyService
                ]
            })
            .overrideTemplate(PropertyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PropertyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Property(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.property = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'propertyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Property();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.property = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'propertyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
