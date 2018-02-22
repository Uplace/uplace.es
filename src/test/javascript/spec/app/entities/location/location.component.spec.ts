/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UplaceTestModule } from '../../../test.module';
import { LocationComponent } from '../../../../../../main/webapp/app/entities/location/location.component';
import { LocationService } from '../../../../../../main/webapp/app/entities/location/location.service';
import { Location } from '../../../../../../main/webapp/app/entities/location/location.model';

describe('Component Tests', () => {

    describe('Location Management Component', () => {
        let comp: LocationComponent;
        let fixture: ComponentFixture<LocationComponent>;
        let service: LocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [LocationComponent],
                providers: [
                    LocationService
                ]
            })
            .overrideTemplate(LocationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Location(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.locations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
