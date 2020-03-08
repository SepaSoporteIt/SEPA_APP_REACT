import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyDetail = (props: ICompanyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { companyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="sepaApp.company.detail.title">Company</Translate> [<b>{companyEntity.name}</b>]
        </h2>
        <dl className="jh-entity-details">
          <h3>
            <Translate contentKey="sepaApp.company.detail.basicData">Basic Data</Translate>
          </h3>
          <dt>
            <span id="name">
              <Translate contentKey="sepaApp.company.name">Name</Translate>
            </span>
          </dt>
          <dd>{companyEntity.name}</dd>
          <dt>
            <span id="fantasyName">
              <Translate contentKey="sepaApp.company.fantasyName">Fantasy Name</Translate>
            </span>
          </dt>
          <dd>{companyEntity.fantasyName}</dd>
          <dt>
            <span id="cuit">
              <Translate contentKey="sepaApp.company.cuit">Cuit</Translate>
            </span>
          </dt>
          <dd>{companyEntity.cuit}</dd>
          <dt>
            <Translate contentKey="sepaApp.company.employee">Employee</Translate>
          </dt>
          <dd>{companyEntity.employee ? companyEntity.employee.name : ''}</dd>
          </dl>

          <dl className="jh-entity-details">
          <h3>
            <Translate contentKey="sepaApp.company.detail.clientAddress">Stablishment Address</Translate>
          </h3>
          <dt>
            <span id="addressDirection">
              <Translate contentKey="sepaApp.company.addressDirection">Address Direction</Translate>
            </span>
          </dt>
          <dd>{companyEntity.addressDirection}</dd>
          <dt>
            <span id="addressNumber">
              <Translate contentKey="sepaApp.company.addressNumber">Address Number</Translate>
            </span>
          </dt>
          <dd>{companyEntity.addressNumber}</dd>
    <dt>
          <span id="betweenStreets">
            <Translate contentKey="sepaApp.company.betweenStreets">Between Streets</Translate>
          </span>
    </dt>
    <dd>{companyEntity.betweenStreets}</dd>
          <dt>
            <span id="floor">
              <Translate contentKey="sepaApp.company.floor">Floor</Translate>
            </span>
          </dt>
          <dd>{companyEntity.floor}</dd>
          <dt>
            <span id="departament">
              <Translate contentKey="sepaApp.company.departament">Departament</Translate>
            </span>
          </dt>
          <dd>{companyEntity.departament}</dd>
    <dt>
      <Translate contentKey="sepaApp.company.localidadId">Localidad Id</Translate>
    </dt>
    <dd>{companyEntity.localidadId ? companyEntity.localidadId.localidad : ''}</dd>
    <dt>
      <Translate contentKey="sepaApp.company.partidoId">Partido Id</Translate>
    </dt>
    <dd>{companyEntity.partidoId ? companyEntity.partidoId.partido : ''}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="sepaApp.company.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{companyEntity.postalCode}</dd>
          </dl>

          <dl className="jh-entity-details">
          <h3>
          <Translate contentKey="sepaApp.company.detail.contactData">Contact Data</Translate>
          </h3>
          <dt>
            <span id="contact">
              <Translate contentKey="sepaApp.company.contact">Contact</Translate>
            </span>
          </dt>
          <dd>{companyEntity.contact}</dd>
          <dt>
            <span id="tlf">
              <Translate contentKey="sepaApp.company.tlf">Tlf</Translate>
            </span>
          </dt>
          <dd>{companyEntity.tlf}</dd>
          <dt>
            <span id="internalTlf">
              <Translate contentKey="sepaApp.company.internalTlf">Internal Tlf</Translate>
            </span>
          </dt>
          <dd>{companyEntity.internalTlf}</dd>
          <dt>
            <span id="cellphone">
              <Translate contentKey="sepaApp.company.cellphone">Cellphone</Translate>
            </span>
          </dt>
          <dd>{companyEntity.cellphone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="sepaApp.company.email">Email</Translate>
            </span>
          </dt>
          <dd>{companyEntity.email}</dd>
          </dl>

          <dl className="jh-entity-details">
          <h3>
            <Translate contentKey="sepaApp.company.detail.afipActivity">Activity stablished by AFIP</Translate>
          </h3>
          <dt>
            <Translate contentKey="sepaApp.company.primIndustryTipe">Prim Industry Tipe</Translate>
          </dt>
          <dd>{companyEntity.primIndustryTipe ? companyEntity.primIndustryTipe.ciiu : ''}</dd>
          <dt>
            <Translate contentKey="sepaApp.company.secIndustryTipe">Sec Industry Tipe</Translate>
          </dt>
          <dd>{companyEntity.secIndustryTipe ? companyEntity.secIndustryTipe.ciiu : ''}</dd>
          </dl>

          <dl className="jh-entity-details">
          <h3>
            <Translate contentKey="sepaApp.company.detail.serviceData">Service Data</Translate>
          </h3>
          <dt>
            <span id="visitsQtyMin">
              <Translate contentKey="sepaApp.company.visitsQtyMin">Visits Qty Min</Translate>
            </span>
          </dt>
          <dd>{companyEntity.visitsQtyMin}</dd>
          <dt>
            <span id="visitsQtyMax">
              <Translate contentKey="sepaApp.company.visitsQtyMax">Visits Qty Max</Translate>
            </span>
          </dt>
          <dd>{companyEntity.visitsQtyMax}</dd>           
          <dt>
            <span id="isSubscripted">
              <Translate contentKey="sepaApp.company.isSubscripted">Is Subscripted</Translate>
            </span>
          </dt>
          <dd>{companyEntity.isSubscripted ? 'si' : 'no'}</dd>
          </dl>

          <dl className="jh-entity-details">
          <h3>
            <Translate contentKey="sepaApp.company.detail.others">Others</Translate>
          </h3>  
          <dt>
            <span id="habPrim">
              <Translate contentKey="sepaApp.company.habPrim">Hab Prim</Translate>
            </span>
          </dt>
          <dd>{companyEntity.habPrim}</dd>
          <dt>
            <span id="habSec">
              <Translate contentKey="sepaApp.company.habSec">Hab Sec</Translate>
            </span>
          </dt>
          <dd>{companyEntity.habSec}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="sepaApp.company.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{companyEntity.comment}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="sepaApp.company.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={companyEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="sepaApp.company.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={companyEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
        </dl>

        <Button tag={Link} to="/company" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ company }: IRootState) => ({
  companyEntity: company.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyDetail);
