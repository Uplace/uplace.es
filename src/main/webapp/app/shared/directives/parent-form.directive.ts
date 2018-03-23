import {ControlContainer, NgForm} from "@angular/forms";
import {Directive} from "@angular/core";

@Directive({
    selector: '[provide-parent-form]',
    providers: [
        {
            provide: ControlContainer,
            useFactory: function(form: NgForm) {
                console.log(form);
                return form;
            },
            deps: [NgForm]
        }
    ]
})
export class ProvideParentForm {}
