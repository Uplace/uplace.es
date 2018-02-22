/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { UplaceTestModule } from '../../../test.module';
import { PhotoDialogComponent } from '../../../../../../main/webapp/app/entities/photo/photo-dialog.component';
import { PhotoService } from '../../../../../../main/webapp/app/entities/photo/photo.service';
import { Photo } from '../../../../../../main/webapp/app/entities/photo/photo.model';
import { PropertyService } from '../../../../../../main/webapp/app/entities/property';

describe('Component Tests', () => {

    describe('Photo Management Dialog Component', () => {
        let comp: PhotoDialogComponent;
        let fixture: ComponentFixture<PhotoDialogComponent>;
        let service: PhotoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [PhotoDialogComponent],
                providers: [
                    PropertyService,
                    PhotoService
                ]
            })
            .overrideTemplate(PhotoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PhotoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhotoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Photo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.photo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'photoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Photo();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.photo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'photoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
