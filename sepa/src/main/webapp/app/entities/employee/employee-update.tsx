import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmployeeUpdateState {
  isNew: boolean;
}

export class EmployeeUpdate extends React.Component<IEmployeeUpdateProps, IEmployeeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    if (errors.length === 0) {
      const { employeeEntity } = this.props;
      const entity = {
        ...employeeEntity,
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
    this.props.history.push('/employee');
  };

  render() {
    const { employeeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sepaApp.employee.home.createOrEditLabel">
              <Translate contentKey="sepaApp.employee.home.createOrEditLabel">Create or edit a Employee</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : employeeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="employee-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="employee-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="employee-name">
                    <Translate contentKey="sepaApp.employee.name">Name</Translate>
                  </Label>
                  <AvField
                    id="employee-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="surnameLabel" for="employee-surname">
                    <Translate contentKey="sepaApp.employee.surname">Surname</Translate>
                  </Label>
                  <AvField
                    id="employee-surname"
                    type="text"
                    name="surname"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="employee-email">
                    <Translate contentKey="sepaApp.employee.email">Email</Translate>
                  </Label>
                  <AvField id="employee-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label id="tlfLabel" for="employee-tlf">
                    <Translate contentKey="sepaApp.employee.tlf">Tlf</Translate>
                  </Label>
                  <AvField id="employee-tlf" type="text" name="tlf" />
                </AvGroup>
                <AvGroup>
                  <Label id="tipoLabel" check>
                    <AvInput id="employee-tipo" type="checkbox" className="form-control" name="tipo" />
                    <Translate contentKey="sepaApp.employee.tipo">Tipo</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="matNumberLabel" for="employee-matNumber">
                    <Translate contentKey="sepaApp.employee.matNumber">Mat Number</Translate>
                  </Label>
                  <AvField
                    id="employee-matNumber"
                    type="text"
                    name="matNumber"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="cuitLabel" for="employee-cuit">
                    <Translate contentKey="sepaApp.employee.cuit">Cuit</Translate>
                  </Label>
                  <AvField
                    id="employee-cuit"
                    type="text"
                    name="cuit"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="addressDirectionLabel" for="employee-addressDirection">
                    <Translate contentKey="sepaApp.employee.addressDirection">Address Direction</Translate>
                  </Label>
                  <AvField id="employee-addressDirection" type="text" name="addressDirection" />
                </AvGroup>
                <AvGroup>
                  <Label id="addressNumberLabel" for="employee-addressNumber">
                    <Translate contentKey="sepaApp.employee.addressNumber">Address Number</Translate>
                  </Label>
                  <AvField id="employee-addressNumber" type="text" name="addressNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="floorLabel" for="employee-floor">
                    <Translate contentKey="sepaApp.employee.floor">Floor</Translate>
                  </Label>
                  <AvField id="employee-floor" type="text" name="floor" />
                </AvGroup>
                <AvGroup>
                  <Label id="departamentLabel" for="employee-departament">
                    <Translate contentKey="sepaApp.employee.departament">Departament</Translate>
                  </Label>
                  <AvField id="employee-departament" type="text" name="departament" />
                </AvGroup>
                <AvGroup>
                  <Label id="degreeLabel" for="employee-degree">
                    <Translate contentKey="sepaApp.employee.degree">Degree</Translate>
                  </Label>
                  <AvField id="employee-degree" type="text" name="degree" />
                </AvGroup>
                <AvGroup>
                  <Label id="localidadLabel" for="employee-localidad">
                    <Translate contentKey="sepaApp.employee.localidad">Localidad</Translate>
                  </Label>
                  <AvField id="employee-localidad" type="text" name="localidad" />
                </AvGroup>
                <AvGroup>
                  <Label id="partidoLabel" for="employee-partido">
                    <Translate contentKey="sepaApp.employee.partido">Partido</Translate>
                  </Label>
                  <AvField id="employee-partido" type="text" name="partido" />
                </AvGroup>
                <AvGroup>
                  <Label id="especializacionLabel" for="employee-especializacion">
                    <Translate contentKey="sepaApp.employee.especializacion">Especializacion</Translate>
                  </Label>
                  <AvField id="employee-especializacion" type="text" name="especializacion" />
                </AvGroup>
                <AvGroup>
                  <Label id="celularLabel" for="employee-celular">
                    <Translate contentKey="sepaApp.employee.celular">Celular</Translate>
                  </Label>
                  <AvField id="employee-celular" type="text" name="celular" />
                </AvGroup>
                <AvGroup>
                  <Label id="comentarioLabel" for="employee-comentario">
                    <Translate contentKey="sepaApp.employee.comentario">Comentario</Translate>
                  </Label>
                  <AvField id="employee-comentario" type="text" name="comentario" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="employee-createdAt">
                    <Translate contentKey="sepaApp.employee.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="employee-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.employeeEntity.createdAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedAtLabel" for="employee-updatedAt">
                    <Translate contentKey="sepaApp.employee.updatedAt">Updated At</Translate>
                  </Label>
                  <AvInput
                    id="employee-updatedAt"
                    type="datetime-local"
                    className="form-control"
                    name="updatedAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.employeeEntity.updatedAt)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/employee" replace color="info">
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
  employeeEntity: storeState.employee.entity,
  loading: storeState.employee.loading,
  updating: storeState.employee.updating,
  updateSuccess: storeState.employee.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeeUpdate);
