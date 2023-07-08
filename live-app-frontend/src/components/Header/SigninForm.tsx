import { yupResolver } from '@hookform/resolvers/yup/dist/yup'
import { Box, Button, TextField, Typography } from '@mui/material'
import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import * as Yup from 'yup'

import { FormTextField } from 'libs/ui/components/FormTextField'
import { MaskFormTextField } from 'libs/ui/components/MaskFormTextField'

type SigninFormProps = {
  defaultValues?: SigninFormInput
  onSubmitClick(data: SigninFormInput): void
}

type SigninFormInput = {
  phoneNumber: string
  verifyCode: string
}

const phoneRegExp = /^(\+\d{1,2}\s?)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/
const phoneInputMask = [
  '(',
  /[1-9]/,
  /\d/,
  /\d/,
  ')',
  ' ',
  /\d/,
  /\d/,
  /\d/,
  '-',
  /\d/,
  /\d/,
  /\d/,
  /\d/,
]

const SigninForm = (props: SigninFormProps) => {
  const [error, setError] = useState<string>('')
  const {
    defaultValues = {
      phoneNumber: '',
      verifyCode: '',
    },
    onSubmitClick,
  } = props

  const signinFormValidationSchema = Yup.object().shape({
    phoneNumber: Yup.string()
      .required('phoneNumber is required')
      .matches(phoneRegExp, 'phoneNumber is not valid'),
    verifyCode: Yup.string()
      .required('verifyCode is required')
      .matches(/^[0-9]+$/, 'Must be only digits')
      .min(6, 'Must be exactly 6 digits')
      .max(6, 'Must be exactly 6 digits'),
  })

  const methods = useForm<SigninFormInput>({
    defaultValues,
    resolver: yupResolver(signinFormValidationSchema),
  })
  const { handleSubmit, reset, setValue, getValues, watch, control, formState } = methods
  watch(['phoneNumber', 'verifyCode'])

  return (
    <>
      <Box sx={{ marginBottom: '12px' }}>
        <MaskFormTextField
          error={error}
          value={getValues('phoneNumber')}
          mask={phoneInputMask}
          label={'Phone Number'}
          sx={{
            '& label': {
              fontSize: '0.8rem;',
              lineHeight: 1.57,
            },
            '.MuiInputBase-input': {
              fontSize: '0.8rem',
            },
          }}
          type={'text'}
          onBlur={() => {
            const phoneNumber = getValues('phoneNumber')
            const purePhoneNumber = (phoneNumber.match(/\d+/g) || []).join('')
            if (!purePhoneNumber) {
              setError('Phone number is required')
            } else if (!phoneRegExp.test(phoneNumber)) {
              setError('Invalid phone number format')
            } else {
              setError('')
            }
          }}
          onChange={e => {
            setValue('phoneNumber', e.target.value)
          }}
        />
      </Box>
      <Box sx={{ display: 'flex', flexDirection: 'row', marginBottom: '24px' }}>
        <FormTextField
          name="verifyCode"
          label={'SMS Code'}
          control={control}
          sx={{
            '& label': {
              fontSize: '0.8rem;',
              lineHeight: 1.57,
            },
            '.MuiInputBase-input': {
              fontSize: '0.8rem',
            },
          }}
          type={'text'}
        />
        <Box sx={{ width: '120px', paddingLeft: '12px' }}>
          <Button
            sx={{ height: '100%' }}
            onClick={() => {}}
            color={'primary'}
            variant="contained"
            fullWidth
            disabled={!!error || !getValues('phoneNumber')}
          >
            <Typography
              component="div"
              sx={{
                fontSize: '15px',
                fontFamily: 'Roboto',
                cursor: 'pointer',
              }}
            >
              Send
            </Typography>
          </Button>
        </Box>
      </Box>
      <Box sx={{ display: 'flex', justifyContent: 'center' }}>
        <Button
          color={'primary'}
          disabled={!!error || !getValues('phoneNumber') || !getValues('verifyCode')}
          variant="contained"
          onClick={() => {}}
        >
          <Typography
            variant="subtitle2"
            component="div"
            sx={{
              fontSize: '15px',
              fontFamily: 'Roboto',
              cursor: 'pointer',
            }}
          >
            Confirm
          </Typography>
        </Button>
      </Box>
    </>
  )
}

export default SigninForm
