import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IEmployeeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Employee = (props: IEmployeeProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const resetAll = () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      resetAll();
    }
  }, [props.updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
    setSorting(true);
  };

  const { employeeList, match, loading } = props;
  return (
    <div>
      <h2 id="employee-heading">
        Employees
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Employee
        </Link>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < props.links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {employeeList && employeeList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('surname')}>
                    Surname <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    Email <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tlf')}>
                    Tlf <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isInternal')}>
                    Is Internal <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('matNumber')}>
                    Mat Number <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cuit')}>
                    Cuit <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addressDirection')}>
                    Address Direction <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addressNumber')}>
                    Address Number <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('floor')}>
                    Floor <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('departament')}>
                    Departament <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('degree')}>
                    Degree <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('localidad')}>
                    Localidad <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('partido')}>
                    Partido <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('especializacion')}>
                    Especializacion <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('celular')}>
                    Celular <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('comentario')}>
                    Comentario <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdAt')}>
                    Created At <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedAt')}>
                    Updated At <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {employeeList.map((employee, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${employee.id}`} color="link" size="sm">
                        {employee.id}
                      </Button>
                    </td>
                    <td>{employee.name}</td>
                    <td>{employee.surname}</td>
                    <td>{employee.email}</td>
                    <td>{employee.tlf}</td>
                    <td>{employee.isInternal ? 'true' : 'false'}</td>
                    <td>{employee.matNumber}</td>
                    <td>{employee.cuit}</td>
                    <td>{employee.addressDirection}</td>
                    <td>{employee.addressNumber}</td>
                    <td>{employee.floor}</td>
                    <td>{employee.departament}</td>
                    <td>{employee.degree}</td>
                    <td>{employee.localidad}</td>
                    <td>{employee.partido}</td>
                    <td>{employee.especializacion}</td>
                    <td>{employee.celular}</td>
                    <td>{employee.comentario}</td>
                    <td>{employee.createdAt ? <TextFormat type="date" value={employee.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{employee.updatedAt ? <TextFormat type="date" value={employee.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${employee.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${employee.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${employee.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Employees found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ employee }: IRootState) => ({
  employeeList: employee.entities,
  loading: employee.loading,
  totalItems: employee.totalItems,
  links: employee.links,
  entity: employee.entity,
  updateSuccess: employee.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Employee);
