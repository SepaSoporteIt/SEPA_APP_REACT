import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LocalidadAndPartido from './localidad-and-partido';
import LocalidadAndPartidoDetail from './localidad-and-partido-detail';
import LocalidadAndPartidoUpdate from './localidad-and-partido-update';
import LocalidadAndPartidoDeleteDialog from './localidad-and-partido-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LocalidadAndPartidoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LocalidadAndPartidoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LocalidadAndPartidoDetail} />
      <ErrorBoundaryRoute path={match.url} component={LocalidadAndPartido} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LocalidadAndPartidoDeleteDialog} />
  </>
);

export default Routes;
