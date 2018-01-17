import { Route } from '@angular/router';

import { UpConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'up-configuration',
    component: UpConfigurationComponent,
    data: {
        pageTitle: 'configuration.title'
    }
};
