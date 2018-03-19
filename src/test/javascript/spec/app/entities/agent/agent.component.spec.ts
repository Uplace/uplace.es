/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UplaceTestModule } from '../../../test.module';
import { AgentComponent } from '../../../../../../main/webapp/app/entities/agent/agent.component';
import { AgentService } from '../../../../../../main/webapp/app/entities/agent/agent.service';
import { Agent } from '../../../../../../main/webapp/app/entities/agent/agent.model';

describe('Component Tests', () => {

    describe('Agent Management Component', () => {
        let comp: AgentComponent;
        let fixture: ComponentFixture<AgentComponent>;
        let service: AgentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [AgentComponent],
                providers: [
                    AgentService
                ]
            })
            .overrideTemplate(AgentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Agent(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.agents[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
