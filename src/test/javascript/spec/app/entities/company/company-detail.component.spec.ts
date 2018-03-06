/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { CompanyDetailComponent } from '../../../../../../main/webapp/app/entities/company/company-detail.component';
import { CompanyService } from '../../../../../../main/webapp/app/entities/company/company.service';
import { Company } from '../../../../../../main/webapp/app/entities/company/company.model';

describe('Component Tests', () => {

    describe('Company Management Detail Component', () => {
        let comp: CompanyDetailComponent;
        let fixture: ComponentFixture<CompanyDetailComponent>;
        let service: CompanyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [CompanyDetailComponent],
                providers: [
                    CompanyService
                ]
            })
            .overrideTemplate(CompanyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Company(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.company).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
