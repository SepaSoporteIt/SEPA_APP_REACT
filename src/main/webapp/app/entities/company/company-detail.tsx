import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
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
          Company [<b>{companyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{companyEntity.name}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{companyEntity.email}</dd>
          <dt>
            <span id="addressDirection">Address Direction</span>
          </dt>
          <dd>{companyEntity.addressDirection}</dd>
          <dt>
            <span id="addressNumber">Address Number</span>
          </dt>
          <dd>{companyEntity.addressNumber}</dd>
          <dt>
            <span id="floor">Floor</span>
          </dt>
          <dd>{companyEntity.floor}</dd>
          <dt>
            <span id="departament">Departament</span>
          </dt>
          <dd>{companyEntity.departament}</dd>
          <dt>
            <span id="cuit">Cuit</span>
          </dt>
          <dd>{companyEntity.cuit}</dd>
          <dt>
            <span id="isSubscripted">Is Subscripted</span>
          </dt>
          <dd>{companyEntity.isSubscripted ? 'true' : 'false'}</dd>
          <dt>
            <span id="fantasyName">Fantasy Name</span>
          </dt>
          <dd>{companyEntity.fantasyName}</dd>
          <dt>
            <span id="postalCode">Postal Code</span>
          </dt>
          <dd>{companyEntity.postalCode}</dd>
          <dt>
            <span id="tlf">Tlf</span>
          </dt>
          <dd>{companyEntity.tlf}</dd>
          <dt>
            <span id="internalTlf">Internal Tlf</span>
          </dt>
          <dd>{companyEntity.internalTlf}</dd>
          <dt>
            <span id="contact">Contact</span>
          </dt>
          <dd>{companyEntity.contact}</dd>
          <dt>
            <span id="cellphone">Cellphone</span>
          </dt>
          <dd>{companyEntity.cellphone}</dd>
          <dt>
            <span id="visitsQtyMin">Visits Qty Min</span>
          </dt>
          <dd>{companyEntity.visitsQtyMin}</dd>
          <dt>
            <span id="visitsQtyMax">Visits Qty Max</span>
          </dt>
          <dd>{companyEntity.visitsQtyMax}</dd>
          <dt>
            <span id="habPrim">Hab Prim</span>
          </dt>
          <dd>{companyEntity.habPrim}</dd>
          <dt>
            <span id="habSec">Hab Sec</span>
          </dt>
          <dd>{companyEntity.habSec}</dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>{companyEntity.comment}</dd>
          <dt>
            <span id="legislationId">Legislation Id</span>
          </dt>
          <dd>{companyEntity.legislationId}</dd>
          <dt>
            <span id="solicitadorId">Solicitador Id</span>
          </dt>
          <dd>{companyEntity.solicitadorId}</dd>
          <dt>
            <span id="ambitoId">Ambito Id</span>
          </dt>
          <dd>{companyEntity.ambitoId}</dd>
          <dt>
            <span id="autoridadId">Autoridad Id</span>
          </dt>
          <dd>{companyEntity.autoridadId}</dd>
          <dt>
            <span id="frecuencyTypeId">Frecuency Type Id</span>
          </dt>
          <dd>{companyEntity.frecuencyTypeId}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{companyEntity.createdAt ? <TextFormat value={companyEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{companyEntity.updatedAt ? <TextFormat value={companyEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Employee</dt>
          <dd>{companyEntity.employee ? companyEntity.employee.name : ''}</dd>
          <dt>Prim Industry Tipe</dt>
          <dd>{companyEntity.primIndustryTipe ? companyEntity.primIndustryTipe.ciiu : ''}</dd>
          <dt>Sec Industry Tipe</dt>
          <dd>{companyEntity.secIndustryTipe ? companyEntity.secIndustryTipe.ciiu : ''}</dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ company }: IRootState) => ({
  companyEntity: company.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyDetail);
