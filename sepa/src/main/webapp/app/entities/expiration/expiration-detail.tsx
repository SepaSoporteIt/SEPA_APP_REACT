import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './expiration.reducer';
import { IExpiration } from 'app/shared/model/expiration.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExpirationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ExpirationDetail extends React.Component<IExpirationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { expirationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sepaApp.expiration.detail.title">Expiration</Translate> [<b>{expirationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="startDate">
                <Translate contentKey="sepaApp.expiration.startDate">Start Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={expirationEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">
                <Translate contentKey="sepaApp.expiration.endDate">End Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={expirationEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="status">
                <Translate contentKey="sepaApp.expiration.status">Status</Translate>
              </span>
            </dt>
            <dd>{expirationEntity.status}</dd>
            <dt>
              <span id="comments">
                <Translate contentKey="sepaApp.expiration.comments">Comments</Translate>
              </span>
            </dt>
            <dd>{expirationEntity.comments}</dd>
            <dt>
              <span id="uniqueCode">
                <Translate contentKey="sepaApp.expiration.uniqueCode">Unique Code</Translate>
              </span>
            </dt>
            <dd>{expirationEntity.uniqueCode}</dd>
            <dt>
              <span id="responsible">
                <Translate contentKey="sepaApp.expiration.responsible">Responsible</Translate>
              </span>
            </dt>
            <dd>{expirationEntity.responsible}</dd>
            <dt>
              <Translate contentKey="sepaApp.expiration.company">Company</Translate>
            </dt>
            <dd>{expirationEntity.company ? expirationEntity.company.fantasyName : ''}</dd>
            <dt>
              <Translate contentKey="sepaApp.expiration.employee">Employee</Translate>
            </dt>
            <dd>{expirationEntity.employee ? expirationEntity.employee.name : ''}</dd>
            <dt>
              <Translate contentKey="sepaApp.expiration.study">Study</Translate>
            </dt>
            <dd>{expirationEntity.study ? expirationEntity.study.name : ''}</dd>
          </dl>
          <Button tag={Link} to="/expiration" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/expiration/${expirationEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ expiration }: IRootState) => ({
  expirationEntity: expiration.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExpirationDetail);
