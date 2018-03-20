/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UplaceTestModule } from '../../../test.module';
import { RealEstateComponent } from '../../../../../../main/webapp/app/entities/real-estate/real-estate.component';
import { RealEstateService } from '../../../../../../main/webapp/app/entities/real-estate/real-estate.service';
import { RealEstate } from '../../../../../../main/webapp/app/entities/real-estate/real-estate.model';

describe('Component Tests', () => {

    describe('RealEstate Management Component', () => {
        let comp: RealEstateComponent;
        let fixture: ComponentFixture<RealEstateComponent>;
        let service: RealEstateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [RealEstateComponent],
                providers: [
                    RealEstateService
                ]
            })
            .overrideTemplate(RealEstateComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RealEstateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RealEstateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RealEstate(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.realEstates[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
