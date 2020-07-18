import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICompanyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Company = (props: ICompanyProps) => {
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

  const { companyList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="company-heading">
        <Translate contentKey="sepaApp.company.home.title">Companies</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="sepaApp.company.home.createLabel">Create new Company</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {companyList && companyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="sepaApp.company.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="sepaApp.company.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addressDirection')}>
                  <Translate contentKey="sepaApp.company.addressDirection">Address Direction</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addressNumber')}>
                  <Translate contentKey="sepaApp.company.addressNumber">Address Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('betweenStreets')}>
                  <Translate contentKey="sepaApp.company.betweenStreets">Between Streets</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('floor')}>
                  <Translate contentKey="sepaApp.company.floor">Floor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('departament')}>
                  <Translate contentKey="sepaApp.company.departament">Departament</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cuit')}>
                  <Translate contentKey="sepaApp.company.cuit">Cuit</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isSubscripted')}>
                  <Translate contentKey="sepaApp.company.isSubscripted">Is Subscripted</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fantasyName')}>
                  <Translate contentKey="sepaApp.company.fantasyName">Fantasy Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('postalCode')}>
                  <Translate contentKey="sepaApp.company.postalCode">Postal Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tlf')}>
                  <Translate contentKey="sepaApp.company.tlf">Tlf</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('internalTlf')}>
                  <Translate contentKey="sepaApp.company.internalTlf">Internal Tlf</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contact')}>
                  <Translate contentKey="sepaApp.company.contact">Contact</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cellphone')}>
                  <Translate contentKey="sepaApp.company.cellphone">Cellphone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('visitsQtyMin')}>
                  <Translate contentKey="sepaApp.company.visitsQtyMin">Visits Qty Min</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('visitsQtyMax')}>
                  <Translate contentKey="sepaApp.company.visitsQtyMax">Visits Qty Max</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('habPrim')}>
                  <Translate contentKey="sepaApp.company.habPrim">Hab Prim</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('habSec')}>
                  <Translate contentKey="sepaApp.company.habSec">Hab Sec</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comment')}>
                  <Translate contentKey="sepaApp.company.comment">Comment</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isDisabled')}>
                  <Translate contentKey="sepaApp.company.isDisabled">Is Disabled</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="sepaApp.company.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="sepaApp.company.industryType">Industry Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="sepaApp.company.secIndustryType">Sec Industry Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="sepaApp.company.localidadId">Localidad Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="sepaApp.company.partidoId">Partido Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {companyList.map((company, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${company.id}`} color="link" size="sm">
                      {company.id}
                    </Button>
                  </td>
                  <td>{company.name}</td>
                  <td>{company.email}</td>
                  <td>{company.addressDirection}</td>
                  <td>{company.addressNumber}</td>
                  <td>{company.betweenStreets}</td>
                  <td>{company.floor}</td>
                  <td>{company.departament}</td>
                  <td>{company.cuit}</td>
                  <td>{company.isSubscripted ? 'true' : 'false'}</td>
                  <td>{company.fantasyName}</td>
                  <td>{company.postalCode}</td>
                  <td>{company.tlf}</td>
                  <td>{company.internalTlf}</td>
                  <td>{company.contact}</td>
                  <td>{company.cellphone}</td>
                  <td>{company.visitsQtyMin}</td>
                  <td>{company.visitsQtyMax}</td>
                  <td>{company.habPrim}</td>
                  <td>{company.habSec}</td>
                  <td>{company.comment}</td>
                  <td>{company.isDisabled ? 'true' : 'false'}</td>
                  <td>{company.employee ? <Link to={`employee/${company.employee.id}`}>{company.employee.id}</Link> : ''}</td>
                  <td>
                    {company.industryType ? <Link to={`industry-type/${company.industryType.id}`}>{company.industryType.id}</Link> : ''}
                  </td>
                  <td>
                    {company.secIndustryType ? (
                      <Link to={`industry-type/${company.secIndustryType.id}`}>{company.secIndustryType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {company.localidadId ? (
                      <Link to={`localidad-and-partido/${company.localidadId.id}`}>{company.localidadId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {company.partidoId ? <Link to={`localidad-and-partido/${company.partidoId.id}`}>{company.partidoId.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${company.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${company.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${company.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="sepaApp.company.home.notFound">No Companies found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={companyList && companyList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ company }: IRootState) => ({
  companyList: company.entities,
  loading: company.loading,
  totalItems: company.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Company);
