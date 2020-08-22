import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './expiration.reducer';
import { IExpiration } from 'app/shared/model/expiration.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IExpirationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Expiration = (props: IExpirationProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { expirationList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="expiration-heading">
        <Translate contentKey="sepaApp.expiration.home.title">Expirations</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="sepaApp.expiration.home.createLabel">Create new Expiration</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {expirationList && expirationList.length > 0 ? (
          <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('uniqueCode')}>
                    <Translate contentKey="sepaApp.expiration.uniqueCode">Unique Code</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="sepaApp.expiration.company">Company</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('responsible')}>
                    <Translate contentKey="sepaApp.expiration.responsible">Responsible</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="sepaApp.expiration.study">Study</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    <Translate contentKey="sepaApp.expiration.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startDate')}>
                    <Translate contentKey="sepaApp.expiration.startDate">Start Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endDate')}>
                    <Translate contentKey="sepaApp.expiration.endDate">End Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isCompleted')}>
                    <Translate contentKey="sepaApp.expiration.isCompleted">Is Completed</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                   <th>
                    <Translate contentKey="sepaApp.expiration.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
            <tbody>
              {expirationList.map((expiration, i) => (
                <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${expiration.id}`} color="link" size="sm">
                        {expiration.uniqueCode}
                      </Button>
                    </td>
                    <td>
                      {expiration.company ? <Link to={`company/${expiration.company.id}`}>{expiration.company.name + " - " + expiration.company.addressDirection + " " + expiration.company.addressNumber}</Link> : <Translate contentKey="sepaApp.expiration.errors.noCompany">No Company Assigned</Translate>}
                    </td>
                    <td>
                      {expiration.responsible ? expiration.responsible : expiration.company ? <Translate contentKey="sepaApp.expiration.errors.noResponsible">No Employee Assigned in the selected Company</Translate> : <Translate contentKey="sepaApp.expiration.errors.noCompany">No Company Assigned</Translate>}
                    </td>
                    <td>{expiration.study ? <Link to={`study/${expiration.study.id}`}>{expiration.study.name}</Link> : ''}</td>
                    <td>
                      <Translate contentKey={`sepaApp.Status.${expiration.status}`} />
                    </td>
                    <td>
                      <TextFormat type="date" value={expiration.startDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={expiration.endDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{expiration.isCompleted ? <Translate contentKey="sepaApp.expiration.booleans.true">true</Translate> : <Translate contentKey="sepaApp.expiration.booleans.false">false</Translate>}</td>
                    <td>
                      {expiration.employee ? <Link to={`employee/${expiration.employee.id}`}>{expiration.employee.name + " " + expiration.employee.surname}</Link> : <Translate contentKey="sepaApp.expiration.errors.noEmployee">No Employee Assigned</Translate>}
                    </td>
                    <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${expiration.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${expiration.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${expiration.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="sepaApp.expiration.home.notFound">No Expirations found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={expirationList && expirationList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ expiration }: IRootState) => ({
  expirationList: expiration.entities,
  loading: expiration.loading,
  totalItems: expiration.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Expiration);
