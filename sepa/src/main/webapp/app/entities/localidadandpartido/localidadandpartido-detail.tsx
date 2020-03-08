import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './localidadandpartido.reducer';
import { ILocalidadandpartido } from 'app/shared/model/localidadandpartido.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILocalidadandpartidoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LocalidadandpartidoDetail = (props: ILocalidadandpartidoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { localidadandpartidoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="sepaApp.localidadandpartido.detail.title">Localidadandpartido</Translate> [
          <b>{localidadandpartidoEntity.localidad + " - " + localidadandpartidoEntity.partido}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="localidad">
              <Translate contentKey="sepaApp.localidadandpartido.localidad">Localidad</Translate>
            </span>
          </dt>
          <dd>{localidadandpartidoEntity.localidad}</dd>
          <dt>
            <span id="partido">
              <Translate contentKey="sepaApp.localidadandpartido.partido">Partido</Translate>
            </span>
          </dt>
          <dd>{localidadandpartidoEntity.partido}</dd>
        </dl>
        <Button tag={Link} to="/localidadandpartido" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/localidadandpartido/${localidadandpartidoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ localidadandpartido }: IRootState) => ({
  localidadandpartidoEntity: localidadandpartido.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LocalidadandpartidoDetail);
