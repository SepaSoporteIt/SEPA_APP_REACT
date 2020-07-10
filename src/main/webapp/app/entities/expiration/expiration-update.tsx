import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { getEntities as getStudies } from 'app/entities/study/study.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './expiration.reducer';
import { IExpiration } from 'app/shared/model/expiration.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExpirationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExpirationUpdate = (props: IExpirationUpdateProps) => {
  const [companyId, setCompanyId] = useState('0');
  const [employeeId, setEmployeeId] = useState('0');
  const [studyId, setStudyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { expirationEntity, companies, employees, studies, loading, updating } = props;

  const { comments } = expirationEntity;

  const handleClose = () => {
    props.history.push('/expiration');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getCompanies();
    props.getEmployees();
    props.getStudies();
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
    if (errors.length === 0) {
      const entity = {
        ...expirationEntity,
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
          <h2 id="sepaAppReactApp.expiration.home.createOrEditLabel">Create or edit a Expiration</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : expirationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="expiration-id">ID</Label>
                  <AvInput id="expiration-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startDateLabel" for="expiration-startDate">
                  Start Date
                </Label>
                <AvField id="expiration-startDate" type="date" className="form-control" name="startDate" />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="expiration-endDate">
                  End Date
                </Label>
                <AvField id="expiration-endDate" type="date" className="form-control" name="endDate" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="expiration-status">
                  Status
                </Label>
                <AvInput
                  id="expiration-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && expirationEntity.status) || 'VENCIDO'}
                >
                  <option value="VENCIDO">VENCIDO</option>
                  <option value="A_VENCER">A_VENCER</option>
                  <option value="PENDIENTE">PENDIENTE</option>
                  <option value="VIGENTE">VIGENTE</option>
                  <option value="ANTIGUO">ANTIGUO</option>
                  <option value="SIN_FECHA">SIN_FECHA</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="commentsLabel" for="expiration-comments">
                  Comments
                </Label>
                <AvInput id="expiration-comments" type="textarea" name="comments" />
              </AvGroup>
              <AvGroup>
                <Label for="expiration-company">Company</Label>
                <AvInput id="expiration-company" type="select" className="form-control" name="company.id">
                  <option value="" key="0" />
                  {companies
                    ? companies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.fantasyName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="expiration-employee">Employee</Label>
                <AvInput id="expiration-employee" type="select" className="form-control" name="employee.id">
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
                <Label for="expiration-study">Study</Label>
                <AvInput id="expiration-study" type="select" className="form-control" name="study.id">
                  <option value="" key="0" />
                  {studies
                    ? studies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/expiration" replace color="info">
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
  companies: storeState.company.entities,
  employees: storeState.employee.entities,
  studies: storeState.study.entities,
  expirationEntity: storeState.expiration.entity,
  loading: storeState.expiration.loading,
  updating: storeState.expiration.updating,
  updateSuccess: storeState.expiration.updateSuccess,
});

const mapDispatchToProps = {
  getCompanies,
  getEmployees,
  getStudies,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExpirationUpdate);
