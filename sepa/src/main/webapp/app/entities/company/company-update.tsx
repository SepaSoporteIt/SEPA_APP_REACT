import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IIndustryType } from 'app/shared/model/industry-type.model';
import { getEntities as getIndustryTypes } from 'app/entities/industry-type/industry-type.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICompanyUpdateState {
  isNew: boolean;
  employeeId: string;
  primIndustryTipeId: string;
  secIndustryTipeId: string;
}

export class CompanyUpdate extends React.Component<ICompanyUpdateProps, ICompanyUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      employeeId: '0',
      primIndustryTipeId: '0',
      secIndustryTipeId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getEmployees();
    this.props.getIndustryTypes();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    if (errors.length === 0) {
      const { companyEntity } = this.props;
      const entity = {
        ...companyEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/company');
  };

  render() {
    const { companyEntity, employees, industryTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    const { comment } = companyEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sepaApp.company.home.createOrEditLabel">
              <Translate contentKey="sepaApp.company.home.createOrEditLabel">Create or edit a Company</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : companyEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="company-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="company-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="company-name">
                    <Translate contentKey="sepaApp.company.name">Name</Translate>
                  </Label>
                  <AvField
                    id="company-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="company-email">
                    <Translate contentKey="sepaApp.company.email">Email</Translate>
                  </Label>
                  <AvField id="company-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label id="addressDirectionLabel" for="company-addressDirection">
                    <Translate contentKey="sepaApp.company.addressDirection">Address Direction</Translate>
                  </Label>
                  <AvField
                    id="company-addressDirection"
                    type="text"
                    name="addressDirection"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="addressNumberLabel" for="company-addressNumber">
                    <Translate contentKey="sepaApp.company.addressNumber">Address Number</Translate>
                  </Label>
                  <AvField
                    id="company-addressNumber"
                    type="text"
                    name="addressNumber"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="floorLabel" for="company-floor">
                    <Translate contentKey="sepaApp.company.floor">Floor</Translate>
                  </Label>
                  <AvField id="company-floor" type="text" name="floor" />
                </AvGroup>
                <AvGroup>
                  <Label id="departamentLabel" for="company-departament">
                    <Translate contentKey="sepaApp.company.departament">Departament</Translate>
                  </Label>
                  <AvField id="company-departament" type="text" name="departament" />
                </AvGroup>
                <AvGroup>
                  <Label id="cuitLabel" for="company-cuit">
                    <Translate contentKey="sepaApp.company.cuit">Cuit</Translate>
                  </Label>
                  <AvField
                    id="company-cuit"
                    type="text"
                    name="cuit"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="isSubscriptedLabel" check>
                    <AvInput id="company-isSubscripted" type="checkbox" className="form-control" name="isSubscripted" />
                    <Translate contentKey="sepaApp.company.isSubscripted">Is Subscripted</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="fantasyNameLabel" for="company-fantasyName">
                    <Translate contentKey="sepaApp.company.fantasyName">Fantasy Name</Translate>
                  </Label>
                  <AvField id="company-fantasyName" type="text" name="fantasyName" />
                </AvGroup>
                <AvGroup>
                  <Label id="postalCodeLabel" for="company-postalCode">
                    <Translate contentKey="sepaApp.company.postalCode">Postal Code</Translate>
                  </Label>
                  <AvField id="company-postalCode" type="text" name="postalCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="tlfLabel" for="company-tlf">
                    <Translate contentKey="sepaApp.company.tlf">Tlf</Translate>
                  </Label>
                  <AvField id="company-tlf" type="text" name="tlf" />
                </AvGroup>
                <AvGroup>
                  <Label id="internalTlfLabel" for="company-internalTlf">
                    <Translate contentKey="sepaApp.company.internalTlf">Internal Tlf</Translate>
                  </Label>
                  <AvField id="company-internalTlf" type="text" name="internalTlf" />
                </AvGroup>
                <AvGroup>
                  <Label id="contactLabel" for="company-contact">
                    <Translate contentKey="sepaApp.company.contact">Contact</Translate>
                  </Label>
                  <AvField id="company-contact" type="text" name="contact" />
                </AvGroup>
                <AvGroup>
                  <Label id="cellphoneLabel" for="company-cellphone">
                    <Translate contentKey="sepaApp.company.cellphone">Cellphone</Translate>
                  </Label>
                  <AvField id="company-cellphone" type="text" name="cellphone" />
                </AvGroup>
                <AvGroup>
                  <Label id="visitsQtyMinLabel" for="company-visitsQtyMin">
                    <Translate contentKey="sepaApp.company.visitsQtyMin">Visits Qty Min</Translate>
                  </Label>
                  <AvField id="company-visitsQtyMin" type="string" className="form-control" name="visitsQtyMin" />
                </AvGroup>
                <AvGroup>
                  <Label id="visitsQtyMaxLabel" for="company-visitsQtyMax">
                    <Translate contentKey="sepaApp.company.visitsQtyMax">Visits Qty Max</Translate>
                  </Label>
                  <AvField id="company-visitsQtyMax" type="string" className="form-control" name="visitsQtyMax" />
                </AvGroup>
                <AvGroup>
                  <Label id="habPrimLabel" for="company-habPrim">
                    <Translate contentKey="sepaApp.company.habPrim">Hab Prim</Translate>
                  </Label>
                  <AvField id="company-habPrim" type="text" name="habPrim" />
                </AvGroup>
                <AvGroup>
                  <Label id="habSecLabel" for="company-habSec">
                    <Translate contentKey="sepaApp.company.habSec">Hab Sec</Translate>
                  </Label>
                  <AvField id="company-habSec" type="text" name="habSec" />
                </AvGroup>
                <AvGroup>
                  <Label id="commentLabel" for="company-comment">
                    <Translate contentKey="sepaApp.company.comment">Comment</Translate>
                  </Label>
                  <AvInput id="company-comment" type="textarea" name="comment" />
                </AvGroup>
                <AvGroup>
                  <Label id="legislationIdLabel" for="company-legislationId">
                    <Translate contentKey="sepaApp.company.legislationId">Legislation Id</Translate>
                  </Label>
                  <AvField id="company-legislationId" type="string" className="form-control" name="legislationId" />
                </AvGroup>
                <AvGroup>
                  <Label id="solicitadorIdLabel" for="company-solicitadorId">
                    <Translate contentKey="sepaApp.company.solicitadorId">Solicitador Id</Translate>
                  </Label>
                  <AvField id="company-solicitadorId" type="string" className="form-control" name="solicitadorId" />
                </AvGroup>
                <AvGroup>
                  <Label id="ambitoIdLabel" for="company-ambitoId">
                    <Translate contentKey="sepaApp.company.ambitoId">Ambito Id</Translate>
                  </Label>
                  <AvField id="company-ambitoId" type="string" className="form-control" name="ambitoId" />
                </AvGroup>
                <AvGroup>
                  <Label id="autoridadIdLabel" for="company-autoridadId">
                    <Translate contentKey="sepaApp.company.autoridadId">Autoridad Id</Translate>
                  </Label>
                  <AvField id="company-autoridadId" type="string" className="form-control" name="autoridadId" />
                </AvGroup>
                <AvGroup>
                  <Label id="frecuencyTypeIdLabel" for="company-frecuencyTypeId">
                    <Translate contentKey="sepaApp.company.frecuencyTypeId">Frecuency Type Id</Translate>
                  </Label>
                  <AvField id="company-frecuencyTypeId" type="string" className="form-control" name="frecuencyTypeId" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="company-createdAt">
                    <Translate contentKey="sepaApp.company.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="company-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.companyEntity.createdAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedAtLabel" for="company-updatedAt">
                    <Translate contentKey="sepaApp.company.updatedAt">Updated At</Translate>
                  </Label>
                  <AvInput
                    id="company-updatedAt"
                    type="datetime-local"
                    className="form-control"
                    name="updatedAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.companyEntity.updatedAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="company-employee">
                    <Translate contentKey="sepaApp.company.employee">Employee</Translate>
                  </Label>
                  <AvInput id="company-employee" type="select" className="form-control" name="employee.id">
                    <option value="" key="0" />
                    {employees
                      ? employees.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="company-primIndustryTipe">
                    <Translate contentKey="sepaApp.company.primIndustryTipe">Prim Industry Tipe</Translate>
                  </Label>
                  <AvInput id="company-primIndustryTipe" type="select" className="form-control" name="primIndustryTipe.id">
                    <option value="" key="0" />
                    {industryTypes
                      ? industryTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.ciiu}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="company-secIndustryTipe">
                    <Translate contentKey="sepaApp.company.secIndustryTipe">Sec Industry Tipe</Translate>
                  </Label>
                  <AvInput id="company-secIndustryTipe" type="select" className="form-control" name="secIndustryTipe.id">
                    <option value="" key="0" />
                    {industryTypes
                      ? industryTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.ciiu}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/company" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  employees: storeState.employee.entities,
  industryTypes: storeState.industryType.entities,
  companyEntity: storeState.company.entity,
  loading: storeState.company.loading,
  updating: storeState.company.updating,
  updateSuccess: storeState.company.updateSuccess
});

const mapDispatchToProps = {
  getEmployees,
  getIndustryTypes,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CompanyUpdate);
