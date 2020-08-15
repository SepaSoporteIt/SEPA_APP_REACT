import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/company">
      <Translate contentKey="global.menu.entities.company" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/industry-type">
      <Translate contentKey="global.menu.entities.industryType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee">
      <Translate contentKey="global.menu.entities.employee" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/expiration">
      <Translate contentKey="global.menu.entities.expiration" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/study">
      <Translate contentKey="global.menu.entities.study" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/localidad-and-partido">
      <Translate contentKey="global.menu.entities.localidad-and-partido" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/visits">
      <Translate contentKey="global.menu.entities.visits" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
