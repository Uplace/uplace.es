/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { UplaceTestModule } from '../../../test.module';
import { ParkingComponent } from '../../../../../../main/webapp/app/entities/parking/parking.component';
import { ParkingService } from '../../../../../../main/webapp/app/entities/parking/parking.service';
import { Parking } from '../../../../../../main/webapp/app/entities/parking/parking.model';

describe('Component Tests', () => {

    describe('Parking Management Component', () => {
        let comp: ParkingComponent;
        let fixture: ComponentFixture<ParkingComponent>;
        let service: ParkingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [ParkingComponent],
                providers: [
                    ParkingService
                ]
            })
            .overrideTemplate(ParkingComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParkingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParkingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Parking(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.parkings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
