import {Component, ElementRef, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Photo} from '../../../../entities/photo';
import {JhiDataUtils} from 'ng-jhipster';
import {FileUploader} from 'ng2-file-upload';

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

    public uploader: FileUploader = new FileUploader({url: ''});
    public hasBaseDropZoneOver: boolean = false;

    public fileOverBase(e: any): void {
        this.hasBaseDropZoneOver = e;
        if (!this.hasBaseDropZoneOver) {
            let fileCount: number = this.uploader.queue.length;
            if (fileCount > 0) {

                this.uploader.queue.forEach((val, i, array) => {

                    let fileReader = new FileReader();
                    fileReader.onloadend = (e) => {

                        let imageData = fileReader.result;
                        let rawData = imageData.split("base64,");

                        if (rawData.length > 1) {

                            rawData = rawData[1];
                            this.photos.push(new Photo(null, 'Test Photo', 'Test photo', 'image/jpeg', rawData, true, null));
                            this.photosChange.emit(this.photos);
                        }
                    };
                    fileReader.readAsDataURL(val._file);

                });
            }
        }
    }

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
        console.log(event);
        console.log(entity);
        console.log(field);
        console.log(isImage);
        this.dataUtils.setFileData(event, entity, field, isImage);
        this.photo.name = 'Test photo';
        this.photo.description = 'Test photo';
        this.photo.thumbnail = true;
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
