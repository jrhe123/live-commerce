import { yupResolver } from '@hookform/resolvers/yup/dist/yup'
import { Box, Button, TextField, Typography } from '@mui/material'
import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import MaskedInput from 'react-text-mask'
import * as Yup from 'yup'

import { FormTextField } from 'libs/ui/components/FormTextField'

type SigninFormProps = {
  defaultValues?: SigninFormInput
  onSubmitClick(data: SigninFormInput): void
}

type SigninFormInput = {
  phoneNumber: string
  verifyCode: string
}

const phoneRegExp =
  /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/

const SigninForm = (props: SigninFormProps) => {
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
  const { handleSubmit, reset, control, formState } = methods

  return (
    <>
      <Box sx={{ marginBottom: '12px' }}>
        <MaskedInput
          mask={['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/]}
          render={(innerRef, iProps) => (
            <TextField {...iProps} inputRef={innerRef} />
            // <FormTextField
            //   name="phoneNumber"
            //   label={'Phone Number'}
            //   control={control}
            //   sx={{
            //     '& label': {
            //       fontSize: '0.8rem;',
            //       lineHeight: 1.57,
            //     },
            //     '.MuiInputBase-input': {
            //       fontSize: '0.8rem',
            //     },
            //   }}
            //   type={'text'}
            // />
          )}
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
        <Button color={'primary'} variant="contained" onClick={() => {}}>
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
