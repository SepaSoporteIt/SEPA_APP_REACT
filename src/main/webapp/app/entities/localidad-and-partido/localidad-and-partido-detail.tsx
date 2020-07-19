import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './localidad-and-partido.reducer';
import { ILocalidadAndPartido } from 'app/shared/model/localidad-and-partido.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILocalidadAndPartidoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LocalidadAndPartidoDetail = (props: ILocalidadAndPartidoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { localidadAndPartidoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="sepaApp.localidadAndPartido.detail.title">LocalidadAndPartido</Translate> [
          <b>{localidadAndPartidoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="localidad">
              <Translate contentKey="sepaApp.localidadAndPartido.localidad">Localidad</Translate>
            </span>
          </dt>
          <dd>{localidadAndPartidoEntity.localidad}</dd>
          <dt>
            <span id="partido">
              <Translate contentKey="sepaApp.localidadAndPartido.partido">Partido</Translate>
            </span>
          </dt>
          <dd>{localidadAndPartidoEntity.partido}</dd>
        </dl>
        <Button tag={Link} to="/localidad-and-partido" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/localidad-and-partido/${localidadAndPartidoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ localidadAndPartido }: IRootState) => ({
  localidadAndPartidoEntity: localidadAndPartido.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LocalidadAndPartidoDetail);
