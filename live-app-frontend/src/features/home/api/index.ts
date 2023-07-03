/* eslint-disable @typescript-eslint/no-explicit-any */
import { Env } from 'config/Env'
import makeApi from 'libs/core/configureAxios'

import { Todo } from '../types'

const api = makeApi(`${Env.API_BASE_URL}`)
const TODO_BASE_URL = `/todos`

export const getTodos = (): Promise<Todo[]> => api.get(TODO_BASE_URL)
export const createTodo = (todo: Todo): Promise<Todo> => api.post(TODO_BASE_URL, todo)
