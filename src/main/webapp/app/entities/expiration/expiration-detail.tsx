import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './expiration.reducer';
import { IExpiration } from 'app/shared/model/expiration.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExpirationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExpirationDetail = (props: IExpirationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { expirationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Expiration [<b>{expirationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>
            {expirationEntity.startDate ? (
              <TextFormat value={expirationEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {expirationEntity.endDate ? <TextFormat value={expirationEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{expirationEntity.status}</dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{expirationEntity.comments}</dd>
          <dt>Company</dt>
          <dd>{expirationEntity.company ? expirationEntity.company.fantasyName : ''}</dd>
          <dt>Employee</dt>
          <dd>{expirationEntity.employee ? expirationEntity.employee.name : ''}</dd>
          <dt>Study</dt>
          <dd>{expirationEntity.study ? expirationEntity.study.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/expiration" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/expiration/${expirationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ expiration }: IRootState) => ({
  expirationEntity: expiration.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExpirationDetail);
