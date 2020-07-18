import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { getEntities as getStudies } from 'app/entities/study/study.reducer';
import { getEntity, updateEntity, createEntity, reset } from './expiration.reducer';
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

  const handleClose = () => {
    props.history.push('/expiration' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCompanies();
    props.getEmployees();
    props.getStudies();
  }, []);

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
          <h2 id="sepaApp.expiration.home.createOrEditLabel">
            <Translate contentKey="sepaApp.expiration.home.createOrEditLabel">Create or edit a Expiration</Translate>
          </h2>
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
                  <Label for="expiration-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="expiration-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startDateLabel" for="expiration-startDate">
                  <Translate contentKey="sepaApp.expiration.startDate">Start Date</Translate>
                </Label>
                <AvField id="expiration-startDate" type="date" className="form-control" name="startDate" />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="expiration-endDate">
                  <Translate contentKey="sepaApp.expiration.endDate">End Date</Translate>
                </Label>
                <AvField id="expiration-endDate" type="date" className="form-control" name="endDate" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="expiration-status">
                  <Translate contentKey="sepaApp.expiration.status">Status</Translate>
                </Label>
                <AvInput
                  id="expiration-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && expirationEntity.status) || 'VENCIDO'}
                >
                  <option value="VENCIDO">{translate('sepaApp.Status.VENCIDO')}</option>
                  <option value="A_VENCER">{translate('sepaApp.Status.A_VENCER')}</option>
                  <option value="VIGENTE">{translate('sepaApp.Status.VIGENTE')}</option>
                  <option value="PENDIENTE">{translate('sepaApp.Status.PENDIENTE')}</option>
                  <option value="ANTIGUO">{translate('sepaApp.Status.ANTIGUO')}</option>
                  <option value="SIN_FECHA">{translate('sepaApp.Status.SIN_FECHA')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="commentsLabel" for="expiration-comments">
                  <Translate contentKey="sepaApp.expiration.comments">Comments</Translate>
                </Label>
                <AvField id="expiration-comments" type="text" name="comments" />
              </AvGroup>
              <AvGroup>
                <Label id="uniqueCodeLabel" for="expiration-uniqueCode">
                  <Translate contentKey="sepaApp.expiration.uniqueCode">Unique Code</Translate>
                </Label>
                <AvField id="expiration-uniqueCode" type="text" name="uniqueCode" />
              </AvGroup>
              <AvGroup>
                <Label id="responsibleLabel" for="expiration-responsible">
                  <Translate contentKey="sepaApp.expiration.responsible">Responsible</Translate>
                </Label>
                <AvField id="expiration-responsible" type="text" name="responsible" />
              </AvGroup>
              <AvGroup check>
                <Label id="isCompletedLabel">
                  <AvInput id="expiration-isCompleted" type="checkbox" className="form-check-input" name="isCompleted" />
                  <Translate contentKey="sepaApp.expiration.isCompleted">Is Completed</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="expiration-company">
                  <Translate contentKey="sepaApp.expiration.company">Company</Translate>
                </Label>
                <AvInput id="expiration-company" type="select" className="form-control" name="company.id">
                  <option value="" key="0" />
                  {companies
                    ? companies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="expiration-employee">
                  <Translate contentKey="sepaApp.expiration.employee">Employee</Translate>
                </Label>
                <AvInput id="expiration-employee" type="select" className="form-control" name="employee.id">
                  <option value="" key="0" />
                  {employees
                    ? employees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="expiration-study">
                  <Translate contentKey="sepaApp.expiration.study">Study</Translate>
                </Label>
                <AvInput id="expiration-study" type="select" className="form-control" name="study.id">
                  <option value="" key="0" />
                  {studies
                    ? studies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/expiration" replace color="info">
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
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExpirationUpdate);
