import { Box, CssBaseline, ThemeProvider } from '@mui/material'
import { createTheme } from '@mui/material/styles'
import React, { useMemo } from 'react'
import { Outlet } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'

// import Footer from 'components/Footer'

const Layout = () => {
  const theme = useMemo(
    () =>
      createTheme({
        typography: {
          button: {
            textTransform: 'none',
          },
          fontFamily: ['Arial', 'Helvetica', 'sans-serif'].join(','),
        },
        palette: {
          mode: 'light',
          primary: {
            light: '#c2c2c2',
            main: '#c2c2c2',
            dark: '#c2c2c2',
            contrastText: '#fff',
          },
          secondary: {
            light: '#ef9a9a',
            main: '#ef5350',
            dark: '#b71c1c',
            contrastText: '#fff',
          },
        },
      }),
    [],
  )

  return (
    <Box
      sx={{
        bgcolor: '#fafafa',
        height: '100vh',
        position: 'relative',
      }}
    >
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <main>
          <Outlet />
        </main>
      </ThemeProvider>
      <ToastContainer draggable pauseOnHover />
      {/* <Box
        sx={{
          position: 'fixed',
          bottom: 0,
          left: 0,
          zIndex: 99,
          overflow: 'hidden',
          boxShadow: 'rgba(0, 0, 0, 0.24) 0px 3px 8px',
        }}
      >
        <Footer />
      </Box> */}
    </Box>
  )
}

export default Layout
