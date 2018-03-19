/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { LocationDetailComponent } from '../../../../../../main/webapp/app/entities/location/location-detail.component';
import { LocationService } from '../../../../../../main/webapp/app/entities/location/location.service';
import { Location } from '../../../../../../main/webapp/app/entities/location/location.model';

describe('Component Tests', () => {

    describe('Location Management Detail Component', () => {
        let comp: LocationDetailComponent;
        let fixture: ComponentFixture<LocationDetailComponent>;
        let service: LocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [LocationDetailComponent],
                providers: [
                    LocationService
                ]
            })
            .overrideTemplate(LocationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Location(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.location).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
