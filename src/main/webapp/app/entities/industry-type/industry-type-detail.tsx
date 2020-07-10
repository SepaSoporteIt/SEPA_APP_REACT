import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './industry-type.reducer';
import { IIndustryType } from 'app/shared/model/industry-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIndustryTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const IndustryTypeDetail = (props: IIndustryTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { industryTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          IndustryType [<b>{industryTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{industryTypeEntity.name}</dd>
          <dt>
            <span id="ciiu">Ciiu</span>
          </dt>
          <dd>{industryTypeEntity.ciiu}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {industryTypeEntity.createdAt ? <TextFormat value={industryTypeEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {industryTypeEntity.updatedAt ? <TextFormat value={industryTypeEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/industry-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/industry-type/${industryTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ industryType }: IRootState) => ({
  industryTypeEntity: industryType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IndustryTypeDetail);
