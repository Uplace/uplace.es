/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { ParkingDetailComponent } from '../../../../../../main/webapp/app/entities/parking/parking-detail.component';
import { ParkingService } from '../../../../../../main/webapp/app/entities/parking/parking.service';
import { Parking } from '../../../../../../main/webapp/app/entities/parking/parking.model';

describe('Component Tests', () => {

    describe('Parking Management Detail Component', () => {
        let comp: ParkingDetailComponent;
        let fixture: ComponentFixture<ParkingDetailComponent>;
        let service: ParkingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [ParkingDetailComponent],
                providers: [
                    ParkingService
                ]
            })
            .overrideTemplate(ParkingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParkingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParkingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Parking(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.parking).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
