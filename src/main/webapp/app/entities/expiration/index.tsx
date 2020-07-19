import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Expiration from './expiration';
import ExpirationDetail from './expiration-detail';
import ExpirationUpdate from './expiration-update';
import ExpirationDeleteDialog from './expiration-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExpirationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExpirationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExpirationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Expiration} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ExpirationDeleteDialog} />
  </>
);

export default Routes;
