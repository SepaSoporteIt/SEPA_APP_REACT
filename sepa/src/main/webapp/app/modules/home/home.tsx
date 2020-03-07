import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      <Col md="9">
        <h2>
          <Translate contentKey="home.title">Welcome, Java Hipster!</Translate>
        </h2>
        <p className="lead">
          <Translate contentKey="home.subtitle">This is your homepage</Translate>
        </p>
        {account && account.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </div>
        )}
        <p>
          <Translate contentKey="home.follow">Follow us!:</Translate>
        </p>

        <ul>
          <li>
            <a href="http://www.gruposepa.com.ar/" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.homepage">SEPA homepage</Translate>
            </a>
          </li>
          <li>
            <a href="https://www.facebook.com/gruposepaarg/" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.facebook">SEPA on Facebook</Translate>
            </a>
          </li>
          <li>
            <a href="https://twitter.com/Gruposepa" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.twitter">SEPA on Twitter</Translate>
            </a>
          </li>
          <li>
            <a href="https://www.linkedin.com/company/s-e-p-a-y-asociados-s-r-l-" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.linkedin">SEPA on LinkedIn</Translate>
            </a>
          </li>
        </ul>

        {account && account.login ? (
          <ul>
            <li>
              <a href="https://server:8080/mantisbt" target="_blank" rel="noopener noreferrer">
                <Translate contentKey="home.link.mantis">If you have a problem with the app, let us know</Translate>
              </a>
            </li>
            <li>
              <a href="https://server:8080/sepawiki" target="_blank" rel="noopener noreferrer">
                <Translate contentKey="home.link.sepawiki">Information about the multiples applications used on SEPA</Translate>
              </a>
            </li>
          </ul>
        ): (
          <ul>

          </ul>
        )}

      </Col>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
