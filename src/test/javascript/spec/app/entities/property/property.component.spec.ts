/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { UplaceTestModule } from '../../../test.module';
import { PropertyComponent } from '../../../../../../main/webapp/app/entities/property/property.component';
import { PropertyService } from '../../../../../../main/webapp/app/entities/property/property.service';
import { Property } from '../../../../../../main/webapp/app/entities/property/property.model';

describe('Component Tests', () => {

    describe('Property Management Component', () => {
        let comp: PropertyComponent;
        let fixture: ComponentFixture<PropertyComponent>;
        let service: PropertyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [PropertyComponent],
                providers: [
                    PropertyService
                ]
            })
            .overrideTemplate(PropertyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PropertyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Property(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.properties[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
