// DUCKS pattern
import { createAction, createSlice, nanoid, PayloadAction } from '@reduxjs/toolkit'

import { Todo } from 'features/home/types'
import type { RootState } from 'store/store'

export interface HomeState {
  isLoading: boolean
  todos: Todo[]
  errors?: Error[]
}

const initialState: HomeState = {
  isLoading: false,
  todos: [],
  errors: [],
}

// slice
export const homeSlice = createSlice({
  name: 'home',
  initialState,
  reducers: {
    fetchAllSucceeded(state, action: PayloadAction<Todo[]>) {
      state.todos = action.payload
    },
  },
})

// Actions
export const homeActions = {
  create: createAction(`${homeSlice.name}/create`, (todo: Todo) => ({
    payload: { id: nanoid(), title: todo.title },
  })),
  fetchAll: createAction(`${homeSlice.name}/fetchAll`),
  fetchAllSucceeded: homeSlice.actions.fetchAllSucceeded,
}

// Selectors
export const selectTodos = (state: RootState) => state.home.todos

// Reducer
export default homeSlice.reducer
