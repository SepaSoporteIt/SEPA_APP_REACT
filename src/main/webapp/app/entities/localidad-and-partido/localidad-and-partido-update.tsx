import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './localidad-and-partido.reducer';
import { ILocalidadAndPartido } from 'app/shared/model/localidad-and-partido.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILocalidadAndPartidoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LocalidadAndPartidoUpdate = (props: ILocalidadAndPartidoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { localidadAndPartidoEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/localidad-and-partido' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...localidadAndPartidoEntity,
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
          <h2 id="sepaApp.localidadAndPartido.home.createOrEditLabel">
            <Translate contentKey="sepaApp.localidadAndPartido.home.createOrEditLabel">Create or edit a LocalidadAndPartido</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : localidadAndPartidoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="localidad-and-partido-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="localidad-and-partido-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="localidadLabel" for="localidad-and-partido-localidad">
                  <Translate contentKey="sepaApp.localidadAndPartido.localidad">Localidad</Translate>
                </Label>
                <AvField id="localidad-and-partido-localidad" type="text" name="localidad" />
              </AvGroup>
              <AvGroup>
                <Label id="partidoLabel" for="localidad-and-partido-partido">
                  <Translate contentKey="sepaApp.localidadAndPartido.partido">Partido</Translate>
                </Label>
                <AvField id="localidad-and-partido-partido" type="text" name="partido" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/localidad-and-partido" replace color="info">
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
  localidadAndPartidoEntity: storeState.localidadAndPartido.entity,
  loading: storeState.localidadAndPartido.loading,
  updating: storeState.localidadAndPartido.updating,
  updateSuccess: storeState.localidadAndPartido.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LocalidadAndPartidoUpdate);
