import { SagaIterator } from '@redux-saga/core'
import { call, put, takeEvery } from 'redux-saga/effects'

import { createTodo, getTodos } from 'features/home/api'
import { homeActions } from 'features/home/store/home.slice'
import { Todo } from 'features/home/types'

// Worker Sagas
export function* onGetTodos(): SagaIterator {
  const todos: Todo[] = yield call(getTodos)
  yield put(homeActions.fetchAllSucceeded(todos))
}

function* onCreateTodo({
  payload,
}: {
  type: typeof homeActions.create
  payload: Todo
}): SagaIterator {
  yield call(createTodo, payload)
  yield put(homeActions.fetchAll())
}

// Watcher Saga
export function* homeWatcherSaga(): SagaIterator {
  yield takeEvery(homeActions.fetchAll.type, onGetTodos)
  yield takeEvery(homeActions.create.type, onCreateTodo)
}

export default homeWatcherSaga
