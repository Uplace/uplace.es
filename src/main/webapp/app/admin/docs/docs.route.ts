import { Route } from '@angular/router';

import { UpDocsComponent } from './docs.component';

export const docsRoute: Route = {
    path: 'docs',
    component: UpDocsComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
