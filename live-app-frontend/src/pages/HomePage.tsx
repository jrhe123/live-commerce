import React from 'react'

import SEO from 'components/SEO'
import { HomeContainer } from 'features/home'

const HomePage = () => (
  <>
    <SEO
      title={'Home | livestream'}
      description={'livestream'}
      name={'livestream'}
      type={'website'}
    />
    <HomeContainer />
  </>
)

export default HomePage
