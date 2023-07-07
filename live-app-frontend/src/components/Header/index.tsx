import { Icon } from '@iconify/react'
import { Box, Button, Drawer, IconButton, Typography, Modal } from '@mui/material'
import AppBar from '@mui/material/AppBar'
import React, { useState } from 'react'
import { Link, Outlet, useNavigate, useLocation } from 'react-router-dom'

import LogoImage from 'assets/images/livingroom.jpg'
import useWindowSize from 'hooks/useWindowSize'

import SearchForm from './SearchForm'
import SigninForm from './SigninForm'

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
  const [open, setOpen] = useState<boolean>(false)

  const { pathname } = useLocation()
  const { width } = useWindowSize()
  const isMobile = width <= BREAK_POINT

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
                marginLeft: '24px',
              }}
            >
              <SearchForm onSubmitClick={() => {}} />
            </Box>
          </Box>
        </Box>
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
          <IconButton
            onClick={() => {
              setOpen(true)
            }}
          >
            <Icon icon={'arcticons:bigo-live'} style={{ fontSize: 36 }} />
          </IconButton>
        </Box>
      </AppBar>
      {/* popup modal */}
      <Modal open={open} aria-labelledby="signin-modal" aria-describedby="user-signin">
        <Box
          sx={{
            justifyContent: 'center',
            alignItems: 'center',
            display: 'flex',
            width: '100vw',
            height: '100vh',
            backdropFilter: 'blur(3px)',
          }}
        >
          <Box
            sx={{
              background: '#fff',
              width: '400px',
              padding: '18px',
              position: 'relative',
            }}
          >
            <Box
              sx={{
                position: 'absolute',
                right: 6,
                top: 6,
                zIndex: 1,
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'center',
              }}
            >
              <IconButton
                onClick={() => {
                  setOpen(false)
                }}
                sx={{ padding: '3px' }}
              >
                <Icon icon={'material-symbols:close'} style={{ fontSize: 21 }} />
              </IconButton>
            </Box>
            <Box
              sx={{
                display: 'flex',
                flexDirection: 'row',
                alignItems: 'center',
                marginBottom: '24px',
              }}
            >
              <Box
                component="img"
                sx={{
                  width: '30px',
                  height: '27px',
                  display: 'block',
                  marginRight: '12px',
                }}
                alt={'live stream'}
                src={LogoImage}
              />
              <Typography variant="subtitle1" component="div" sx={{ fontWeight: 'bold' }}>
                Sign In
              </Typography>
            </Box>
            <SigninForm onSubmitClick={() => {}} />
          </Box>
        </Box>
      </Modal>
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
