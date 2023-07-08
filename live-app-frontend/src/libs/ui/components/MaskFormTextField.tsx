import { SxProps, TextField, Theme, InputAdornment } from '@mui/material'
import React, { ChangeEventHandler } from 'react'
import MaskedInput, { Mask } from 'react-text-mask'

export interface FormInputProps {
  mask: Mask | ((value: string) => Mask)
  error?: string
  value?: string
  label?: string
  size?: 'small' | 'medium'
  sx?: SxProps<Theme>
  type?: React.InputHTMLAttributes<unknown>['type']
  rows?: string | number
  multiline?: boolean
  disabled?: boolean
  variant?: 'outlined' | 'filled' | 'standard'
  iconEnd?: React.ReactNode
  onBlur: ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement>
  onChange: ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement>
}

export const MaskFormTextField = ({
  mask,
  error,
  value,
  label,
  size,
  sx,
  type,
  rows,
  multiline,
  disabled,
  variant,
  iconEnd,
  onBlur,
  onChange,
}: FormInputProps) => (
  <MaskedInput
    mask={mask}
    render={(innerRef, { onBlur: onBlurMask, onChange: onChangeMask, ...rest }) => (
      <TextField
        inputRef={innerRef}
        helperText={error}
        size={size || 'small'}
        error={!!error}
        value={value}
        onBlur={e => {
          onBlurMask(e)
          onBlur(e)
        }}
        onChange={e => {
          onChangeMask(e)
          onChange(e)
        }}
        fullWidth
        label={label}
        variant={variant || 'outlined'}
        sx={sx}
        type={type}
        rows={rows}
        multiline={multiline}
        disabled={disabled}
        InputProps={{
          endAdornment:
            iconEnd != null ? <InputAdornment position="end">{iconEnd}</InputAdornment> : null,
        }}
        {...rest}
      />
    )}
  />
)

export default MaskFormTextField
