/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { UplaceTestModule } from '../../../test.module';
import { OfficeComponent } from '../../../../../../main/webapp/app/entities/office/office.component';
import { OfficeService } from '../../../../../../main/webapp/app/entities/office/office.service';
import { Office } from '../../../../../../main/webapp/app/entities/office/office.model';

describe('Component Tests', () => {

    describe('Office Management Component', () => {
        let comp: OfficeComponent;
        let fixture: ComponentFixture<OfficeComponent>;
        let service: OfficeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [OfficeComponent],
                providers: [
                    OfficeService
                ]
            })
            .overrideTemplate(OfficeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OfficeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OfficeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Office()],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.offices[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
