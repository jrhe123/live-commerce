import { Box, IconButton, Typography } from '@mui/material'
import React from 'react'

const FOOTER_HEIGHT = 42
const MEDIA_ICON_SIZE = 27

const Footer = () => (
  <Box
    sx={{
      width: '100vw',
      height: `${FOOTER_HEIGHT}px`,
      display: 'flex',
      flexDirection: 'row',
      paddingLeft: '12px',
      paddingRight: '12px',
      alignItems: 'space-between',
      background: 'white',
    }}
  ></Box>
)

export default Footer
