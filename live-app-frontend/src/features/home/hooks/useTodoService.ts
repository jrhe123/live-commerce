import { useCallback } from 'react'

import { homeActions, selectTodos } from 'features/home/store'
import { Todo, TodoFormInput } from 'features/home/types'
import { useAppDispatch, useAppSelector } from 'store/hooks'

export type TodoServiceOperators = {
  todos: Todo[]
  createTodo: (data: TodoFormInput) => void
}

/**
 * custom-hooks
 * @see https://reactjs.org/docs/hooks-custom.html
 */
export const useHomeService = (): Readonly<TodoServiceOperators> => {
  const dispatch = useAppDispatch()
  return {
    todos: useAppSelector(selectTodos),
    createTodo: useCallback(
      (todo: TodoFormInput) => {
        dispatch(homeActions.create({ title: todo.title }))
      },
      [dispatch],
    ),
  }
}

export default useHomeService
