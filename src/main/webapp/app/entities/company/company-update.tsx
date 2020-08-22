import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IIndustryType } from 'app/shared/model/industry-type.model';
import { getEntities as getIndustryTypes } from 'app/entities/industry-type/industry-type.reducer';
import { ILocalidadAndPartido } from 'app/shared/model/localidad-and-partido.model';
import { getEntities as getLocalidadAndPartidos } from 'app/entities/localidad-and-partido/localidad-and-partido.reducer';
import { getEntity, updateEntity, createEntity, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyUpdate = (props: ICompanyUpdateProps) => {
  const [employeeId, setEmployeeId] = useState('0');
  const [industryTypeId, setIndustryTypeId] = useState('0');
  const [secIndustryTypeId, setSecIndustryTypeId] = useState('0');
  const [localidadIdId, setLocalidadIdId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { companyEntity, employees, industryTypes, localidadAndPartidos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/company' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmployees();
    props.getIndustryTypes();
    props.getLocalidadAndPartidos();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...companyEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

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
            <AvForm model={isNew ? {} : companyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="company-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="company-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}

            <AvGroup>
              <h3>
                <Translate contentKey="sepaApp.company.detail.basicData">Basic Data</Translate>
              </h3>
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
                <Label id="fantasyNameLabel" for="company-fantasyName">
                  <Translate contentKey="sepaApp.company.fantasyName">Fantasy Name</Translate>
                </Label>
                <AvField id="company-fantasyName" type="text" name="fantasyName" />
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
                <Label for="company-employee">
                  <Translate contentKey="sepaApp.company.employee">Employee</Translate>
                </Label>
                <AvInput id="company-employee" type="select" className="form-control" name="employee.id">
                  <option value="" key="0" />
                  {employees
                    ? employees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name + " " + otherEntity.surname}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
            </AvGroup>

            <AvGroup>
              <h3>
                <Translate contentKey="sepaApp.company.detail.clientAddress">Stablishment Address</Translate>
              </h3>
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
                <Label id="betweenStreetsLabel" for="company-betweenStreets">
                  <Translate contentKey="sepaApp.company.betweenStreets">Between Streets</Translate>
                </Label>
                <AvField id="company-betweenStreets" type="text" name="betweenStreets" />
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
                <Label for="company-localidadId">
                  <Translate contentKey="sepaApp.company.localidadId">Localidad Id</Translate>
                </Label>
                <AvInput id="company-localidadId" type="select" className="form-control" name="localidadId.id">
                  <option value="" key="0" />
                  {localidadAndPartidos
                    ? localidadAndPartidos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.localidad + " " + otherEntity.partido}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="postalCodeLabel" for="company-postalCode">
                  <Translate contentKey="sepaApp.company.postalCode">Postal Code</Translate>
                </Label>
                <AvField id="company-postalCode" type="text" name="postalCode" />
              </AvGroup>
            </AvGroup>

            <AvGroup>
              <h3>
                <Translate contentKey="sepaApp.company.detail.contactData">Contact Data</Translate>
              </h3>
              <AvGroup>
                <Label id="contactLabel" for="company-contact">
                  <Translate contentKey="sepaApp.company.contact">Contact</Translate>
                </Label>
                <AvField id="company-contact" type="text" name="contact" />
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
                <Label id="cellphoneLabel" for="company-cellphone">
                  <Translate contentKey="sepaApp.company.cellphone">Cellphone</Translate>
                </Label>
                <AvField id="company-cellphone" type="text" name="cellphone" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="company-email">
                  <Translate contentKey="sepaApp.company.email">Email</Translate>
                </Label>
                <AvField id="company-email" type="text" name="email" />
              </AvGroup>
            </AvGroup>

            <AvGroup>
              <h3>
                <Translate contentKey="sepaApp.company.detail.afipActivity">Activity stablished by AFIP</Translate>
              </h3>
              <AvGroup>
                <Label for="company-industryType">
                  <Translate contentKey="sepaApp.company.industryType">1º CIIU</Translate>
                </Label>
                <AvInput id="company-industryType" type="select" className="form-control" name="industryType.id">
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
                <Label for="company-secIndustryType">
                  <Translate contentKey="sepaApp.company.secIndustryType">2º CIIU</Translate>
                </Label>
                <AvInput id="company-secIndustryType" type="select" className="form-control" name="secIndustryType.id">
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
            </AvGroup>

            <AvGroup>
              <h3>
                <Translate contentKey="sepaApp.company.detail.serviceData">Service Data</Translate>
              </h3>
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
              <AvGroup check>
                <Label id="isSubscriptedLabel">
                  <AvInput id="company-isSubscripted" type="checkbox" className="form-check-input" name="isSubscripted" />
                  <Translate contentKey="sepaApp.company.isSubscripted">Is Subscripted</Translate>
                </Label>
              </AvGroup>
            </AvGroup>

            <AvGroup>
              <h3>
                <Translate contentKey="sepaApp.company.detail.others">Others</Translate>
              </h3>
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
};

const mapStateToProps = (storeState: IRootState) => ({
  employees: storeState.employee.entities,
  industryTypes: storeState.industryType.entities,
  localidadAndPartidos: storeState.localidadAndPartido.entities,
  companyEntity: storeState.company.entity,
  loading: storeState.company.loading,
  updating: storeState.company.updating,
  updateSuccess: storeState.company.updateSuccess,
});

const mapDispatchToProps = {
  getEmployees,
  getIndustryTypes,
  getLocalidadAndPartidos,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUpdate);
