/* eslint-disable @typescript-eslint/no-explicit-any */

import { rest } from 'msw'

import Env from 'config/Env'
import { db, persistDb } from 'test/msw/db'

const BASE_URL = `${Env.API_BASE_URL}/posts`

export const postsHandlers = [
  rest.get(BASE_URL, (req, res, ctx) => {
    try {
      const result = db.posts.getAll()
      return res(ctx.json(result))
    } catch (error: any) {
      return res(ctx.status(400), ctx.json({ message: error?.message || 'Server Error' }))
    }
  }),

  rest.get(`${BASE_URL}/:postId`, (req, res, ctx) => {
    try {
      const { postId } = req.params
      const result = db.posts.findFirst({
        where: {
          id: {
            equals: postId,
          },
        },
      })
      return res(ctx.json(result))
    } catch (error: any) {
      return res(ctx.status(400), ctx.json({ message: error?.message || 'Server Error' }))
    }
  }),
]
