import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './study.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StudyDetail extends React.Component<IStudyDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { studyEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sepaApp.study.detail.title">Study</Translate> [<b>{studyEntity.name}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sepaApp.study.name">Name</Translate>
              </span>
            </dt>
            <dd>{studyEntity.name}</dd>
            <dt>
              <span id="resolution">
                <Translate contentKey="sepaApp.study.resolution">Resolution</Translate>
              </span>
            </dt>
            <dd>{studyEntity.resolution}</dd>
            <dt>
              <span id="legislation">
                <Translate contentKey="sepaApp.study.legislation">Legislation</Translate>
              </span>
            </dt>
            <dd>{studyEntity.legislation}</dd>
          </dl>
          <Button tag={Link} to="/study" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/study/${studyEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ study }: IRootState) => ({
  studyEntity: study.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudyDetail);
