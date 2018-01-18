/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { UplaceTestModule } from '../../../test.module';
import { GalleryDialogComponent } from '../../../../../../main/webapp/app/entities/gallery/gallery-dialog.component';
import { GalleryService } from '../../../../../../main/webapp/app/entities/gallery/gallery.service';
import { Gallery } from '../../../../../../main/webapp/app/entities/gallery/gallery.model';
import { PropertyService } from '../../../../../../main/webapp/app/entities/property';

describe('Component Tests', () => {

    describe('Gallery Management Dialog Component', () => {
        let comp: GalleryDialogComponent;
        let fixture: ComponentFixture<GalleryDialogComponent>;
        let service: GalleryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [GalleryDialogComponent],
                providers: [
                    PropertyService,
                    GalleryService
                ]
            })
            .overrideTemplate(GalleryDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GalleryDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GalleryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Gallery(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.gallery = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'galleryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Gallery();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.gallery = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'galleryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
