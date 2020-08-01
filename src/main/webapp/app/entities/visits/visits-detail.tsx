import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './visits.reducer';
import { IVisits } from 'app/shared/model/visits.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVisitsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VisitsDetail = (props: IVisitsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { visitsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="sepaApp.visits.detail.title">Visits</Translate> [<b>{visitsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="employee">
              <Translate contentKey="sepaApp.visits.employee">Employee</Translate>
            </span>
          </dt>
          <dd>{visitsEntity.employee}</dd>
          <dt>
            <span id="visit_date">
              <Translate contentKey="sepaApp.visits.visit_date">Visit Date</Translate>
            </span>
          </dt>
          <dd>
            {visitsEntity.visit_date ? <TextFormat value={visitsEntity.visit_date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="sepaApp.visits.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{visitsEntity.comments}</dd>
          <dt>
            <Translate contentKey="sepaApp.visits.company">Company</Translate>
          </dt>
          <dd>{visitsEntity.company ? visitsEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/visits" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/visits/${visitsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ visits }: IRootState) => ({
  visitsEntity: visits.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VisitsDetail);
