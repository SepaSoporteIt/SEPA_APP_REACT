import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeUpdate = (props: IEmployeeUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/employee');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

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
        ...employeeEntity,
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
          <h2 id="sepaAppReactApp.employee.home.createOrEditLabel">Create or edit a Employee</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : employeeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="employee-id">ID</Label>
                  <AvInput id="employee-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="employee-name">
                  Name
                </Label>
                <AvField
                  id="employee-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="surnameLabel" for="employee-surname">
                  Surname
                </Label>
                <AvField
                  id="employee-surname"
                  type="text"
                  name="surname"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="employee-email">
                  Email
                </Label>
                <AvField id="employee-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="tlfLabel" for="employee-tlf">
                  Tlf
                </Label>
                <AvField id="employee-tlf" type="text" name="tlf" />
              </AvGroup>
              <AvGroup check>
                <Label id="isInternalLabel">
                  <AvInput id="employee-isInternal" type="checkbox" className="form-check-input" name="isInternal" />
                  Is Internal
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="matNumberLabel" for="employee-matNumber">
                  Mat Number
                </Label>
                <AvField
                  id="employee-matNumber"
                  type="text"
                  name="matNumber"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cuitLabel" for="employee-cuit">
                  Cuit
                </Label>
                <AvField
                  id="employee-cuit"
                  type="text"
                  name="cuit"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="addressDirectionLabel" for="employee-addressDirection">
                  Address Direction
                </Label>
                <AvField id="employee-addressDirection" type="text" name="addressDirection" />
              </AvGroup>
              <AvGroup>
                <Label id="addressNumberLabel" for="employee-addressNumber">
                  Address Number
                </Label>
                <AvField id="employee-addressNumber" type="text" name="addressNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="floorLabel" for="employee-floor">
                  Floor
                </Label>
                <AvField id="employee-floor" type="text" name="floor" />
              </AvGroup>
              <AvGroup>
                <Label id="departamentLabel" for="employee-departament">
                  Departament
                </Label>
                <AvField id="employee-departament" type="text" name="departament" />
              </AvGroup>
              <AvGroup>
                <Label id="degreeLabel" for="employee-degree">
                  Degree
                </Label>
                <AvField id="employee-degree" type="text" name="degree" />
              </AvGroup>
              <AvGroup>
                <Label id="localidadLabel" for="employee-localidad">
                  Localidad
                </Label>
                <AvField id="employee-localidad" type="text" name="localidad" />
              </AvGroup>
              <AvGroup>
                <Label id="partidoLabel" for="employee-partido">
                  Partido
                </Label>
                <AvField id="employee-partido" type="text" name="partido" />
              </AvGroup>
              <AvGroup>
                <Label id="especializacionLabel" for="employee-especializacion">
                  Especializacion
                </Label>
                <AvField id="employee-especializacion" type="text" name="especializacion" />
              </AvGroup>
              <AvGroup>
                <Label id="celularLabel" for="employee-celular">
                  Celular
                </Label>
                <AvField id="employee-celular" type="text" name="celular" />
              </AvGroup>
              <AvGroup>
                <Label id="comentarioLabel" for="employee-comentario">
                  Comentario
                </Label>
                <AvField id="employee-comentario" type="text" name="comentario" />
              </AvGroup>
              <AvGroup>
                <Label id="createdAtLabel" for="employee-createdAt">
                  Created At
                </Label>
                <AvInput
                  id="employee-createdAt"
                  type="datetime-local"
                  className="form-control"
                  name="createdAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeEntity.createdAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedAtLabel" for="employee-updatedAt">
                  Updated At
                </Label>
                <AvInput
                  id="employee-updatedAt"
                  type="datetime-local"
                  className="form-control"
                  name="updatedAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeEntity.updatedAt)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/employee" replace color="info">
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
  employeeEntity: storeState.employee.entity,
  loading: storeState.employee.loading,
  updating: storeState.employee.updating,
  updateSuccess: storeState.employee.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeUpdate);
