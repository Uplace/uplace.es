/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UplaceTestModule } from '../../../test.module';
import { RequestComponent } from '../../../../../../main/webapp/app/entities/request/request.component';
import { RequestService } from '../../../../../../main/webapp/app/entities/request/request.service';
import { Request } from '../../../../../../main/webapp/app/entities/request/request.model';

describe('Component Tests', () => {

    describe('Request Management Component', () => {
        let comp: RequestComponent;
        let fixture: ComponentFixture<RequestComponent>;
        let service: RequestService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [RequestComponent],
                providers: [
                    RequestService
                ]
            })
            .overrideTemplate(RequestComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Request(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.requests[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
