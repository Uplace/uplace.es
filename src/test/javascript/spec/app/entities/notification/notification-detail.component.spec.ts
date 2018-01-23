/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { NotificationDetailComponent } from '../../../../../../main/webapp/app/entities/notification/notification-detail.component';
import { NotificationService } from '../../../../../../main/webapp/app/entities/notification/notification.service';
import { Notification } from '../../../../../../main/webapp/app/entities/notification/notification.model';

describe('Component Tests', () => {

    describe('Notification Management Detail Component', () => {
        let comp: NotificationDetailComponent;
        let fixture: ComponentFixture<NotificationDetailComponent>;
        let service: NotificationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [NotificationDetailComponent],
                providers: [
                    NotificationService
                ]
            })
            .overrideTemplate(NotificationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Notification(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.notification).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
