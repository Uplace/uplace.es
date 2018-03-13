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

    @Input() photos: Photo[];

    @Output() photosChange: EventEmitter<Photo[]> = new EventEmitter<Photo[]>();

    public uploader: FileUploader = new FileUploader({url: ''});
    public hasBaseDropZoneOver = false;

    constructor() { }

    public fileOverBase(e: any): void {
        this.hasBaseDropZoneOver = e;
    }

    ngOnInit() {

        if (this.photos == null) {
            this.photos = <Photo[]>[];
        }

        this.uploader.onAfterAddingFile = (item) => {
            const reader = new FileReader();

            var photo: Photo = new Photo();

            photo.photoContentType = item._file.type;
            photo.name = item._file.name;
            photo.description = item._file.name;

            reader.onload =  () => {

                let imageData = reader.result;

                let rawData = imageData.split('base64,');

                if (rawData.length > 1) {

                    photo.photo = rawData[1];

                    this.photos.push(photo);
                    this.photosChange.emit(this.photos);
                    console.log(this.photos);
                }
            };
            reader.readAsDataURL(item._file);
        };

    }
    deletePhoto(photo: Photo) {
        const index: number = this.photos.indexOf(photo);
        this.photos.splice(index, 1);
    }

}
