import { all, fork } from 'redux-saga/effects'

import { homeWatcherSaga } from 'features/home/store/home.sagas'

export function* rootSaga() {
  // list of saga
  yield all([fork(homeWatcherSaga)])
}

export default rootSaga
