/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { UplaceTestModule } from '../../../test.module';
import { IndustrialPlantDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/industrial-plant/industrial-plant-delete-dialog.component';
import { IndustrialPlantService } from '../../../../../../main/webapp/app/entities/industrial-plant/industrial-plant.service';

describe('Component Tests', () => {

    describe('IndustrialPlant Management Delete Component', () => {
        let comp: IndustrialPlantDeleteDialogComponent;
        let fixture: ComponentFixture<IndustrialPlantDeleteDialogComponent>;
        let service: IndustrialPlantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [IndustrialPlantDeleteDialogComponent],
                providers: [
                    IndustrialPlantService
                ]
            })
            .overrideTemplate(IndustrialPlantDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustrialPlantDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustrialPlantService);
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
