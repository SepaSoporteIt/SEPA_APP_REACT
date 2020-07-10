import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
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
          Employee [<b>{employeeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{employeeEntity.name}</dd>
          <dt>
            <span id="surname">Surname</span>
          </dt>
          <dd>{employeeEntity.surname}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{employeeEntity.email}</dd>
          <dt>
            <span id="tlf">Tlf</span>
          </dt>
          <dd>{employeeEntity.tlf}</dd>
          <dt>
            <span id="isInternal">Is Internal</span>
          </dt>
          <dd>{employeeEntity.isInternal ? 'true' : 'false'}</dd>
          <dt>
            <span id="matNumber">Mat Number</span>
          </dt>
          <dd>{employeeEntity.matNumber}</dd>
          <dt>
            <span id="cuit">Cuit</span>
          </dt>
          <dd>{employeeEntity.cuit}</dd>
          <dt>
            <span id="addressDirection">Address Direction</span>
          </dt>
          <dd>{employeeEntity.addressDirection}</dd>
          <dt>
            <span id="addressNumber">Address Number</span>
          </dt>
          <dd>{employeeEntity.addressNumber}</dd>
          <dt>
            <span id="floor">Floor</span>
          </dt>
          <dd>{employeeEntity.floor}</dd>
          <dt>
            <span id="departament">Departament</span>
          </dt>
          <dd>{employeeEntity.departament}</dd>
          <dt>
            <span id="degree">Degree</span>
          </dt>
          <dd>{employeeEntity.degree}</dd>
          <dt>
            <span id="localidad">Localidad</span>
          </dt>
          <dd>{employeeEntity.localidad}</dd>
          <dt>
            <span id="partido">Partido</span>
          </dt>
          <dd>{employeeEntity.partido}</dd>
          <dt>
            <span id="especializacion">Especializacion</span>
          </dt>
          <dd>{employeeEntity.especializacion}</dd>
          <dt>
            <span id="celular">Celular</span>
          </dt>
          <dd>{employeeEntity.celular}</dd>
          <dt>
            <span id="comentario">Comentario</span>
          </dt>
          <dd>{employeeEntity.comentario}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{employeeEntity.createdAt ? <TextFormat value={employeeEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{employeeEntity.updatedAt ? <TextFormat value={employeeEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employee }: IRootState) => ({
  employeeEntity: employee.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDetail);
