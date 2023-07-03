import { SxProps, TextField, Theme } from '@mui/material'
import React from 'react'
// react hook form
import { Controller } from 'react-hook-form'
import { Control } from 'react-hook-form/dist/types'

export interface FormInputProps {
  name: string
  control: Control<any> // eslint-disable-line
  label?: string
  size?: 'small' | 'medium'
  sx?: SxProps<Theme>
  type?: React.InputHTMLAttributes<unknown>['type']
  rows?: string | number
  multiline?: boolean
  disabled?: boolean
  variant?: 'outlined' | 'filled' | 'standard'
}

export const FormTextField = ({
  name,
  control,
  label,
  size,
  sx,
  type,
  rows,
  multiline,
  disabled,
  variant,
}: FormInputProps) => (
  <Controller
    name={name}
    control={control}
    render={({ field: { onChange, value }, fieldState: { error } }) => (
      <TextField
        helperText={error ? error.message : null}
        size={size || 'small'}
        error={!!error}
        onChange={onChange}
        value={value}
        fullWidth
        label={label}
        variant={variant || 'outlined'}
        sx={sx}
        type={type}
        rows={rows}
        multiline={multiline}
        disabled={disabled}
      />
    )}
  />
)

export default FormTextField
