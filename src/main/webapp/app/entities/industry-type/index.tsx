import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IndustryType from './industry-type';
import IndustryTypeDetail from './industry-type-detail';
import IndustryTypeUpdate from './industry-type-update';
import IndustryTypeDeleteDialog from './industry-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IndustryTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IndustryTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IndustryTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={IndustryType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IndustryTypeDeleteDialog} />
  </>
);

export default Routes;
