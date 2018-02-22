/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { PropertyDetailComponent } from '../../../../../../main/webapp/app/entities/property/property-detail.component';
import { PropertyService } from '../../../../../../main/webapp/app/entities/property/property.service';
import { Property } from '../../../../../../main/webapp/app/entities/property/property.model';

describe('Component Tests', () => {

    describe('Property Management Detail Component', () => {
        let comp: PropertyDetailComponent;
        let fixture: ComponentFixture<PropertyDetailComponent>;
        let service: PropertyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [PropertyDetailComponent],
                providers: [
                    PropertyService
                ]
            })
            .overrideTemplate(PropertyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PropertyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Property(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.property).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
