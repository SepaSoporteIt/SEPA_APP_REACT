import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import {
  byteSize,
  Translate,
  translate,
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ICompanyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface ICompanyState extends IPaginationBaseState {
  search: string;
}

export class Company extends React.Component<ICompanyProps, ICompanyState> {
  state: ICompanyState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  search = () => {
    if (this.state.search) {
      this.props.reset();
      this.setState({ activePage: 1 }, () => {
        const { activePage, itemsPerPage, sort, order, search } = this.state;
        this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
      });
    }
  };

  clear = () => {
    this.props.reset();
    this.setState({ search: '', activePage: 1 }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order, search } = this.state;
    if (search) {
      this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
    } else {
      this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
    }
  };

  render() {
    const { companyList, match } = this.props;
    return (
      <div>
        <h2 id="company-heading">
          <Translate contentKey="sepaApp.company.home.title">Companies</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sepaApp.company.home.createLabel">Create a new Company</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('sepaApp.company.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            {companyList && companyList.length > 0 ? (
              <Table responsive aria-describedby="company-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('name')}>
                      <Translate contentKey="sepaApp.company.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('email')}>
                      <Translate contentKey="sepaApp.company.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('addressDirection')}>
                      <Translate contentKey="sepaApp.company.addressDirection">Address Direction</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('addressNumber')}>
                      <Translate contentKey="sepaApp.company.addressNumber">Address Number</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('floor')}>
                      <Translate contentKey="sepaApp.company.floor">Floor</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('departament')}>
                      <Translate contentKey="sepaApp.company.departament">Departament</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('cuit')}>
                      <Translate contentKey="sepaApp.company.cuit">Cuit</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('isSubscripted')}>
                      <Translate contentKey="sepaApp.company.isSubscripted">Is Subscripted</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('fantasyName')}>
                      <Translate contentKey="sepaApp.company.fantasyName">Fantasy Name</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('postalCode')}>
                      <Translate contentKey="sepaApp.company.postalCode">Postal Code</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('tlf')}>
                      <Translate contentKey="sepaApp.company.tlf">Tlf</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('internalTlf')}>
                      <Translate contentKey="sepaApp.company.internalTlf">Internal Tlf</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('contact')}>
                      <Translate contentKey="sepaApp.company.contact">Contact</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('cellphone')}>
                      <Translate contentKey="sepaApp.company.cellphone">Cellphone</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('visitsQtyMin')}>
                      <Translate contentKey="sepaApp.company.visitsQtyMin">Visits Qty Min</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('visitsQtyMax')}>
                      <Translate contentKey="sepaApp.company.visitsQtyMax">Visits Qty Max</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('habPrim')}>
                      <Translate contentKey="sepaApp.company.habPrim">Hab Prim</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('habSec')}>
                      <Translate contentKey="sepaApp.company.habSec">Hab Sec</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('comment')}>
                      <Translate contentKey="sepaApp.company.comment">Comment</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('createdAt')}>
                      <Translate contentKey="sepaApp.company.createdAt">Created At</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('updatedAt')}>
                      <Translate contentKey="sepaApp.company.updatedAt">Updated At</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      <Translate contentKey="sepaApp.company.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      <Translate contentKey="sepaApp.company.primIndustryTipe">Prim Industry Tipe</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      <Translate contentKey="sepaApp.company.secIndustryTipe">Sec Industry Tipe</Translate> <FontAwesomeIcon icon="sort" />
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
                      <td>
                        <TextFormat type="date" value={company.createdAt} format={APP_DATE_FORMAT} />
                      </td>
                      <td>
                        <TextFormat type="date" value={company.updatedAt} format={APP_DATE_FORMAT} />
                      </td>
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
                            <FontAwesomeIcon icon="eye" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.view">View</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${company.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${company.id}/delete`} color="danger" size="sm">
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
              <div className="alert alert-warning">
                <Translate contentKey="sepaApp.company.home.notFound">No Companies found</Translate>
              </div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ company }: IRootState) => ({
  companyList: company.entities,
  totalItems: company.totalItems,
  links: company.links,
  entity: company.entity,
  updateSuccess: company.updateSuccess
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Company);
