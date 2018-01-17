/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { UplaceTestModule } from '../../../test.module';
import { GalleryComponent } from '../../../../../../main/webapp/app/entities/gallery/gallery.component';
import { GalleryService } from '../../../../../../main/webapp/app/entities/gallery/gallery.service';
import { Gallery } from '../../../../../../main/webapp/app/entities/gallery/gallery.model';

describe('Component Tests', () => {

    describe('Gallery Management Component', () => {
        let comp: GalleryComponent;
        let fixture: ComponentFixture<GalleryComponent>;
        let service: GalleryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [GalleryComponent],
                providers: [
                    GalleryService
                ]
            })
            .overrideTemplate(GalleryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GalleryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GalleryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Gallery(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.galleries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
