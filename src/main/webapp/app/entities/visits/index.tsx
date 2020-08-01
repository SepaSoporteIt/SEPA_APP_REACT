import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Visits from './visits';
import VisitsDetail from './visits-detail';
import VisitsUpdate from './visits-update';
import VisitsDeleteDialog from './visits-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VisitsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VisitsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VisitsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Visits} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VisitsDeleteDialog} />
  </>
);

export default Routes;
