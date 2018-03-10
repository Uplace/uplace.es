import {Component, ElementRef, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Photo} from '../../../../entities/photo';
import {JhiDataUtils} from 'ng-jhipster';

@Component({
    selector: 'up-property-photos',
    templateUrl: './property-photos.component.html',
    styleUrls: [
        './property-photos.component.css'
    ]
})

export class PropertyPhotosComponent implements OnInit {

    photo: Photo;

    @Input() photos: Photo[];

    @Output() photosChange: EventEmitter<Photo[]> = new EventEmitter<Photo[]>();

    constructor(
        private dataUtils: JhiDataUtils,
        private elementRef: ElementRef
    ) {
        this.photo = new Photo();
    }

    ngOnInit() {
        if (this.photos == null) {
            this.photos = <Photo[]>[];
        }
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
        this.photo.name = 'Test photo';
        this.photo.description = 'Test photo';
        this.photo.thumbnail = true;
        console.log(this.photo);
        this.photos.push(this.photo);
        this.photosChange.emit(this.photos);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.photo, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        console.log('Clear me');
    }

}
