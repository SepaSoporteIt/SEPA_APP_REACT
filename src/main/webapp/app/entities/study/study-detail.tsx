import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './study.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StudyDetail = (props: IStudyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { studyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Study [<b>{studyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{studyEntity.name}</dd>
          <dt>
            <span id="resolution">Resolution</span>
          </dt>
          <dd>{studyEntity.resolution}</dd>
          <dt>
            <span id="legislation">Legislation</span>
          </dt>
          <dd>{studyEntity.legislation}</dd>
        </dl>
        <Button tag={Link} to="/study" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/study/${studyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ study }: IRootState) => ({
  studyEntity: study.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StudyDetail);
