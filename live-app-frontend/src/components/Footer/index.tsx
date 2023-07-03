import FacebookIcon from '@mui/icons-material/Facebook'
import InstagramIcon from '@mui/icons-material/Instagram'
import LinkedInIcon from '@mui/icons-material/LinkedIn'
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
  >
    {/* copyright */}
    <Box sx={{ flex: 1, height: '100%', display: 'flex', alignItems: 'center' }}>
      <Typography
        component="div"
        sx={{
          fontSize: '11px',
          color: '#2c2c2b',
          fontFamily: 'Roboto',
          fontWeight: 200,
        }}
      >
        ©2022 by Yuting Zheng
      </Typography>
    </Box>
    {/* social media */}
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'row',
        flex: 1,
        height: '100%',
        alignItems: 'center',
        justifyContent: 'flex-end',
      }}
    >
      {/* linkedin */}
      <Box>
        <IconButton
          onClick={() => {
            window.open('https://www.linkedin.com/in/yuting-zheng-08696b13a')
          }}
          sx={{
            padding: '4px',
          }}
        >
          <Box
            sx={{
              width: `${MEDIA_ICON_SIZE}px`,
              height: `${MEDIA_ICON_SIZE}px`,
              borderRadius: `${MEDIA_ICON_SIZE}px`,
              backgroundColor: '#1DA1F2',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            <LinkedInIcon
              sx={{
                color: 'white',
              }}
              fontSize={'small'}
            />
          </Box>
        </IconButton>
      </Box>
      {/* fb */}
      <Box>
        <IconButton
          onClick={() => {
            window.open('https://www.facebook.com/yuting.zheng.94')
          }}
          sx={{
            padding: '4px',
          }}
        >
          <Box
            sx={{
              width: `${MEDIA_ICON_SIZE}px`,
              height: `${MEDIA_ICON_SIZE}px`,
              borderRadius: `${MEDIA_ICON_SIZE}px`,
              backgroundColor: '#3b5998',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            <FacebookIcon
              sx={{
                color: 'white',
              }}
              fontSize={'small'}
            />
          </Box>
        </IconButton>
      </Box>
      {/* ins */}
      <Box>
        <IconButton
          onClick={() => {
            window.open('https://www.instagram.com/yt.illustration')
          }}
          sx={{
            padding: '4px',
          }}
        >
          <Box
            sx={{
              width: `${MEDIA_ICON_SIZE}px`,
              height: `${MEDIA_ICON_SIZE}px`,
              borderRadius: `${MEDIA_ICON_SIZE}px`,
              backgroundColor: '#8a3ab9',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            <InstagramIcon
              sx={{
                color: 'white',
              }}
              fontSize={'small'}
            />
          </Box>
        </IconButton>
      </Box>
    </Box>
  </Box>
)

export default Footer
