import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './industry-type.reducer';
import { IIndustryType } from 'app/shared/model/industry-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIndustryTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class IndustryTypeDetail extends React.Component<IIndustryTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { industryTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sepaApp.industryType.detail.title">IndustryType</Translate> [<b>{industryTypeEntity.name}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sepaApp.industryType.name">Name</Translate>
              </span>
            </dt>
            <dd>{industryTypeEntity.name}</dd>
            <dt>
              <span id="ciiu">
                <Translate contentKey="sepaApp.industryType.ciiu">Ciiu</Translate>
              </span>
            </dt>
            <dd>{industryTypeEntity.ciiu}</dd>
            <dt>
              <span id="createdAt">
                <Translate contentKey="sepaApp.industryType.createdAt">Created At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={industryTypeEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updatedAt">
                <Translate contentKey="sepaApp.industryType.updatedAt">Updated At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={industryTypeEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/industry-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/industry-type/${industryTypeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ industryType }: IRootState) => ({
  industryTypeEntity: industryType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(IndustryTypeDetail);
