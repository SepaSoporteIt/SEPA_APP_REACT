import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Localidadandpartido from './localidadandpartido';
import LocalidadandpartidoDetail from './localidadandpartido-detail';
import LocalidadandpartidoUpdate from './localidadandpartido-update';
import LocalidadandpartidoDeleteDialog from './localidadandpartido-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LocalidadandpartidoDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LocalidadandpartidoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LocalidadandpartidoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LocalidadandpartidoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Localidadandpartido} />
    </Switch>
  </>
);

export default Routes;
