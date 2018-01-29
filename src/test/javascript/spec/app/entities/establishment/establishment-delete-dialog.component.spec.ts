/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { UplaceTestModule } from '../../../test.module';
import { EstablishmentDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/establishment/establishment-delete-dialog.component';
import { EstablishmentService } from '../../../../../../main/webapp/app/entities/establishment/establishment.service';

describe('Component Tests', () => {

    describe('Establishment Management Delete Component', () => {
        let comp: EstablishmentDeleteDialogComponent;
        let fixture: ComponentFixture<EstablishmentDeleteDialogComponent>;
        let service: EstablishmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [EstablishmentDeleteDialogComponent],
                providers: [
                    EstablishmentService
                ]
            })
            .overrideTemplate(EstablishmentDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstablishmentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstablishmentService);
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
