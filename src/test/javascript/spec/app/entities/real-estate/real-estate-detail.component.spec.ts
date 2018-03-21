/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { RealEstateDetailComponent } from '../../../../../../main/webapp/app/entities/real-estate/real-estate-detail.component';
import { RealEstateService } from '../../../../../../main/webapp/app/admin/dashboard/real-estate/real-estate.service';
import { RealEstate } from '../../../../../../main/webapp/app/entities/real-estate/real-estate.model';

describe('Component Tests', () => {

    describe('RealEstate Management Detail Component', () => {
        let comp: RealEstateDetailComponent;
        let fixture: ComponentFixture<RealEstateDetailComponent>;
        let service: RealEstateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [RealEstateDetailComponent],
                providers: [
                    RealEstateService
                ]
            })
            .overrideTemplate(RealEstateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RealEstateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RealEstateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RealEstate(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.realEstate).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
