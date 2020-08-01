import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import company, {
  CompanyState
} from 'app/entities/company/company.reducer';
// prettier-ignore
import industryType, {
  IndustryTypeState
} from 'app/entities/industry-type/industry-type.reducer';
// prettier-ignore
import employee, {
  EmployeeState
} from 'app/entities/employee/employee.reducer';
// prettier-ignore
import expiration, {
  ExpirationState
} from 'app/entities/expiration/expiration.reducer';
// prettier-ignore
import study, {
  StudyState
} from 'app/entities/study/study.reducer';
// prettier-ignore
import localidadAndPartido, {
  LocalidadAndPartidoState
} from 'app/entities/localidad-and-partido/localidad-and-partido.reducer';
// prettier-ignore
import visits, {
  VisitsState
} from 'app/entities/visits/visits.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly company: CompanyState;
  readonly industryType: IndustryTypeState;
  readonly employee: EmployeeState;
  readonly expiration: ExpirationState;
  readonly study: StudyState;
  readonly localidadAndPartido: LocalidadAndPartidoState;
  readonly visits: VisitsState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  company,
  industryType,
  employee,
  expiration,
  study,
  localidadAndPartido,
  visits,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
