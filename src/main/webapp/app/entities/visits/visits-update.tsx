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
import { getEntity, updateEntity, createEntity, reset } from './visits.reducer';
import { IVisits } from 'app/shared/model/visits.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVisitsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VisitsUpdate = (props: IVisitsUpdateProps) => {
  const [companyId, setCompanyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { visitsEntity, companies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/visits' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCompanies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...visitsEntity,
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
          <h2 id="sepaApp.visits.home.createOrEditLabel">
            <Translate contentKey="sepaApp.visits.home.createOrEditLabel">Create or edit a Visits</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : visitsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="visits-id">
                    <Translate contentKey="sepaApp.visits.id">ID</Translate>
                  </Label>
                  <AvInput id="visits-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="visits-company">
                  <Translate contentKey="sepaApp.visits.company">Company</Translate>
                </Label>
                <AvInput id="visits-company" type="select" className="form-control" name="company.id">
                  <option value="" key="0" />
                  {companies
                    ? companies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name + " " + otherEntity.addressDirection + " " + otherEntity.addressNumber}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="visit_dateLabel" for="visits-visit_date">
                  <Translate contentKey="sepaApp.visits.visit_date">Visit Date</Translate>
                </Label>
                <AvField id="visits-visit_date" type="date" className="form-control" name="visit_date" />
              </AvGroup>
              <AvGroup>
                <Label id="employeeLabel" for="visits-employee">
                  <Translate contentKey="sepaApp.visits.employee">Employee</Translate>
                </Label>
                <AvField id="visits-employee" type="text" name="employee" />
              </AvGroup>
              <AvGroup>
                <Label id="commentsLabel" for="visits-comments">
                  <Translate contentKey="sepaApp.visits.comments">Comments</Translate>
                </Label>
                <AvField id="visits-comments" type="text" name="comments" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/visits" replace color="info">
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
  visitsEntity: storeState.visits.entity,
  loading: storeState.visits.loading,
  updating: storeState.visits.updating,
  updateSuccess: storeState.visits.updateSuccess,
});

const mapDispatchToProps = {
  getCompanies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VisitsUpdate);
