/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { HotelDetailComponent } from '../../../../../../main/webapp/app/entities/hotel/hotel-detail.component';
import { HotelService } from '../../../../../../main/webapp/app/entities/hotel/hotel.service';
import { Hotel } from '../../../../../../main/webapp/app/entities/hotel/hotel.model';

describe('Component Tests', () => {

    describe('Hotel Management Detail Component', () => {
        let comp: HotelDetailComponent;
        let fixture: ComponentFixture<HotelDetailComponent>;
        let service: HotelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [HotelDetailComponent],
                providers: [
                    HotelService
                ]
            })
            .overrideTemplate(HotelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HotelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HotelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Hotel(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hotel).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
