import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeDetail = (props: IEmployeeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="sepaApp.employee.detail.title">Employee</Translate> [<b>{employeeEntity.name + " " + employeeEntity.surname}</b>]
        </h2>
        <dl className="jh-entity-details">
          <h3>
            <Translate contentKey="sepaApp.employee.detail.personalData">Personal Data</Translate>
          </h3>
          <dt>
            <span id="name">
              <Translate contentKey="sepaApp.employee.name">Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.name}</dd>
          <dt>
            <span id="surname">
              <Translate contentKey="sepaApp.employee.surname">Surname</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.surname}</dd>
          <dt>
            <span id="tlf">
              <Translate contentKey="sepaApp.employee.tlf">Tlf</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.tlf}</dd>
          <dt>
            <span id="celular">
              <Translate contentKey="sepaApp.employee.celular">Celular</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.celular}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="sepaApp.employee.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.email}</dd>
          
          <h3>
            <Translate contentKey="sepaApp.employee.detail.matData">Tuition Data</Translate>
          </h3>
          <dt>
            <span id="matNumber">
              <Translate contentKey="sepaApp.employee.matNumber">Mat Number</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.matNumber}</dd>
          <dt>
            <span id="cuit">
              <Translate contentKey="sepaApp.employee.cuit">Cuit</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.cuit}</dd>
          <dt>
            <span id="degree">
              <Translate contentKey="sepaApp.employee.degree">Degree</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.degree}</dd>
          <dt>
            <span id="especializacion">
              <Translate contentKey="sepaApp.employee.especializacion">Especializacion</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.especializacion}</dd>

          <h3>
            <Translate contentKey="sepaApp.employee.detail.addressData">Address Data</Translate>
          </h3>
          <dt>
            <span id="addressDirection">
              <Translate contentKey="sepaApp.employee.addressDirection">Address Direction</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.addressDirection}</dd>
          <dt>
            <span id="addressNumber">
              <Translate contentKey="sepaApp.employee.addressNumber">Address Number</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.addressNumber}</dd>
          <dt>
            <span id="floor">
              <Translate contentKey="sepaApp.employee.floor">Floor</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.floor}</dd>
          <dt>
            <span id="departament">
              <Translate contentKey="sepaApp.employee.departament">Departament</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.departament}</dd>
          <dt>
            <Translate contentKey="sepaApp.employee.localidadId">Localidad Id</Translate>
          </dt>
          <dd>{employeeEntity.localidadId ? employeeEntity.localidadId.localidad : ''}</dd>
          <dt>
            <Translate contentKey="sepaApp.employee.partidoId">Partido Id</Translate>
          </dt>
          <dd>{employeeEntity.partidoId ? employeeEntity.partidoId.partido : ''}</dd>

          <h3>
            <Translate contentKey="sepaApp.employee.detail.others">Others</Translate>
          </h3>
          <dt>
            <span id="isInternal">
              <Translate contentKey="sepaApp.employee.isInternal">Is Internal</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.isInternal ? 'Si' : 'No'}</dd>
          <dt>
            <span id="comentario">
              <Translate contentKey="sepaApp.employee.comentario">Comentario</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.comentario}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="sepaApp.employee.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={employeeEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="sepaApp.employee.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={employeeEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
          </dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employee }: IRootState) => ({
  employeeEntity: employee.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDetail);
