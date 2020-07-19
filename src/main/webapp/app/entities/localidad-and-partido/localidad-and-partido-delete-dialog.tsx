import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ILocalidadAndPartido } from 'app/shared/model/localidad-and-partido.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './localidad-and-partido.reducer';

export interface ILocalidadAndPartidoDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LocalidadAndPartidoDeleteDialog = (props: ILocalidadAndPartidoDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/localidad-and-partido' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.localidadAndPartidoEntity.id);
  };

  const { localidadAndPartidoEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="sepaApp.localidadAndPartido.delete.question">
        <Translate contentKey="sepaApp.localidadAndPartido.delete.question" interpolate={{ id: localidadAndPartidoEntity.id }}>
          Are you sure you want to delete this LocalidadAndPartido?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-localidadAndPartido" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ localidadAndPartido }: IRootState) => ({
  localidadAndPartidoEntity: localidadAndPartido.entity,
  updateSuccess: localidadAndPartido.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LocalidadAndPartidoDeleteDialog);
