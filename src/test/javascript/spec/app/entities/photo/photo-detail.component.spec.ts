/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { PhotoDetailComponent } from '../../../../../../main/webapp/app/entities/photo/photo-detail.component';
import { PhotoService } from '../../../../../../main/webapp/app/entities/photo/photo.service';
import { Photo } from '../../../../../../main/webapp/app/entities/photo/photo.model';

describe('Component Tests', () => {

    describe('Photo Management Detail Component', () => {
        let comp: PhotoDetailComponent;
        let fixture: ComponentFixture<PhotoDetailComponent>;
        let service: PhotoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [PhotoDetailComponent],
                providers: [
                    PhotoService
                ]
            })
            .overrideTemplate(PhotoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PhotoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhotoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Photo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.photo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
