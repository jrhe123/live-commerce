import { yupResolver } from '@hookform/resolvers/yup/dist/yup'
import { Icon } from '@iconify/react'
import { Box } from '@mui/material'
import React from 'react'
import { useForm } from 'react-hook-form'
import * as Yup from 'yup'

import { FormTextField } from 'libs/ui/components/FormTextField'

type SearchFormProps = {
  defaultValues?: SearchFormInput
  onSubmitClick(data: SearchFormInput): void
}

type SearchFormInput = {
  title: string
}

const SearchForm = (props: SearchFormProps) => {
  const {
    defaultValues = {
      title: '',
    },
    onSubmitClick,
  } = props

  const searchFormValidationSchema = Yup.object().shape({
    title: Yup.string().required('title is required'),
  })

  const methods = useForm<SearchFormInput>({
    defaultValues,
    resolver: yupResolver(searchFormValidationSchema),
  })
  const { handleSubmit, reset, control, formState } = methods

  return (
    <FormTextField
      name="title"
      label={'Search..'}
      control={control}
      sx={{
        background: '#f0f0f0',
        color: '#2c2c2b',
        borderRadius: '12px',
        width: 120,
        '& fieldset': { border: 'none' },
        '& label': {
          fontSize: '0.8rem;',
          lineHeight: 1.57,
        },
        '.MuiInputBase-input': {
          fontSize: '0.8rem',
          '&:-webkit-autofill': {
            borderRadius: '12px',
          },
        },
      }}
      type={'text'}
      iconEnd={
        formState.isDirty ? (
          <Icon icon={'material-symbols:search'} style={{ fontSize: 15, cursor: 'pointer' }} />
        ) : null
      }
    />
  )
}

export default SearchForm
