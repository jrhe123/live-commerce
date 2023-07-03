import axios from 'axios'

const ACCESS_TOKEN = process.env.REACT_APP_ACCESS_TOKEN || 'accessToken'
export default function makeApi(baseURL: string) {
  const api = axios.create({
    baseURL,
  })

  const token = localStorage.getItem(ACCESS_TOKEN)
  // set content-type
  api.defaults.headers.post['Content-Type'] = 'application/json'
  api.defaults.headers.put['Content-Type'] = 'application/json'
  api.defaults.headers.delete['Content-Type'] = 'application/json'

  api.interceptors.request.use(
    config => {
      if (token) {
        config.headers = {
          ...config.headers,
          Authorization: `Bearer ${token}`,
        }
      }
      return config
    },
    error => Promise.reject(error),
  )
  api.interceptors.response.use(
    response => response.data, // return data object
    error => Promise.reject(error),
  )
  return api
}
