import { Route } from '@angular/router';

import { UpMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'up-metrics',
    component: UpMetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title'
    }
};
