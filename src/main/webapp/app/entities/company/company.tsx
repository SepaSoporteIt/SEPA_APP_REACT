import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICompanyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Company = (props: ICompanyProps) => {
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

  const { companyList, match, loading } = props;
  return (
    <div>
      <h2 id="company-heading">
        Companies
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Company
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
          {companyList && companyList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    Email <FontAwesomeIcon icon="sort" />
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
                  <th className="hand" onClick={sort('cuit')}>
                    Cuit <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isSubscripted')}>
                    Is Subscripted <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fantasyName')}>
                    Fantasy Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('postalCode')}>
                    Postal Code <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tlf')}>
                    Tlf <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('internalTlf')}>
                    Internal Tlf <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('contact')}>
                    Contact <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cellphone')}>
                    Cellphone <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('visitsQtyMin')}>
                    Visits Qty Min <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('visitsQtyMax')}>
                    Visits Qty Max <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('habPrim')}>
                    Hab Prim <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('habSec')}>
                    Hab Sec <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('comment')}>
                    Comment <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('legislationId')}>
                    Legislation Id <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('solicitadorId')}>
                    Solicitador Id <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('ambitoId')}>
                    Ambito Id <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('autoridadId')}>
                    Autoridad Id <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('frecuencyTypeId')}>
                    Frecuency Type Id <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdAt')}>
                    Created At <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedAt')}>
                    Updated At <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Employee <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Prim Industry Tipe <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Sec Industry Tipe <FontAwesomeIcon icon="sort" />
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
                    <td>{company.legislationId}</td>
                    <td>{company.solicitadorId}</td>
                    <td>{company.ambitoId}</td>
                    <td>{company.autoridadId}</td>
                    <td>{company.frecuencyTypeId}</td>
                    <td>{company.createdAt ? <TextFormat type="date" value={company.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{company.updatedAt ? <TextFormat type="date" value={company.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{company.employee ? <Link to={`employee/${company.employee.id}`}>{company.employee.name}</Link> : ''}</td>
                    <td>
                      {company.primIndustryTipe ? (
                        <Link to={`industry-type/${company.primIndustryTipe.id}`}>{company.primIndustryTipe.ciiu}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {company.secIndustryTipe ? (
                        <Link to={`industry-type/${company.secIndustryTipe.id}`}>{company.secIndustryTipe.ciiu}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${company.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${company.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${company.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Companies found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ company }: IRootState) => ({
  companyList: company.entities,
  loading: company.loading,
  totalItems: company.totalItems,
  links: company.links,
  entity: company.entity,
  updateSuccess: company.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Company);
