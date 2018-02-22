/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UplaceTestModule } from '../../../test.module';
import { HotelComponent } from '../../../../../../main/webapp/app/entities/hotel/hotel.component';
import { HotelService } from '../../../../../../main/webapp/app/entities/hotel/hotel.service';
import { Hotel } from '../../../../../../main/webapp/app/entities/hotel/hotel.model';

describe('Component Tests', () => {

    describe('Hotel Management Component', () => {
        let comp: HotelComponent;
        let fixture: ComponentFixture<HotelComponent>;
        let service: HotelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [HotelComponent],
                providers: [
                    HotelService
                ]
            })
            .overrideTemplate(HotelComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HotelComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HotelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Hotel(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hotels[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
