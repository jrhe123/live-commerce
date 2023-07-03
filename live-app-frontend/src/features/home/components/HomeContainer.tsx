import { Box, Typography } from '@mui/material'
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

// import Cover1Image from 'assets/images/cover_1.jpg'
import useWindowSize from 'hooks/useWindowSize'

const MAX_WIDTH = 840
const BREAK_POINT = 600

export const HomeContainer = () => {
  const [activeIndex, setActiveIndex] = useState<number | null>(null)
  const navigate = useNavigate()
  const { width } = useWindowSize()
  const isMobile = width <= BREAK_POINT

  return <div>123</div>

  // return (
  //   <Box
  //     sx={{
  //       paddingTop: '24px',
  //       display: 'flex',
  //       justifyContent: 'center',
  //       paddingLeft: isMobile ? '24px' : '12px',
  //       paddingRight: isMobile ? '24px' : '12px',
  //       paddingBottom: isMobile ? '36px' : '24px',
  //     }}
  //     className="fade-in"
  //   >
  //     <Box
  //       sx={{
  //         display: 'flex',
  //         flexDirection: isMobile ? 'column' : 'row',
  //         flexWrap: 'wrap',
  //         width: '100%',
  //         maxWidth: `${MAX_WIDTH}px`,
  //       }}
  //     >
  //       {/* 1 */}
  //       <Box
  //         sx={{
  //           width: isMobile ? '100%' : 'calc(45% - 12px)',
  //           display: 'flex',
  //           justifyContent: 'flex-end',
  //           marginBottom: '24px',
  //         }}
  //       >
  //         <Box
  //           sx={{
  //             position: 'relative',
  //             maxWidth: isMobile ? '100vw' : '284px',
  //             maxHeight: isMobile ? '233px' : '100vh',
  //             width: '100%',
  //             overflow: 'hidden',
  //           }}
  //           onMouseEnter={() => {
  //             setActiveIndex(1)
  //           }}
  //           onMouseLeave={() => {
  //             setActiveIndex(null)
  //           }}
  //         >
  //           <Box
  //             component="img"
  //             sx={{
  //               maxWidth: isMobile ? '100vw' : '284px',
  //               width: '100%',
  //               cursor: 'pointer',
  //               display: 'block',
  //             }}
  //             alt={'Authentic Fairy Tales'}
  //             src={Cover1Image}
  //             className={`cover-scale-image${activeIndex === 1 ? ' active' : ''}`}
  //           />
  //           <Box
  //             sx={{
  //               position: 'absolute',
  //               top: 0,
  //               left: 0,
  //               zIndex: 1,
  //               width: '100%',
  //               height: '100%',
  //               display: 'flex',
  //               flexDirection: 'column',
  //               justifyContent: 'center',
  //               alignItems: 'center',
  //               background: isMobile ? 'rgba(0,0,0,0.3)' : 'transparent',
  //             }}
  //             className={isMobile ? '' : 'cover-hidden-text'}
  //             onClick={() => {
  //               navigate('/thesis')
  //             }}
  //           >
  //             <Typography
  //               component="div"
  //               sx={{
  //                 fontSize: isMobile ? '18px' : '30px',
  //                 color: 'white',
  //                 fontFamily: 'Roboto',
  //                 fontWeight: 'bold',
  //                 lineHeight: isMobile ? '21px' : '36px',
  //                 marginTop: isMobile ? '0px' : '100px',
  //               }}
  //             >
  //               Authentic
  //               <br />
  //               Fairy Tales
  //             </Typography>
  //             <Box
  //               sx={{
  //                 mt: '12px',
  //                 mb: '18px',
  //                 width: '100%',
  //               }}
  //             >
  //               <Box
  //                 sx={{
  //                   height: '2px',
  //                   width: '60%',
  //                   background: '#fff',
  //                   margin: '0 auto',
  //                 }}
  //               />
  //             </Box>
  //             <Typography
  //               component="div"
  //               sx={{
  //                 fontSize: isMobile ? '12px' : '15px',
  //                 color: 'white',
  //                 fontFamily: 'Roboto',
  //                 fontWeight: 'bold',
  //                 lineHeight: '18px',
  //                 textAlign: 'center',
  //               }}
  //             >
  //               cultural and social issues that
  //               <br />
  //               existed in children{"'"}s stories that
  //               <br />
  //               are still impacting modern
  //               <br />
  //               society
  //             </Typography>
  //           </Box>
  //         </Box>
  //       </Box>
  //       {/* space */}
  //       {!isMobile && <Box sx={{ width: '24px' }} />}
  //       {/* 2 */}
  //       <Box
  //         sx={{
  //           width: isMobile ? '100%' : 'calc(55% - 12px)',
  //           marginBottom: '24px',
  //           display: 'flex',
  //           alignItems: 'flex-end',
  //         }}
  //       >
  //         <Box
  //           sx={{
  //             position: 'relative',
  //             maxWidth: isMobile ? '100vw' : '400px',
  //             maxHeight: isMobile ? '233px' : '100vh',
  //             width: '100%',
  //             overflow: 'hidden',
  //           }}
  //           onMouseEnter={() => {
  //             setActiveIndex(2)
  //           }}
  //           onMouseLeave={() => {
  //             setActiveIndex(null)
  //           }}
  //         >
  //           <Box
  //             component="img"
  //             sx={{
  //               maxWidth: isMobile ? '100vw' : '400px',
  //               width: '100%',
  //               cursor: 'pointer',
  //               display: 'block',
  //             }}
  //             alt={'Illustrations'}
  //             src={Cover2Image}
  //             className={`cover-scale-image${activeIndex === 2 ? ' active' : ''}`}
  //           />
  //           <Box
  //             sx={{
  //               position: 'absolute',
  //               top: 0,
  //               left: 0,
  //               zIndex: 1,
  //               width: '100%',
  //               height: '100%',
  //               display: 'flex',
  //               flexDirection: 'column',
  //               justifyContent: 'center',
  //               alignItems: 'center',
  //               background: isMobile ? 'rgba(0,0,0,0.3)' : 'transparent',
  //             }}
  //             className={isMobile ? '' : 'cover-hidden-text'}
  //             onClick={() => {
  //               navigate('/editorial')
  //             }}
  //           >
  //             <Typography
  //               component="div"
  //               sx={{
  //                 fontSize: isMobile ? '18px' : '30px',
  //                 color: 'white',
  //                 fontFamily: 'Roboto',
  //                 fontWeight: 'bold',
  //                 lineHeight: '36px',
  //                 marginTop: isMobile ? '0px' : '100px',
  //               }}
  //             >
  //               Illustrations
  //             </Typography>
  //             <Box
  //               sx={{
  //                 mt: '12px',
  //                 mb: '18px',
  //                 width: '100%',
  //               }}
  //             >
  //               <Box
  //                 sx={{
  //                   height: '2px',
  //                   width: '60%',
  //                   background: '#fff',
  //                   margin: '0 auto',
  //                 }}
  //               />
  //             </Box>
  //           </Box>
  //         </Box>
  //       </Box>
  //       {/* 3 */}
  //       <Box
  //         sx={{
  //           width: isMobile ? '100%' : 'calc(40% - 12px)',
  //           display: 'flex',
  //           justifyContent: 'flex-end',
  //         }}
  //       >
  //         <Box
  //           sx={{
  //             position: 'relative',
  //             maxWidth: isMobile ? '100vw' : '296px',
  //             maxHeight: isMobile ? '233px' : '100vh',
  //             width: '100%',
  //             overflow: 'hidden',
  //           }}
  //           onMouseEnter={() => {
  //             setActiveIndex(3)
  //           }}
  //           onMouseLeave={() => {
  //             setActiveIndex(null)
  //           }}
  //         >
  //           <Box
  //             component="img"
  //             sx={{
  //               maxWidth: isMobile ? '100vw' : '296px',
  //               width: '100%',
  //               cursor: 'pointer',
  //               display: 'block',
  //             }}
  //             alt={'Portraits'}
  //             src={Cover3Image}
  //             className={`cover-scale-image${activeIndex === 3 ? ' active' : ''}`}
  //           />
  //           <Box
  //             sx={{
  //               position: 'absolute',
  //               top: 0,
  //               left: 0,
  //               zIndex: 1,
  //               width: '100%',
  //               height: '100%',
  //               display: 'flex',
  //               flexDirection: 'column',
  //               justifyContent: 'center',
  //               alignItems: 'center',
  //               background: isMobile ? 'rgba(0,0,0,0.3)' : 'transparent',
  //             }}
  //             className={isMobile ? '' : 'cover-hidden-text'}
  //             onClick={() => {
  //               navigate('/portrait')
  //             }}
  //           >
  //             <Typography
  //               component="div"
  //               sx={{
  //                 fontSize: isMobile ? '18px' : '30px',
  //                 color: 'white',
  //                 fontFamily: 'Roboto',
  //                 fontWeight: 'bold',
  //                 lineHeight: '36px',
  //                 marginTop: isMobile ? '0px' : '100px',
  //               }}
  //             >
  //               Portraits
  //             </Typography>
  //             <Box
  //               sx={{
  //                 mt: '12px',
  //                 mb: '18px',
  //                 width: '100%',
  //               }}
  //             >
  //               <Box
  //                 sx={{
  //                   height: '2px',
  //                   width: '60%',
  //                   background: '#fff',
  //                   margin: '0 auto',
  //                 }}
  //               />
  //             </Box>
  //           </Box>
  //         </Box>
  //       </Box>
  //       {/* space */}
  //       {!isMobile && <Box sx={{ width: '24px' }} />}
  //       {/* 4 */}
  //       <Box
  //         sx={{
  //           width: isMobile ? '100%' : 'calc(60% - 12px)',
  //           display: 'flex',
  //           alignItems: 'flex-end',
  //           marginTop: isMobile ? '24px' : '0px',
  //         }}
  //       >
  //         <Box
  //           sx={{
  //             position: 'relative',
  //             maxWidth: isMobile ? '100vw' : '514px',
  //             maxHeight: isMobile ? '233px' : '100vh',
  //             width: '100%',
  //             overflow: 'hidden',
  //           }}
  //           onMouseEnter={() => {
  //             setActiveIndex(4)
  //           }}
  //           onMouseLeave={() => {
  //             setActiveIndex(null)
  //           }}
  //         >
  //           <Box
  //             component="img"
  //             sx={{
  //               maxWidth: isMobile ? '100vw' : '514px',
  //               width: '100%',
  //               cursor: 'pointer',
  //               display: 'block',
  //             }}
  //             alt={'Personal'}
  //             src={Cover4Image}
  //             className={`cover-scale-image${activeIndex === 4 ? ' active' : ''}`}
  //           />
  //           <Box
  //             sx={{
  //               position: 'absolute',
  //               top: 0,
  //               left: 0,
  //               zIndex: 1,
  //               width: '100%',
  //               height: '100%',
  //               display: 'flex',
  //               flexDirection: 'column',
  //               justifyContent: 'center',
  //               alignItems: 'center',
  //               background: isMobile ? 'rgba(0,0,0,0.3)' : 'transparent',
  //             }}
  //             className={isMobile ? '' : 'cover-hidden-text'}
  //             onClick={() => {
  //               navigate('/personal')
  //             }}
  //           >
  //             <Typography
  //               component="div"
  //               sx={{
  //                 fontSize: isMobile ? '18px' : '30px',
  //                 color: 'white',
  //                 fontFamily: 'Roboto',
  //                 fontWeight: 'bold',
  //                 lineHeight: '36px',
  //                 marginTop: isMobile ? '0px' : '100px',
  //               }}
  //             >
  //               Personal
  //             </Typography>
  //             <Box
  //               sx={{
  //                 mt: '12px',
  //                 mb: '18px',
  //                 width: '100%',
  //               }}
  //             >
  //               <Box
  //                 sx={{
  //                   height: '2px',
  //                   width: '30%',
  //                   background: '#fff',
  //                   margin: '0 auto',
  //                 }}
  //               />
  //             </Box>
  //           </Box>
  //         </Box>
  //       </Box>
  //     </Box>
  //   </Box>
  // )
}
