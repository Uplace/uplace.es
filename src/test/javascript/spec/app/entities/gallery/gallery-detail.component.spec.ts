/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { GalleryDetailComponent } from '../../../../../../main/webapp/app/entities/gallery/gallery-detail.component';
import { GalleryService } from '../../../../../../main/webapp/app/entities/gallery/gallery.service';
import { Gallery } from '../../../../../../main/webapp/app/entities/gallery/gallery.model';

describe('Component Tests', () => {

    describe('Gallery Management Detail Component', () => {
        let comp: GalleryDetailComponent;
        let fixture: ComponentFixture<GalleryDetailComponent>;
        let service: GalleryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [GalleryDetailComponent],
                providers: [
                    GalleryService
                ]
            })
            .overrideTemplate(GalleryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GalleryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GalleryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Gallery(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.gallery).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
