import Typography from '@mui/material/Typography'
import React from 'react'

type VariantType =
  | 'h1'
  | 'h2'
  | 'h3'
  | 'h4'
  | 'h5'
  | 'h6'
  | 'subtitle1'
  | 'subtitle2'
  | 'body1'
  | 'body2'
  | 'caption'
  | 'button'
  | 'overline'
  | 'inherit'
  | undefined
type AlignType = 'right' | 'left' | 'inherit' | 'center' | 'justify' | undefined

type PageTypographyProps = {
  title: string
  variant?: VariantType
  align?: AlignType
  color?: string
}

const TitleTypography = (props: PageTypographyProps) => (
  <>
    <Typography
      component={'h1'}
      variant={props.variant || 'h1'}
      align={props.align || 'center'}
      color={props.color || '#000'}
      {...props}
    >
      {props.title}
    </Typography>
  </>
)

export default TitleTypography
