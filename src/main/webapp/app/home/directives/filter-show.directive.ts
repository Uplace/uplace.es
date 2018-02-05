import {Directive, ElementRef, HostBinding, OnInit} from '@angular/core';

@Directive({
  selector: 'FilterShow'
})
export class FilterShowDirective implements OnInit {

    @HostBinding('class.results-collapsed') isActive = false;

    constructor(element: ElementRef) {
        console.log(element);
    }

    toggle() {
        this.isActive = !this.isActive;
    }

    ngOnInit(): void {
        console.log('Directive is ' + this.isActive);
    }

}
