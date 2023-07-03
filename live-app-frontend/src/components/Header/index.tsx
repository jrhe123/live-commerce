import CloseIcon from '@mui/icons-material/Close'
import MenuIcon from '@mui/icons-material/Menu'
import { Box, Button, Drawer, IconButton, Typography } from '@mui/material'
import AppBar from '@mui/material/AppBar'
import React, { useState } from 'react'
import { Link, Outlet, useNavigate, useLocation } from 'react-router-dom'

import { useScrollPosition } from 'hooks/useScrollPosition'
import useWindowSize from 'hooks/useWindowSize'

const LOGO_BAR_HEIGHT = 190
const NAV_BAR_HEIGHT = 25
const FOOTER_HEIGHT = 42
const BREAK_POINT = 600
const BUTTON_SIZE = 60
const SM_BUTTON_SIZE = 42

const Header = () => {
  const navigate = useNavigate()
  const [open, setOpen] = useState<boolean>(false)
  const [collapse, setCollapse] = useState<boolean>(true)
  const [miniHeader, setMiniHeader] = useState<boolean>(false)
  const { pathname } = useLocation()
  const { width } = useWindowSize()
  const isMobile = width <= BREAK_POINT

  useScrollPosition(({ prevPos, currPos }) => {
    if (currPos.y < 0) {
      setMiniHeader(true)
    } else {
      setMiniHeader(false)
    }
  })

  let isHome = true
  let isAbout = false
  let isContact = false
  if (pathname.includes('about')) {
    isHome = false
    isAbout = true
  } else if (pathname.includes('contact')) {
    isHome = false
    isContact = true
  }

  return (
    <>
      {isMobile && (
        <Box
          sx={{
            position: 'relative',
          }}
        >
          <Drawer
            anchor={'left'}
            open={open}
            onClose={() => {
              setOpen(false)
            }}
          >
            <Box
              sx={{
                mb: '24px',
                mt: '60px',
                width: '100vw',
              }}
            >
              <Typography
                component="div"
                sx={{
                  fontSize: '18px',
                  color: isAbout ? '#fa708a' : '#98bf71',
                  fontFamily: 'Roboto',
                  fontWeight: isAbout ? 'bold' : 200,
                  textAlign: 'center',
                  cursor: 'pointer',
                }}
                onClick={() => {
                  setOpen(false)
                  if (isAbout) return
                  navigate('/about')
                }}
              >
                About
              </Typography>
            </Box>
            <Box sx={{ mb: '24px' }}>
              <Typography
                component="div"
                sx={{
                  fontSize: '18px',
                  color: isHome ? '#fa708a' : '#98bf71',
                  fontFamily: 'Roboto',
                  fontWeight: isHome ? 'bold' : 200,
                  textAlign: 'center',
                  cursor: 'pointer',
                }}
                onClick={() => {
                  setOpen(false)
                  if (pathname !== '/') {
                    navigate('/')
                  }
                }}
              >
                <a
                  onClick={e => {
                    e.stopPropagation()
                    setCollapse(!collapse)
                  }}
                >
                  {!collapse ? '—' : '+'}
                </a>
                &nbsp;&nbsp;&nbsp;&nbsp;Work
              </Typography>
            </Box>
            {!collapse && (
              <>
                <Box sx={{ mb: '24px' }}>
                  <Typography
                    component="div"
                    sx={{
                      fontSize: '14px',
                      color: pathname === '/thesis' ? '#fa708a' : '#98bf71',
                      fontFamily: 'Roboto',
                      fontWeight: pathname === '/thesis' ? 'bold' : 200,
                      textAlign: 'center',
                      cursor: 'pointer',
                    }}
                    onClick={() => {
                      setOpen(false)
                      if (pathname !== '/thesis') {
                        navigate('/thesis')
                      }
                    }}
                  >
                    Thesis
                  </Typography>
                </Box>
                <Box sx={{ mb: '24px' }}>
                  <Typography
                    component="div"
                    sx={{
                      fontSize: '14px',
                      color: pathname === '/editorial' ? '#fa708a' : '#98bf71',
                      fontFamily: 'Roboto',
                      fontWeight: pathname === '/editorial' ? 'bold' : 200,
                      textAlign: 'center',
                      cursor: 'pointer',
                    }}
                    onClick={() => {
                      setOpen(false)
                      if (pathname !== '/editorial') {
                        navigate('/editorial')
                      }
                    }}
                  >
                    Illustrations
                  </Typography>
                </Box>
                <Box sx={{ mb: '24px' }}>
                  <Typography
                    component="div"
                    sx={{
                      fontSize: '14px',
                      color: pathname === '/portrait' ? '#fa708a' : '#98bf71',
                      fontFamily: 'Roboto',
                      fontWeight: pathname === '/portrait' ? 'bold' : 200,
                      textAlign: 'center',
                      cursor: 'pointer',
                    }}
                    onClick={() => {
                      setOpen(false)
                      if (pathname !== '/portrait') {
                        navigate('/portrait')
                      }
                    }}
                  >
                    Portrait
                  </Typography>
                </Box>
                <Box sx={{ mb: '24px' }}>
                  <Typography
                    component="div"
                    sx={{
                      fontSize: '14px',
                      color: pathname === '/personal' ? '#fa708a' : '#98bf71',
                      fontFamily: 'Roboto',
                      fontWeight: pathname === '/personal' ? 'bold' : 200,
                      textAlign: 'center',
                      cursor: 'pointer',
                    }}
                    onClick={() => {
                      setOpen(false)
                      if (pathname !== '/personal') {
                        navigate('/personal')
                      }
                    }}
                  >
                    Personal
                  </Typography>
                </Box>
              </>
            )}
            <Box>
              <Typography
                component="div"
                sx={{
                  fontSize: '18px',
                  color: isContact ? '#fa708a' : '#98bf71',
                  fontFamily: 'Roboto',
                  fontWeight: isContact ? 'bold' : 200,
                  textAlign: 'center',
                  cursor: 'pointer',
                }}
                onClick={() => {
                  setOpen(false)
                  if (isContact) return
                  navigate('/contact')
                }}
              >
                Contact
              </Typography>
            </Box>
            {open && (
              <Box
                sx={{
                  position: 'absolute',
                  right: '6px',
                  top: '6px',
                  zIndex: 1,
                }}
              >
                <IconButton
                  onClick={() => {
                    setOpen(false)
                  }}
                  sx={{
                    padding: '4px',
                  }}
                >
                  <Box
                    sx={{
                      width: `${SM_BUTTON_SIZE}px`,
                      height: `${SM_BUTTON_SIZE}px`,
                      display: 'flex',
                      justifyContent: 'center',
                      alignItems: 'center',
                    }}
                  >
                    <CloseIcon
                      sx={{
                        color: '#98bf71',
                      }}
                      fontSize={'medium'}
                    />
                  </Box>
                </IconButton>
              </Box>
            )}
          </Drawer>
        </Box>
      )}
      <AppBar
        position={isMobile ? 'relative' : 'sticky'}
        sx={{
          background: 'white',
        }}
        elevation={0}
      >
        {/* logo bar */}
        <Box
          sx={{
            padding: '12px',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            maxHeight: `${LOGO_BAR_HEIGHT}px`,
          }}
          className={miniHeader ? 'animate-height active' : 'animate-height'}
        >
          <Link to={'/'}>
            {/* <Box
              component="img"
              sx={{
                width: '69px',
                cursor: 'pointer',
                display: 'block',
              }}
              alt={'yuting illustration'}
              src={LogoImage}
            /> */}
            logo
          </Link>
          {!miniHeader && (
            <Box className={'fade-in'}>
              <Box
                sx={{
                  borderBottom: '1px solid #c2c2c2',
                  paddingLeft: '48px',
                  paddingRight: '48px',
                }}
              >
                <Typography
                  component="div"
                  sx={{
                    fontSize: '30px',
                    color: '#2c2c2b',
                    fontFamily: 'Roboto',
                    fontWeight: 200,
                  }}
                >
                  Yuting Zheng
                </Typography>
              </Box>
              <Box sx={{ mt: '9px' }}>
                <Typography
                  component="div"
                  sx={{
                    fontSize: '15px',
                    color: '#2c2c2b',
                    fontFamily: 'Roboto',
                    fontWeight: 200,
                    textAlign: 'center',
                  }}
                >
                  Illustrator
                </Typography>
              </Box>
            </Box>
          )}
        </Box>
        {/* burger button */}
        {isMobile && (
          <>
            <Box
              sx={{
                mt: '30px',
                mb: '12px',
                display: 'flex',
                justifyContent: 'center',
              }}
            >
              <IconButton
                onClick={() => {
                  setOpen(true)
                }}
                sx={{
                  padding: '8px',
                }}
              >
                <Box
                  sx={{
                    width: `${BUTTON_SIZE}px`,
                    height: `${BUTTON_SIZE}px`,
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    border: '1px solid #ed9e93',
                    borderRadius: '50%',
                  }}
                >
                  <MenuIcon
                    sx={{
                      color: '#ed9e93',
                    }}
                    fontSize={'large'}
                  />
                </Box>
              </IconButton>
            </Box>
            <Box sx={{ display: 'flex', justifyContent: 'center', mb: '18px' }}>
              <Box
                sx={{
                  height: '8px',
                  width: '80%',
                  background: '#FAF7F7',
                  borderRadius: '6px',
                }}
              />
            </Box>
          </>
        )}
        {/* nav bar */}
        {!isMobile && (
          <Box
            sx={{
              borderTop: '1px solid #000',
              borderBottom: '1px solid #000',
              height: `${NAV_BAR_HEIGHT}px`,
              background: '#FAF7F7',
              boxSizing: 'border-box',
            }}
          >
            <Box
              sx={{
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'space-around',
                height: '100%',
              }}
            >
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
                    navigate('/about')
                  }}
                >
                  <Typography
                    component="div"
                    sx={{
                      fontSize: '13px',
                      color: '#2c2c2b',
                      fontFamily: 'Roboto',
                      fontWeight: 200,
                      cursor: 'pointer',
                    }}
                  >
                    ABOUT
                  </Typography>
                </Button>
              </Box>
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
                      fontWeight: 200,
                    }}
                  >
                    WORK
                  </Typography>
                </Button>
              </Box>
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
                    navigate('/contact')
                  }}
                >
                  <Typography
                    component="div"
                    sx={{
                      fontSize: '13px',
                      color: '#2c2c2b',
                      fontFamily: 'Roboto',
                      fontWeight: 200,
                      cursor: 'pointer',
                    }}
                  >
                    CONTACT
                  </Typography>
                </Button>
              </Box>
            </Box>
          </Box>
        )}
      </AppBar>
      {/* page content outlet */}
      <Box
        sx={{
          position: 'relative',
          overflowY: 'auto',
          overflowX: 'hidden',
          paddingBottom: `${FOOTER_HEIGHT}px`,
        }}
      >
        <Outlet />
      </Box>
    </>
  )
}

export default Header
