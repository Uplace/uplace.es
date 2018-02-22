/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { ApartmentDetailComponent } from '../../../../../../main/webapp/app/entities/apartment/apartment-detail.component';
import { ApartmentService } from '../../../../../../main/webapp/app/entities/apartment/apartment.service';
import { Apartment } from '../../../../../../main/webapp/app/entities/apartment/apartment.model';

describe('Component Tests', () => {

    describe('Apartment Management Detail Component', () => {
        let comp: ApartmentDetailComponent;
        let fixture: ComponentFixture<ApartmentDetailComponent>;
        let service: ApartmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [ApartmentDetailComponent],
                providers: [
                    ApartmentService
                ]
            })
            .overrideTemplate(ApartmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApartmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApartmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Apartment(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.apartment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
