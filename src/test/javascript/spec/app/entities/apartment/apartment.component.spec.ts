/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UplaceTestModule } from '../../../test.module';
import { ApartmentComponent } from '../../../../../../main/webapp/app/entities/apartment/apartment.component';
import { ApartmentService } from '../../../../../../main/webapp/app/entities/apartment/apartment.service';
import { Apartment } from '../../../../../../main/webapp/app/entities/apartment/apartment.model';

describe('Component Tests', () => {

    describe('Apartment Management Component', () => {
        let comp: ApartmentComponent;
        let fixture: ComponentFixture<ApartmentComponent>;
        let service: ApartmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [ApartmentComponent],
                providers: [
                    ApartmentService
                ]
            })
            .overrideTemplate(ApartmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApartmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApartmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Apartment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.apartments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
