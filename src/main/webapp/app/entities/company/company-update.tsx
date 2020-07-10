import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IIndustryType } from 'app/shared/model/industry-type.model';
import { getEntities as getIndustryTypes } from 'app/entities/industry-type/industry-type.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICompanyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyUpdate = (props: ICompanyUpdateProps) => {
  const [employeeId, setEmployeeId] = useState('0');
  const [primIndustryTipeId, setPrimIndustryTipeId] = useState('0');
  const [secIndustryTipeId, setSecIndustryTipeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { companyEntity, employees, industryTypes, loading, updating } = props;

  const { comment } = companyEntity;

  const handleClose = () => {
    props.history.push('/company');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getEmployees();
    props.getIndustryTypes();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

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
          <h2 id="sepaAppReactApp.company.home.createOrEditLabel">Create or edit a Company</h2>
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
                  <Label for="company-id">ID</Label>
                  <AvInput id="company-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="company-name">
                  Name
                </Label>
                <AvField
                  id="company-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="company-email">
                  Email
                </Label>
                <AvField id="company-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="addressDirectionLabel" for="company-addressDirection">
                  Address Direction
                </Label>
                <AvField
                  id="company-addressDirection"
                  type="text"
                  name="addressDirection"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="addressNumberLabel" for="company-addressNumber">
                  Address Number
                </Label>
                <AvField
                  id="company-addressNumber"
                  type="text"
                  name="addressNumber"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="floorLabel" for="company-floor">
                  Floor
                </Label>
                <AvField id="company-floor" type="text" name="floor" />
              </AvGroup>
              <AvGroup>
                <Label id="departamentLabel" for="company-departament">
                  Departament
                </Label>
                <AvField id="company-departament" type="text" name="departament" />
              </AvGroup>
              <AvGroup>
                <Label id="cuitLabel" for="company-cuit">
                  Cuit
                </Label>
                <AvField
                  id="company-cuit"
                  type="text"
                  name="cuit"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isSubscriptedLabel">
                  <AvInput id="company-isSubscripted" type="checkbox" className="form-check-input" name="isSubscripted" />
                  Is Subscripted
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="fantasyNameLabel" for="company-fantasyName">
                  Fantasy Name
                </Label>
                <AvField id="company-fantasyName" type="text" name="fantasyName" />
              </AvGroup>
              <AvGroup>
                <Label id="postalCodeLabel" for="company-postalCode">
                  Postal Code
                </Label>
                <AvField id="company-postalCode" type="text" name="postalCode" />
              </AvGroup>
              <AvGroup>
                <Label id="tlfLabel" for="company-tlf">
                  Tlf
                </Label>
                <AvField id="company-tlf" type="text" name="tlf" />
              </AvGroup>
              <AvGroup>
                <Label id="internalTlfLabel" for="company-internalTlf">
                  Internal Tlf
                </Label>
                <AvField id="company-internalTlf" type="text" name="internalTlf" />
              </AvGroup>
              <AvGroup>
                <Label id="contactLabel" for="company-contact">
                  Contact
                </Label>
                <AvField id="company-contact" type="text" name="contact" />
              </AvGroup>
              <AvGroup>
                <Label id="cellphoneLabel" for="company-cellphone">
                  Cellphone
                </Label>
                <AvField id="company-cellphone" type="text" name="cellphone" />
              </AvGroup>
              <AvGroup>
                <Label id="visitsQtyMinLabel" for="company-visitsQtyMin">
                  Visits Qty Min
                </Label>
                <AvField id="company-visitsQtyMin" type="string" className="form-control" name="visitsQtyMin" />
              </AvGroup>
              <AvGroup>
                <Label id="visitsQtyMaxLabel" for="company-visitsQtyMax">
                  Visits Qty Max
                </Label>
                <AvField id="company-visitsQtyMax" type="string" className="form-control" name="visitsQtyMax" />
              </AvGroup>
              <AvGroup>
                <Label id="habPrimLabel" for="company-habPrim">
                  Hab Prim
                </Label>
                <AvField id="company-habPrim" type="text" name="habPrim" />
              </AvGroup>
              <AvGroup>
                <Label id="habSecLabel" for="company-habSec">
                  Hab Sec
                </Label>
                <AvField id="company-habSec" type="text" name="habSec" />
              </AvGroup>
              <AvGroup>
                <Label id="commentLabel" for="company-comment">
                  Comment
                </Label>
                <AvInput id="company-comment" type="textarea" name="comment" />
              </AvGroup>
              <AvGroup>
                <Label id="legislationIdLabel" for="company-legislationId">
                  Legislation Id
                </Label>
                <AvField id="company-legislationId" type="string" className="form-control" name="legislationId" />
              </AvGroup>
              <AvGroup>
                <Label id="solicitadorIdLabel" for="company-solicitadorId">
                  Solicitador Id
                </Label>
                <AvField id="company-solicitadorId" type="string" className="form-control" name="solicitadorId" />
              </AvGroup>
              <AvGroup>
                <Label id="ambitoIdLabel" for="company-ambitoId">
                  Ambito Id
                </Label>
                <AvField id="company-ambitoId" type="string" className="form-control" name="ambitoId" />
              </AvGroup>
              <AvGroup>
                <Label id="autoridadIdLabel" for="company-autoridadId">
                  Autoridad Id
                </Label>
                <AvField id="company-autoridadId" type="string" className="form-control" name="autoridadId" />
              </AvGroup>
              <AvGroup>
                <Label id="frecuencyTypeIdLabel" for="company-frecuencyTypeId">
                  Frecuency Type Id
                </Label>
                <AvField id="company-frecuencyTypeId" type="string" className="form-control" name="frecuencyTypeId" />
              </AvGroup>
              <AvGroup>
                <Label id="createdAtLabel" for="company-createdAt">
                  Created At
                </Label>
                <AvInput
                  id="company-createdAt"
                  type="datetime-local"
                  className="form-control"
                  name="createdAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.companyEntity.createdAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedAtLabel" for="company-updatedAt">
                  Updated At
                </Label>
                <AvInput
                  id="company-updatedAt"
                  type="datetime-local"
                  className="form-control"
                  name="updatedAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.companyEntity.updatedAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="company-employee">Employee</Label>
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
                <Label for="company-primIndustryTipe">Prim Industry Tipe</Label>
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
                <Label for="company-secIndustryTipe">Sec Industry Tipe</Label>
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
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
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
  companyEntity: storeState.company.entity,
  loading: storeState.company.loading,
  updating: storeState.company.updating,
  updateSuccess: storeState.company.updateSuccess,
});

const mapDispatchToProps = {
  getEmployees,
  getIndustryTypes,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUpdate);
