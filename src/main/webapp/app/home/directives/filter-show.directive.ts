import {Directive, HostBinding, OnInit, ViewContainerRef} from '@angular/core';

@Directive({
  selector: 'FilterShow'
})
export class FilterShowDirective implements OnInit {

    @HostBinding('class.results-collapsed') isActive = false;


  toggle() {
      this.isActive = !this.isActive;
  }

  ngOnInit(): void {
      console.log('Directive is ' + this.isActive);
  }
}
