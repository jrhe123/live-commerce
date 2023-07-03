import React, { Suspense } from 'react'
import { Route, Routes, Navigate } from 'react-router-dom'

// theme layout
import Header from 'components/Header'
import Layout from 'components/Layout'

// pages
const HomePage = React.lazy(() => import('pages/HomePage'))

const AppRoutes = () => (
  <>
    <Suspense fallback={<div />}>
      <Routes>
        <Route element={<Layout />}>
          <Route element={<Header />}>
            {/* main tab */}
            <Route path="/" element={<HomePage />} />
          </Route>
          <Route path="*" element={<Navigate to="/" replace />} />
        </Route>
      </Routes>
    </Suspense>
  </>
)

export default AppRoutes
