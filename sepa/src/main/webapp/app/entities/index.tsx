import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Company from './company';
import IndustryType from './industry-type';
import Employee from './employee';
import Expiration from './expiration';
import Study from './study';
import Localidadandpartido from './localidadandpartido';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}company`} component={Company} />
      <ErrorBoundaryRoute path={`${match.url}industry-type`} component={IndustryType} />
      <ErrorBoundaryRoute path={`${match.url}employee`} component={Employee} />
      <ErrorBoundaryRoute path={`${match.url}expiration`} component={Expiration} />
      <ErrorBoundaryRoute path={`${match.url}study`} component={Study} />
      <ErrorBoundaryRoute path={`${match.url}localidadandpartido`} component={Localidadandpartido} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
