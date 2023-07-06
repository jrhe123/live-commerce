import { yupResolver } from '@hookform/resolvers/yup/dist/yup'
import { Icon } from '@iconify/react'
import { Box, Button, Drawer, IconButton, Typography } from '@mui/material'
import AppBar from '@mui/material/AppBar'
import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import { Link, Outlet, useNavigate, useLocation } from 'react-router-dom'
import * as Yup from 'yup'

import LogoImage from 'assets/images/livingroom.jpg'
import useWindowSize from 'hooks/useWindowSize'
import { FormTextField } from 'libs/ui/components/FormTextField'

const NAV_BAR_HEIGHT = 72
const BREAK_POINT = 600

type HeaderFormProps = {
  defaultValues?: SearchFormInput
  onSubmitClick(data: SearchFormInput): void
}

type SearchFormInput = {
  title: string
}

const Header = (props: HeaderFormProps) => {
  const navigate = useNavigate()
  const { pathname } = useLocation()
  const { width } = useWindowSize()
  const isMobile = width <= BREAK_POINT

  const {
    defaultValues = {
      title: '',
    },
    onSubmitClick,
  } = props

  const newPostValidationSchema = Yup.object().shape({
    title: Yup.string().required('title is required'),
  })

  const methods = useForm<SearchFormInput>({
    defaultValues,
    resolver: yupResolver(newPostValidationSchema),
  })
  const { handleSubmit, reset, control } = methods

  return (
    <>
      <AppBar
        position="sticky"
        sx={{
          background: 'white',
          position: 'relative',
        }}
        elevation={0}
      >
        {/* right side login */}
        <Box
          sx={{
            position: 'absolute',
            right: 0,
            top: 0,
            zIndex: 1,
            height: `${NAV_BAR_HEIGHT}px`,
            width: `${NAV_BAR_HEIGHT}px`,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
          }}
        >
          <IconButton onClick={() => {}}>
            <Icon icon={'teenyicons:users-solid'} style={{ fontSize: 21 }} />
          </IconButton>
        </Box>
        {/* nav bar */}
        <Box
          sx={{
            height: `${NAV_BAR_HEIGHT}px`,
            margin: '0 auto',
          }}
        >
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'row',
              justifyContent: 'space-around',
              height: '100%',
              maxWidth: BREAK_POINT,
            }}
          >
            {/* livestream */}
            <Box
              sx={{
                flex: 1,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100%',
              }}
            >
              <Link to={'/'}>
                <Box
                  component="img"
                  sx={{
                    width: '30px',
                    height: '27px',
                    cursor: 'pointer',
                    display: 'block',
                  }}
                  alt={'live stream'}
                  src={LogoImage}
                />
              </Link>
              <Button
                sx={{ height: '100%', paddingLeft: '24px', paddingRight: '24px' }}
                onClick={() => {
                  navigate('/')
                }}
              >
                <Typography
                  component="div"
                  sx={{
                    fontSize: '13px',
                    color: '#2c2c2b',
                    fontFamily: 'Roboto',
                    cursor: 'pointer',
                  }}
                >
                  Livestream
                </Typography>
              </Button>
            </Box>
            {/* pk */}
            <Box
              sx={{
                flex: 1,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100%',
              }}
            >
              <Button
                sx={{ height: '100%', paddingLeft: '24px', paddingRight: '24px' }}
                onClick={() => {
                  navigate('/')
                }}
              >
                <Typography
                  component="div"
                  sx={{
                    fontSize: '13px',
                    color: '#2c2c2b',
                    fontFamily: 'Roboto',
                  }}
                >
                  PK
                </Typography>
              </Button>
            </Box>
            {/* shop */}
            <Box
              sx={{
                flex: 1,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100%',
              }}
            >
              <Button
                sx={{ height: '100%', paddingLeft: '24px', paddingRight: '24px' }}
                onClick={() => {
                  navigate('/')
                }}
              >
                <Typography
                  component="div"
                  sx={{
                    fontSize: '13px',
                    color: '#2c2c2b',
                    fontFamily: 'Roboto',
                    cursor: 'pointer',
                  }}
                >
                  Shop
                </Typography>
              </Button>
            </Box>
            {/* sport */}
            <Box
              sx={{
                flex: 1,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100%',
              }}
            >
              <Button
                sx={{ height: '100%', paddingLeft: '24px', paddingRight: '24px' }}
                onClick={() => {
                  navigate('/')
                }}
              >
                <Typography
                  component="div"
                  sx={{
                    fontSize: '13px',
                    color: '#2c2c2b',
                    fontFamily: 'Roboto',
                    cursor: 'pointer',
                  }}
                >
                  Sport
                </Typography>
              </Button>
            </Box>
            {/* game */}
            <Box
              sx={{
                flex: 1,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100%',
              }}
            >
              <Button
                sx={{ height: '100%', paddingLeft: '24px', paddingRight: '24px' }}
                onClick={() => {
                  navigate('/')
                }}
              >
                <Typography
                  component="div"
                  sx={{
                    fontSize: '13px',
                    color: '#2c2c2b',
                    fontFamily: 'Roboto',
                    cursor: 'pointer',
                  }}
                >
                  Games
                </Typography>
              </Button>
            </Box>
            {/* search bar */}
            <Box
              sx={{
                flex: 1,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                marginLeft: '30px',
              }}
            >
              <FormTextField
                name="title"
                label={'Search..'}
                control={control}
                sx={{
                  background: '#f0f0f0',
                  color: '#2c2c2b',
                  borderRadius: '12px',
                  width: 120,
                  '& fieldset': { border: 'none' },
                  '& label': {
                    fontSize: '0.8rem;',
                    lineHeight: 1.57,
                  },
                  '.MuiInputBase-input': {
                    fontSize: '0.8rem',
                    '&:-webkit-autofill': {
                      borderRadius: '12px',
                    },
                  },
                }}
                type={'text'}
              />
            </Box>
          </Box>
        </Box>
      </AppBar>
      {/* page content outlet */}
      <Box
        sx={{
          position: 'relative',
          overflowY: 'auto',
          overflowX: 'hidden',
        }}
      >
        <Outlet />
      </Box>
    </>
  )
}

export default Header
